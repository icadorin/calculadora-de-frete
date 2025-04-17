package com.freight.calculator.controller;

import com.freight.calculator.dto.FreightCalculationRequest;
import com.freight.calculator.dto.FreightCalculationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.freight.calculator.service.FreightCalculationService;

@RestController
@RequestMapping("${app.api.base-path}${app.api.frete-path}")
public class FreightController {

    private final FreightCalculationService freightCalculationService;

    public FreightController(FreightCalculationService freightCalculationService) {
        this.freightCalculationService = freightCalculationService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<FreightCalculationResponse> calculateFreight(
            @RequestBody @Valid FreightCalculationRequest request) {

        FreightCalculationResponse response = freightCalculationService.calculatorFreight(request);
        return ResponseEntity.ok(response);
    }
}