package com.product;

import com.product.models.Product;
import com.product.models.StandardRate;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Singleton
public class DataApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);
    private static final String DATA_SERVICE_URL = "http://host.docker.internal:8082/data/";

    @Client(DATA_SERVICE_URL)
    HttpClient client;

    @Inject
    public DataApiClient(HttpClient client) {
        this.client = client;
    }

    public Product getProduct(String mortgageId) {
        try {
            String constructedUrl = DATA_SERVICE_URL  + "product?mortgageId="+mortgageId;
            LOG.info("Constructed URL for data-service: " + constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);
            return client.toBlocking().retrieve(request, Product.class);
        } catch (Exception e) {
            LOG.error("Error fetching product data.", e);
            throw e;
        }
    }

    public ArrayList<StandardRate> getStandardRates() {
        try {
            String constructedUrl = DATA_SERVICE_URL + "standardrates";
            LOG.info("Constructed URL for data-service: " + constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);
            LOG.info(request.toString());

            // Log raw response
            HttpResponse<String> rawResponse = client.toBlocking().exchange(request, String.class);
            LOG.info("Raw Response Body: " + rawResponse.getBody().orElse("EMPTY"));

            // Deserialize using explicit type information
            HttpResponse<?> genericResponse = client.toBlocking().exchange(request, Argument.of(ArrayList.class, StandardRate.class));
            HttpResponse<ArrayList<StandardRate>> response = (HttpResponse<ArrayList<StandardRate>>) genericResponse;

            if (response.getStatus().getCode() >= 400) {
                LOG.error("Received an error response: " + response.getStatus().getCode() + " - " + response.getStatus().getReason());
            } else if (!response.getBody().isPresent()) {
                LOG.error("Response body is empty or null.");
            } else {
                ArrayList<StandardRate> standardRates = response.getBody().get();
                if (standardRates.isEmpty()) {
                    LOG.warn("Deserialized mortgage list is empty.");
                } else {
                    for (StandardRate standardRate : standardRates) {
                        LOG.info("Deserialized Standard Rate: " + standardRate.toString());
                    }
                    return standardRates;
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching standardRates data.", e);
            throw e;
        }
        return new ArrayList<>(); // Return an empty list in case of issues to avoid NullPointerException
    }
}
