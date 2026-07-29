package com.example.shop.mock;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ProductControllerViewMockData {

    private ProductControllerViewMockData() {
    }

    public static Map<String, Object> successfulRootViewScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("httpMethod", "GET");
        scenario.put("requestPath", "/");
        scenario.put("expectedStatus", 200);
        scenario.put("expectedViewName", "products");
        return scenario;
    }

    public static Map<String, Object> invalidMethodScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("httpMethod", "POST");
        scenario.put("requestPath", "/");
        scenario.put("expectedStatus", 405);
        scenario.put("expectedError", "Method Not Allowed");
        return scenario;
    }

    public static Map<String, Object> unknownPathScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("httpMethod", "GET");
        scenario.put("requestPath", "/unknown-path");
        scenario.put("expectedStatus", 404);
        scenario.put("expectedError", "Not Found");
        return scenario;
    }

    public static String expectedViewName() {
        return "products";
    }

    public static String nullViewName() {
        return null;
    }
}
