package com.product.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.sql.Date;

@Introspected
@Serdeable
public class Product {

    String productId;
    String productType;
    String productDescription;
    Double calculatedInterestRate;
    Double fixedInterest;
    Double trackerAddedInterest;
    Double boeBaseRate;
    Double standardVariableRate;
    Date endDate;
    int productFee;
    int earlyRepaymentFeePercentage;
    int earlyRepaymentThresholdPercentage;

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getFixedInterest() {
        return fixedInterest;
    }
    public void setFixedInterest(Double fixedInterest) {
        this.fixedInterest = fixedInterest;
    }

    public Double getTrackerAddedInterest() {
        return trackerAddedInterest;
    }
    public void setTrackerAddedInterest(Double trackerAddedInterest) {
        this.trackerAddedInterest = trackerAddedInterest;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getProductFee() {
        return productFee;
    }
    public void setProductFee(int productFee) {
        this.productFee = productFee;
    }

    public int getEarlyRepaymentFeePercentage() {
        return earlyRepaymentFeePercentage;
    }
    public void setEarlyRepaymentFeePercentage(int earlyRepaymentFeePercentage) {
        this.earlyRepaymentFeePercentage = earlyRepaymentFeePercentage;
    }

    public int getEarlyRepaymentThresholdPercentage() {
        return earlyRepaymentThresholdPercentage;
    }
    public void setEarlyRepaymentThresholdPercentage(int earlyRepaymentThresholdPercentage) {
        this.earlyRepaymentThresholdPercentage = earlyRepaymentThresholdPercentage;
    }

    public Double getCalculatedInterestRate() {
        return calculatedInterestRate;
    }
    public void setCalculatedInterestRate(Double calculatedInterestRate) {
        this.calculatedInterestRate = calculatedInterestRate;
    }

    public Double getBoeBaseRate() {
        return boeBaseRate;
    }
    public void setBoeBaseRate(Double boeBaseRate) {
        this.boeBaseRate = boeBaseRate;
    }

    public Double getStandardVariableRate() {
        return standardVariableRate;
    }
    public void setStandardVariableRate(Double standardVariableRate) {
        this.standardVariableRate = standardVariableRate;
    }
}
