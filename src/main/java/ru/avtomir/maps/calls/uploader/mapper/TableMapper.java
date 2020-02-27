package ru.avtomir.maps.calls.uploader.mapper;

import java.util.List;
import java.util.Map;

public interface TableMapper<T> {

    void setSource(List<T> stats);

    List<Map<String, String>> getTableBody();

    List<String> getTableHeaders();
}
