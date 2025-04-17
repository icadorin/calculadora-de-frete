package com.freight.calculator.dto;

public class FreightCalculationResponse {

    private double freightCost;

    public FreightCalculationResponse(){    }

    public FreightCalculationResponse(double freightCost) {
        this.freightCost = freightCost;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }
}
