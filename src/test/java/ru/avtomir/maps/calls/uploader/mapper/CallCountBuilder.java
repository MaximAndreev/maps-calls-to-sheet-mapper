package ru.avtomir.maps.calls.uploader.mapper;

import java.util.List;
import java.util.Map;

public class CallCountBuilder {
    private List<Integer> ids;
    private Map<Tag, Integer> tags;
    private Double cost;
    private boolean costSourceOnly;

    private CallCountBuilder() {
    }

    public static CallCountBuilder aCount() {
        return new CallCountBuilder();
    }

    public CallCountBuilder setIds(List<Integer> ids) {
        this.ids = ids;
        return this;
    }

    public CallCountBuilder setTags(Map<Tag, Integer> tags) {
        this.tags = tags;
        return this;
    }

    public CallCountBuilder setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public CallCountBuilder setCostSourceOnly(boolean costSourceOnly) {
        this.costSourceOnly = costSourceOnly;
        return this;
    }

    public CallCount build() {
        return new CallCount(ids, tags, cost, costSourceOnly);
    }
}