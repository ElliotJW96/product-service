package com.product;

import com.product.models.Product;
import com.product.models.StandardRate;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * Client for interacting with the data service to fetch product-related information.
 */
@Singleton
public class DataApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);
    private static final String DATA_SERVICE_URL = "http://host.docker.internal:8082/data";

    HttpClient client;

    /**
     * Constructor that initializes the HTTP client.
     *
     * @param client The HTTP client for making requests to the data service.
     */
    @Inject
    public DataApiClient(HttpClient client) {
        this.client = client;
    }

    /**
     * Fetches the product associated with a given mortgage ID.
     *
     * @param mortgageId The ID of the mortgage for which to fetch the product.
     * @return The product associated with the given mortgage ID.
     * @throws Exception If there's an error during the fetch operation.
     */
    public Product getProduct(String mortgageId) throws Exception {
        try {
            String constructedUrl = DATA_SERVICE_URL  + "/product?mortgageId=" + mortgageId;
            LOG.info("Constructed URL for data-service: " + constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);
            return client.toBlocking().retrieve(request, Product.class);
        } catch (Exception e) {
            LOG.error("Error fetching product data.", e);
            throw e;
        }
    }

    /**
     * Fetches the standard rates from the data service.
     *
     * @return A list of standard rates.
     * @throws Exception If there's an error during the fetch operation.
     */
    public ArrayList<StandardRate> getStandardRates() throws Exception {
        try {
            String constructedUrl = DATA_SERVICE_URL + "/standardrates";
            LOG.info("Constructed URL for data-service: " + constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);

            // Log raw response
            HttpResponse<String> rawResponse = client.toBlocking().exchange(request, String.class);
            LOG.info("Raw Response Body: " + rawResponse.getBody().orElse("EMPTY"));

            // Deserialize using explicit type information
            HttpResponse<?> genericResponse = client.toBlocking().exchange(request, Argument.of(ArrayList.class, StandardRate.class));
            HttpResponse<ArrayList<StandardRate>> response = (HttpResponse<ArrayList<StandardRate>>) genericResponse;

            if (response.getStatus().getCode() >= 400) {
                LOG.error("Received an error response: " + response.getStatus().getCode() + " - " + response.getStatus().getReason());
                throw new Exception();
            }

            ArrayList<StandardRate> standardRates = response.getBody().orElse(new ArrayList<>());
            if (standardRates.isEmpty()) {
                LOG.warn("Deserialized mortgage list is empty.");
            } else {
                for (StandardRate standardRate : standardRates) {
                    LOG.info("Deserialized Standard Rate: " + standardRate.toString());
                }
            }
            return standardRates;
        } catch (Exception e) {
            LOG.error("Error fetching standardRates data.", e);
            throw e;
        }
    }
}
