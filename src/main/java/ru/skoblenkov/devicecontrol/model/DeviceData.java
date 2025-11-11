package ru.skoblenkov.devicecontrol.model;

public class DeviceData {
    private final String payload;

    public DeviceData(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
