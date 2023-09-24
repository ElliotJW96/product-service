package com.product;

import com.product.models.Product;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/product")
public class ProductController {

    @Get
    @Produces(MediaType.TEXT_JSON)
    public Product product(HttpRequest<?> request){
        return new Product();
    }
}
