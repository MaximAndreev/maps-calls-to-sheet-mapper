package ru.avtomir.maps.calls.uploader.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static ru.avtomir.maps.calls.uploader.mapper.CallCount.NULL_CALL_COUNT;

public class TestData {

    private TestData() {
    }

    public static CallStat statN1() {
        return CallStatBuilder.aStat()
                .setBrand("Brand 1")
                .setRegion("Region 1")
                .setCallCount(
                        CallCountBuilder.aCount()
                                .setCost(1d)
                                .setIds(List.of(1, 2, 3))
                                .setTags(Map.of(Tag.SALE, 1, Tag.INSURANCE, 2))
                                .setCostSourceOnly(true)
                                .build())
                .build();
    }

    public static CallStat statN2() {
        return CallStatBuilder.aStat()
                .setBrand("Brand 2")
                .setRegion("Region 2")
                .setCallCount(
                        CallCountBuilder.aCount()
                                .setCost(2d)
                                .setIds(List.of(11, 22, 33))
                                .setTags(Map.of(Tag.PARTS, 1, Tag.SALE_FLEET, 2))
                                .setCostSourceOnly(true)
                                .build())
                .build();
    }

    public static CallStat statN3() {
        return CallStatBuilder.aStat()
                .setBrand("Brand 3")
                .setRegion("Region 3")
                .setCallCount(
                        CallCountBuilder.aCount()
                                .setCost(3d)
                                .setIds(List.of(111, 222, 333))
                                .setTags(Map.of(Tag.EQUIPMENT, 4, Tag.USED, 5))
                                .setCostSourceOnly(true)
                                .build())
                .build();
    }

    public static CallStatSummary summaryN1() {
        return CallStatSummaryBuilder.aSummary()
                .setBrand("Brand 1")
                .setRegion("Region 1")
                .setGoogle(CallCountBuilder.aCount()
                        .setCost(1d)
                        .setIds(List.of(1, 2, 3))
                        .setTags(Map.of(Tag.SALE, 1, Tag.INSURANCE, 2))
                        .setCostSourceOnly(true)
                        .build())
                .setYandex(
                        CallCountBuilder.aCount()
                                .setCost(2d)
                                .setIds(List.of(11, 22, 33))
                                .setTags(Map.of(Tag.PARTS, 1, Tag.SALE_FLEET, 2))
                                .setCostSourceOnly(true)
                                .build())
                .setTwogis(NULL_CALL_COUNT)
                .build();
    }

    public static CallStatSummary summaryN2() {
        return CallStatSummaryBuilder.aSummary()
                .setBrand("Brand 2")
                .setRegion("Region 2")
                .setGoogle(CallCountBuilder.aCount()
                        .setCost(3d)
                        .setIds(List.of(21, 22, 23))
                        .setTags(Map.of(Tag.SALE, 4, Tag.INSURANCE, 1, Tag.EQUIPMENT, 4))
                        .setCostSourceOnly(true)
                        .build())
                .setYandex(
                        CallCountBuilder.aCount()
                                .setCost(20.1d)
                                .setIds(List.of(24, 25, 26))
                                .setTags(Map.of(Tag.MISSED_CALLS, 1, Tag.SALE_FLEET, 2))
                                .setCostSourceOnly(true)
                                .build())
                .setTwogis(CallCountBuilder.aCount()
                        .setCost(0d)
                        .setIds(List.of(27, 28, 29))
                        .setTags(Map.of(Tag.NOT_ANSWERED50, 1, Tag.UUU, 2))
                        .setCostSourceOnly(false)
                        .build())
                .build();
    }

    public static CallStatSummary summaryN3() {
        return CallStatSummaryBuilder.aSummary()
                .setBrand("Brand 2")
                .setRegion("Region 2")
                .setGoogle(CallCountBuilder.aCount()
                        .setCost(13d)
                        .setIds(List.of(21, 22, 23))
                        .setTags(Map.of(Tag.SALE, 3, Tag.INSURANCE, 5, Tag.EQUIPMENT, 2))
                        .setCostSourceOnly(true)
                        .build())
                .setYandex(
                        CallCountBuilder.aCount()
                                .setCost(20.1d)
                                .setIds(List.of(24, 25, 26))
                                .setTags(Map.of(Tag.MISSED_CALLS, 1, Tag.SALE_FLEET, 5))
                                .setCostSourceOnly(true)
                                .build())
                .setTwogis(CallCountBuilder.aCount()
                        .setCost(1d)
                        .setIds(List.of(27, 28, 29))
                        .setTags(Map.of(Tag.SALE, 4, Tag.UUU, 2))
                        .setCostSourceOnly(false)
                        .build())
                .build();
    }

    public static MonthSummary monthSummaryN1() {
        return new MonthSummary(
                summaryN1(),
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 31));
    }

    public static MonthSummary monthSummaryN2() {
        return new MonthSummary(
                summaryN2(),
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 31));
    }

    public static MonthSummary monthSummaryN3() {
        return new MonthSummary(
                summaryN3(),
                LocalDate.of(2019, 2, 1),
                LocalDate.of(2019, 2, 14));
    }

}
