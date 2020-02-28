package ru.avtomir.maps.calls.uploader.mapper;

public class CallStatBuilder {
    private String brand;
    private String region;
    private CallCount callCount;

    private CallStatBuilder() {
    }

    public static CallStatBuilder aStat() {
        return new CallStatBuilder();
    }

    public CallStatBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CallStatBuilder setRegion(String region) {
        this.region = region;
        return this;
    }

    public CallStatBuilder setCallCount(CallCount callCount) {
        this.callCount = callCount;
        return this;
    }

    public CallStat build() {
        return new CallStat(brand, region, callCount);
    }
}