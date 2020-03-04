package ru.avtomir.maps.calls.uploader.mapper;

import java.time.LocalDate;

public class MonthSummary {
    private final CallStatSummary callStatSummary;
    private final LocalDate month;
    private final LocalDate statsDay;

    /**
     * @param month    a date with day = 1.
     * @param statsDay a date of the same year and month as {@code month}.
     */
    public MonthSummary(CallStatSummary callStatSummary,
                        LocalDate month,
                        LocalDate statsDay) {
        this.callStatSummary = callStatSummary;
        this.month = month;
        this.statsDay = statsDay;
    }

    public LocalDate getMonth() {
        return month;
    }

    public LocalDate getDate() {
        return statsDay;
    }

    public String getBrand() {
        return callStatSummary.getBrand();
    }

    public String getCity() {
        return callStatSummary.getRegion();
    }

    public int getLeadsSale() {
        return callStatSummary.allSale();
    }

    public int getLeadsService() {
        return callStatSummary.allService();
    }

    public double getCost() {
        return callStatSummary.allCost();
    }
}
