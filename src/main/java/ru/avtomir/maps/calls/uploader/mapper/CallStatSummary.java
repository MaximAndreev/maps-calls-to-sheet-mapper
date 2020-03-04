package ru.avtomir.maps.calls.uploader.mapper;

import java.util.Objects;

public class CallStatSummary {
    private final String brand;
    private final String region;
    private final CallCount yandex;
    private final CallCount google;
    private final CallCount twogis;

    public CallStatSummary(String brand,
                           String region,
                           CallCount yandex,
                           CallCount google,
                           CallCount twogis) {
        this.brand = Objects.requireNonNull(brand);
        this.region = Objects.requireNonNull(region);
        this.yandex = Objects.requireNonNull(yandex);
        this.google = Objects.requireNonNull(google);
        this.twogis = Objects.requireNonNull(twogis);
    }

    public String getBrand() {
        return brand;
    }

    public String getRegion() {
        return region;
    }

    public Integer allSale() {
        int saleAll = 0;
        saleAll += yandexSale();
        saleAll += googleSale();
        saleAll += twoGisSale();
        return saleAll;
    }

    public int yandexSale() {
        return yandex.getSaleCount();
    }

    public int googleSale() {
        return google.getSaleCount();
    }

    public int twoGisSale() {
        return twogis.getSaleCount();
    }

    public Integer allService() {
        int serviceAll = 0;
        serviceAll += yandexService();
        serviceAll += googleService();
        serviceAll += twoGisService();
        return serviceAll;
    }

    public int yandexService() {
        return yandex.getServiceCount();
    }

    public int googleService() {
        return google.getServiceCount();
    }

    public int twoGisService() {
        return twogis.getServiceCount();
    }

    public Double allCost() {
        double costAll = 0d;
        costAll += yandexCost();
        costAll += googleCost();
        costAll += twoGisCost();
        return costAll;
    }

    public double yandexCost() {
        return yandex.cost();
    }

    public double googleCost() {
        return google.cost();
    }

    public double twoGisCost() {
        return twogis.cost();
    }

    public Double allCpa() {
        int allLeads = allSale() + allService();
        return allLeads != 0 ? allCost() / allLeads : 0d;
    }

    public Double twoGisCpa() {
        int twoGisLeads = twoGisSale() + twoGisService();
        return twoGisLeads != 0 ? twoGisCost() / twoGisLeads : 0d;
    }

    public Double yandexCpa() {
        int yandexLeads = yandexSale() + yandexService();
        return yandexLeads != 0 ? yandexCost() / yandexLeads : 0d;

    }
}
