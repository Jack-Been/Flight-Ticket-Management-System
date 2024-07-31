package com.company.model;

import java.io.Serializable;

public class FlightType implements Serializable {
    private final String type;

    public FlightType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return type.equals(((FlightType) obj).type);
    }
}
