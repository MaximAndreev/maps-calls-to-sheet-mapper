package ru.avtomir.maps.calls.uploader.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CallCount {

    public static final CallCount NULL_CALL_COUNT = new CallCount(
            Collections.emptyList(),
            Collections.emptyMap(),
            0d,
            true);

    private List<Integer> ids;
    private Map<Tag, Integer> tags;
    private Double cost;
    private boolean costSourceOnly;

    public CallCount(List<Integer> ids,
                     Map<Tag, Integer> tags,
                     Double cost,
                     boolean costSourceOnly) {
        this.cost = Objects.requireNonNull(cost);
        this.costSourceOnly = costSourceOnly;
        this.ids = Objects.requireNonNull(ids);
        this.tags = Objects.requireNonNull(tags);
    }

    public List<Integer> getIds() {
        return Collections.unmodifiableList(ids);
    }

    public boolean isCostSourceOnly() {
        return costSourceOnly;
    }

    public Integer getCount(Tag tag) {
        return tags.getOrDefault(tag, 0);
    }

    public Double cost() {
        return cost;
    }

    public int getSaleCount() {
        return tags.entrySet().stream()
                .filter(entry -> entry.getKey().isSale())
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public int getServiceCount() {
        return tags.entrySet().stream()
                .filter(entry -> entry.getKey().isService())
                .mapToInt(Map.Entry::getValue)
                .sum();
    }
}
