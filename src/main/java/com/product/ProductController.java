package com.product;

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

/**
 * Controller responsible for handling product-related requests.
 */
@Controller("/product")
public class ProductController {

    private final DataApiClient client;
    private final BusinessRules businessRules;
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    /**
     * Constructor that initializes the data client and business rules.
     *
     * @param client The client used to fetch product data.
     * @param businessRules The rules to apply to the product data.
     */
    @Inject
    public ProductController(DataApiClient client, BusinessRules businessRules) {
        this.client = client;
        this.businessRules = businessRules;
    }

    /**
     * Fetches the product details for a given mortgage ID and returns it after applying business rules.
     *
     * @param request The HTTP request object.
     * @param mortgageId The mortgage ID for which to fetch the product.
     * @return The product associated with the given mortgage ID.
     * @throws HttpStatusException If there's an error during the fetch operation.
     */
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Product product(HttpRequest<?> request, @QueryValue String mortgageId) {
        try {
            Product product = client.getProduct(mortgageId);
            ArrayList<StandardRate> rates = client.getStandardRates();
            product = businessRules.applyRules(product, rates);
            return product;
        } catch (Exception e) {
            LOG.error("Error fetching product data.", e);
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product data: " + e.getMessage());
        }
    }
}
