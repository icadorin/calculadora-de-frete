package com.freight.calculator.controller;

import com.freight.calculator.dto.FreightCalculationRequest;
import com.freight.calculator.dto.FreightCalculationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.freight.calculator.service.FreightCalculationService;

@RestController
public class FreightController {

    private final FreightCalculationService service;
    private final String basePath;

    public FreightController(
            FreightCalculationService service,
            @Value("${APP_API_BASE_PATH:/api}") String basePath,
            @Value("${APP_API_FRETE_PATH:/frete}") String fretePath
    ) {
        this.service = service;
        this.basePath = basePath + fretePath;
    }

    @PostMapping("/calcular")
    public ResponseEntity<FreightCalculationResponse> calculateFreight(
            @RequestBody @Valid FreightCalculationRequest request
    ) {
        FreightCalculationResponse response = service.calculatorFreight(request);
        return ResponseEntity.ok(response);
    }
}
