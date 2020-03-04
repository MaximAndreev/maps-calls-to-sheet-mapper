package ru.avtomir.maps.calls.uploader.mapper;

public class CallStatSummaryBuilder {
    private String brand;
    private String region;
    private CallCount yandex;
    private CallCount google;
    private CallCount twogis;

    private CallStatSummaryBuilder() {
    }

    public static CallStatSummaryBuilder aSummary() {
        return new CallStatSummaryBuilder();
    }

    public CallStatSummaryBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CallStatSummaryBuilder setRegion(String region) {
        this.region = region;
        return this;
    }

    public CallStatSummaryBuilder setYandex(CallCount yandex) {
        this.yandex = yandex;
        return this;
    }

    public CallStatSummaryBuilder setGoogle(CallCount google) {
        this.google = google;
        return this;
    }

    public CallStatSummaryBuilder setTwogis(CallCount twogis) {
        this.twogis = twogis;
        return this;
    }

    public CallStatSummary build() {
        return new CallStatSummary(brand, region, yandex, google, twogis);
    }
}