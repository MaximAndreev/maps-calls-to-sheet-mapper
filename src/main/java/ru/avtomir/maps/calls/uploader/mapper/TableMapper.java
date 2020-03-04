package ru.avtomir.maps.calls.uploader.mapper;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface TableMapper<T> {

    Locale LOCALE = Locale.forLanguageTag("ru-RU");

    void setSource(List<T> stats);

    List<Map<String, String>> getTableBody();

    List<String> getTableHeaders();

    boolean dependsOnIndexOfFirstNonHeadersRow();

    int getIndexOfFirstNonHeadersRow();
}
