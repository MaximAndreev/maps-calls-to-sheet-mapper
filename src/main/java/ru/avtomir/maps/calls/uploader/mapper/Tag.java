package ru.avtomir.maps.calls.uploader.mapper;

public enum Tag {
    SALE,
    SERVICE,
    PARTS,
    EQUIPMENT,
    NON_TARGET,
    OTHERS,
    SALE_FLEET,
    USED,
    INSURANCE,
    UUU,
    NOT_ANSWERED50,
    MISSED_CALLS,
    NOT_SWITCHED_CALLS,
    BAD_CONNECTION;

    public boolean isSale() {
        return this == SALE || this == SALE_FLEET;
    }

    public boolean isService() {
        return this == SERVICE || this == PARTS || this == EQUIPMENT;
    }
}
