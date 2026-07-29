package com.example.shop.mock;

import java.util.LinkedHashMap;
import java.util.Map;

public final class KarateApiScenarioData {

    private KarateApiScenarioData() {
    }

    public static Map<String, Object> getRootScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("method", "GET");
        scenario.put("path", "/");
        scenario.put("expectedStatus", 200);
        scenario.put("expectedMvcView", "products");
        return scenario;
    }

    public static Map<String, Object> postRootScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("method", "POST");
        scenario.put("path", "/");
        scenario.put("expectedStatus", 405);
        return scenario;
    }

    public static Map<String, Object> unknownPathScenario() {
        Map<String, Object> scenario = new LinkedHashMap<>();
        scenario.put("method", "GET");
        scenario.put("path", "/unknown-path");
        scenario.put("expectedStatus", 404);
        return scenario;
    }
}
