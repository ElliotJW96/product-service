package com.product.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class StandardRate {
    String rateId;
    Double rateAmount;

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public Double getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(Double rateAmount) {
        this.rateAmount = rateAmount;
    }
}
