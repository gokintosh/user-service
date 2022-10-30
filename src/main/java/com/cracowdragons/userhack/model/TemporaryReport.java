package com.cracowdragons.userhack.model;

//TODO: refactor this class
public class TemporaryReport {

    String deviceId;
    Object data;

    @Override
    public String toString() {
        return String.format("Device: %s\n%s", deviceId, data);
    }
}
