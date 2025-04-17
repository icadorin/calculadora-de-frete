package com.freight.calculator.service;

import com.freight.calculator.dto.FreightCalculationRequest;
import com.freight.calculator.dto.FreightCalculationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FreightCalculationService {

    private final RestTemplate restTemplate;

    private static final Map<String, Double> BASE_FREIGHT_PRICES = Map.ofEntries(
            // Sudeste
            Map.entry("SP", 10.0),
            Map.entry("RJ", 12.0),
            Map.entry("MG", 14.0),
            Map.entry("ES", 15.0),

            // Sul
            Map.entry("PR", 15.0),
            Map.entry("SC", 16.0),
            Map.entry("RS", 17.0),

            // Centro-Oeste
            Map.entry("DF", 18.0),
            Map.entry("GO", 17.0),
            Map.entry("MT", 20.0),
            Map.entry("MS", 19.0),

            // Nordeste
            Map.entry("BA", 16.0),
            Map.entry("SE", 17.0),
            Map.entry("AL", 18.0),
            Map.entry("PE", 18.0),
            Map.entry("PB", 19.0),
            Map.entry("RN", 20.0),
            Map.entry("CE", 21.0),
            Map.entry("PI", 22.0),
            Map.entry("MA", 23.0),

            // Norte
            Map.entry("TO", 21.0),
            Map.entry("PA", 24.0),
            Map.entry("AP", 26.0),
            Map.entry("RR", 28.0),
            Map.entry("AM", 27.0),
            Map.entry("AC", 29.0),
            Map.entry("RO", 25.0)
    );

    private static final Map<String, Double> REGION_FACTORS = Map.of(
            "SUDESTE", 1.0,
            "SUL", 1.2,
            "CENTRO-OESTE", 1.3,
            "NORDESTE", 1.5,
            "NORTE", 1.8
    );

    private static final Map<String, String> STATE_TO_REGION = Map.ofEntries(
            // Sudeste
            Map.entry("SP", "SUDESTE"),
            Map.entry("RJ", "SUDESTE"),
            Map.entry("MG", "SUDESTE"),
            Map.entry("ES", "SUDESTE"),

            // Sul
            Map.entry("PR", "SUL"),
            Map.entry("SC", "SUL"),
            Map.entry("RS", "SUL"),

            // Centro-Oeste
            Map.entry("DF", "CENTRO-OESTE"),
            Map.entry("GO", "CENTRO-OESTE"),
            Map.entry("MT", "CENTRO-OESTE"),
            Map.entry("MS", "CENTRO-OESTE"),

            // Nordeste
            Map.entry("BA", "NORDESTE"),
            Map.entry("SE", "NORDESTE"),
            Map.entry("AL", "NORDESTE"),
            Map.entry("PE", "NORDESTE"),
            Map.entry("PB", "NORDESTE"),
            Map.entry("RN", "NORDESTE"),
            Map.entry("CE", "NORDESTE"),
            Map.entry("PI", "NORDESTE"),
            Map.entry("MA", "NORDESTE"),

            // Norte
            Map.entry("TO", "NORTE"),
            Map.entry("PA", "NORTE"),
            Map.entry("AP", "NORTE"),
            Map.entry("RR", "NORTE"),
            Map.entry("AM", "NORTE"),
            Map.entry("AC", "NORTE"),
            Map.entry("RO", "NORTE")
    );

    public FreightCalculationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FreightCalculationResponse calculatorFreight(FreightCalculationRequest request) {

        if (request == null || request.getCep() == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        if (!request.getCep().matches("\\d{8}")) {
            throw new IllegalArgumentException("O CEP deve ter 8 dígitos");
        }

        String state = getStateFromViaCep(request.getCep());
        double freightCost = calculateBaseFreight(state);

        return new FreightCalculationResponse(freightCost);
    }

    private String getStateFromViaCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.containsKey("erro")) {
                throw new IllegalArgumentException("CEP não encontrado ou inválido.");
            }

            return ((String) response.get("uf")).toUpperCase();

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao consultar: " + e.getMessage());
        }
    }

    private double calculateBaseFreight(String state) {
        if (!STATE_TO_REGION.containsKey(state)) {
            throw new IllegalArgumentException("Estado inválido para cálculo de frete.");
        }

        String region = STATE_TO_REGION.get(state);
        double basePrice = BASE_FREIGHT_PRICES.get(state);
        double regionalFactor = REGION_FACTORS.get(region);

        return basePrice * regionalFactor;
    }
}
