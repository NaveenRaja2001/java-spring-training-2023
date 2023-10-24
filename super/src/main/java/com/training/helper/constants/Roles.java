package com.training.helper.constants;

public enum Roles {
    ADMIN("ADMIN"),
    HELPER("HELPER"),
    RESIDENT("RESIDENT");

    private String value;

    private Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
