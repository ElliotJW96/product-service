package com.product.models;

import jakarta.inject.Singleton;

import java.util.ArrayList;

@Singleton
public class BusinessRules {
    public Product applyBusinessRules(Product product, ArrayList<StandardRate> standardRates) {

        Double boeBaseRate = null;
        Double standardVariableRate = null;
        for (StandardRate rate : standardRates) {
            if ("BOE".equals(rate.getRateId())) {
                boeBaseRate = rate.getRateAmount();
            }
            if("SVR".equals(rate.getRateId())){
                standardVariableRate = rate.getRateAmount();
            }
        }
        product.setBoeBaseRate(boeBaseRate);
        product.setStandardVariableRate(standardVariableRate);

        if(product.getProductType().equals("FIXED")){
            product.setCalculatedInterestRate(product.getFixedInterest());
        }
        if(product.getProductType().equals("SVR")){
            product.setCalculatedInterestRate(product.getStandardVariableRate());
        }
        if(product.getProductType().equals("TRACKER")){
            product.setCalculatedInterestRate(product.getBoeBaseRate() + product.getTrackerAddedInterest());
        }

        return product;
    }
}
