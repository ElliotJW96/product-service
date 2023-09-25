package com.product;

import com.product.models.BusinessRules;
import com.product.models.Product;
import com.product.models.StandardRate;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Controller("/product")
public class ProductController {

    private final DataApiClient client;
    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);

    @Inject
    public ProductController(DataApiClient client) {
        this.client = client;
    }

    @Get
    @Produces(MediaType.TEXT_JSON)
    public Product product(HttpRequest<?> request, @QueryValue String mortgageId) {
        try {
            BusinessRules rules = new BusinessRules();
            Product product = client.getProduct(mortgageId);
            ArrayList<StandardRate> rates = client.getStandardRates();

            product = rules.applyBusinessRules(product, rates);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product data.");
        }
    }
}

