package ru.avtomir.maps.calls.uploader.mapper;

public enum Tag {
    SALE("Продажа"),
    SERVICE("Сервис"),
    PARTS("Запчасти"),
    EQUIPMENT("Доп. оборудование"),
    NON_TARGET("Нецелевой звонок"),
    OTHERS("Прочее"),
    SALE_FLEET("Продажа Юр. Лицо"),
    USED("БУ"),
    INSURANCE("Страхование"),
    UUU("УУУ"),
    NOT_ANSWERED50("Не отвеченный 50+сек"),
    MISSED_CALLS("Упущенный звонок"),
    NOT_SWITCHED_CALLS("Непереключенный звонок"),
    BAD_CONNECTION("Плохая связь");

    private String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public boolean isSale() {
        return this == SALE || this == SALE_FLEET;
    }

    public boolean isService() {
        return this == SERVICE || this == PARTS || this == EQUIPMENT;
    }
}
