package com.radiofc;

/**
 * Created by ESHAMCC on 07/02/2016.
 */
public class Wire {
    private String identifier;
    private int signal;


    public Wire(String identifier, int signal) {
        this.identifier = identifier;
        this.signal = signal;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getSignal() {
        return signal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wire wire = (Wire) o;

        if (signal != wire.signal) return false;
        return identifier.equals(wire.identifier);

    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + signal;
        return result;
    }
}
