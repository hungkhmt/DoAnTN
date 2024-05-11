package org.example.transactionservice.common;

public enum CurrencySymbol {

    USD("USD"),

    EUR("EUR");

    private final String description;

    CurrencySymbol(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
