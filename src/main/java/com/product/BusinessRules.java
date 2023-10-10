package com.product;

import com.product.models.Product;
import com.product.models.StandardRate;
import jakarta.inject.Singleton;

import java.util.ArrayList;

/**
 * Represents the business logic for processing and modifying product data.
 */
@Singleton
public class BusinessRules {

    private static final String BOE = "BOE";
    private static final String SVR = "SVR";
    private static final String FIXED = "FIXED";
    private static final String TRACKER = "TRACKER";

    /**
     * Applies business rules to a product based on provided standard rates.
     * Sets the BOE base rate, standard variable rate, and calculates the interest rate
     * depending on the product type.
     *
     * @param product The product to which the rules should be applied.
     * @param standardRates The list of standard rates used for calculations.
     * @return The modified product with applied business rules.
     */
    public Product applyRules(Product product, ArrayList<StandardRate> standardRates) {

        Double boeBaseRate = null;
        Double standardVariableRate = null;

        for (StandardRate rate : standardRates) {
            if (BOE.equals(rate.getRateId())) {
                boeBaseRate = rate.getRateAmount();
            }
            if (SVR.equals(rate.getRateId())) {
                standardVariableRate = rate.getRateAmount();
            }
            // Exit the loop early if both rates are found
            if (boeBaseRate != null && standardVariableRate != null) {
                break;
            }
        }

        product.setBoeBaseRate(boeBaseRate);
        product.setStandardVariableRate(standardVariableRate);

        if (FIXED.equals(product.getProductType())) {
            product.setCalculatedInterestRate(product.getFixedInterest());
        } else if (SVR.equals(product.getProductType())) {
            product.setCalculatedInterestRate(product.getStandardVariableRate());
        } else if (TRACKER.equals(product.getProductType())) {
            product.setCalculatedInterestRate(product.getBoeBaseRate() + product.getTrackerAddedInterest());
        }

        return product;
    }
}
