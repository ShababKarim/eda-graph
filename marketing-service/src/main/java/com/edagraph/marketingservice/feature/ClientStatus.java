package com.edagraph.marketingservice.feature;

import java.util.List;

public enum ClientStatus {

    ADDED("added"),
    CONTACTED("contacted");

    ClientStatus(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return this.value;
    }

    public static List<ClientStatus> getValues() {
        return List.of(
                ADDED,
                CONTACTED);
    }

    public static ClientStatus findValue(String value) {
        return getValues().stream()
                .filter(status -> status.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow();
    }
}
