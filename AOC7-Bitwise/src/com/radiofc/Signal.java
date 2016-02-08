package com.radiofc;

/**
 * Created by ESHAMCC on 08/02/2016.
 */
public class Signal {

    private String name;
    private String wire;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWire() {
        return wire;
    }

    public void setWire(String wire) {
        this.wire = wire;
    }


public Signal(String name, String wire) {
    this.name = name;
    this.wire = wire;
}

}
