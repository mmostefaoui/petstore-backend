package com.rbcits.sdata.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PetStatus {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private String status;

    private PetStatus(final String status) {
        this.status = status;
    }

    @JsonValue
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
