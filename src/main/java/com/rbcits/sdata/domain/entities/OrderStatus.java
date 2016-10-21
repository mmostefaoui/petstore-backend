package com.rbcits.sdata.domain.entities;


public enum OrderStatus {
    PLACED("placed"),
    APPROVED("approved"),
    DELIVERED("delivered");

    private String status;

    private OrderStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public String getName() {
        return this.name();
    }

}
