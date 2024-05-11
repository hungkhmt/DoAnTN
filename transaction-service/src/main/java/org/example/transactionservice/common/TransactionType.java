package org.example.transactionservice.common;

public enum TransactionType {
    DEPOSIT("Nạp tiền"),
    WITHDRAWAL("Rút tiền"),
    TRANSFER("Chuyển khoản");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
