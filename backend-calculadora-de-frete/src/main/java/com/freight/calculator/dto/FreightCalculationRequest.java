package com.freight.calculator.dto;

import jakarta.validation.constraints.Pattern;

public class FreightCalculationRequest {

    @Pattern(regexp = "\\d{8}", message = "O CEP deve ter 8 dígitos")
    private String cep;

    public FreightCalculationRequest() {}

    public FreightCalculationRequest(String cep) {
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
