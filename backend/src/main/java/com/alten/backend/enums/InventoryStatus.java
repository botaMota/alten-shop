package com.alten.backend.enums;

public enum InventoryStatus {
    INSTOCK("INSTOCK"),

    LOWSTOCK("LOWSTOCK"),

    OUTOFSTOCK("OUTOFSTOCK");

    private String value;

    InventoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}