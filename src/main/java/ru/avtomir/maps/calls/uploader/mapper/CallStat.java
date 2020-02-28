package ru.avtomir.maps.calls.uploader.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CallStat {
    private String brand;
    private String region;
    private CallCount callCount;

    public CallStat(String brand,
                    String region,
                    CallCount callCount) {
        this.brand = brand;
        this.region = region;
        this.callCount = callCount;
    }

    public List<String> getProfileIds() {
        return callCount.getIds().stream().map(String::valueOf).collect(Collectors.toList());
    }

    public String getBrand() {
        return brand;
    }

    public String getRegion() {
        return region;
    }

    public boolean isCostSourceOnly() {
        return callCount.isCostSourceOnly();
    }

    public String getCountAsString(Tag tag) {
        return callCount.getCount(tag).toString();
    }

    public String getCostAsString() {
        return String.format(Locale.forLanguageTag("ru-RU"), "%.2f", cost());
    }

    public Double cost() {
        return callCount.cost();
    }
}
