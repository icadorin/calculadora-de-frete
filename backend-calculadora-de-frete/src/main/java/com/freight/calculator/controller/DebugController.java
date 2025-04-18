package com.freight.calculator.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @Value("${APP_CORS_ALLOWED_ORIGINS:NOT_SET}")
    private String corsOrigins;

    @GetMapping("/debug-env")
    public String debugEnv() {
        return "CORS_ORIGINS=" + corsOrigins;
    }
}
