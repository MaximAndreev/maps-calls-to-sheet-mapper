package ru.avtomir.maps.calls.uploader.mapper.single;

import ru.avtomir.maps.calls.uploader.mapper.TableMapper;

import java.util.List;
import java.util.Map;

// TODO: simply copy old methods and write one Chicago-style test fro verifying correctness
public class YandexTableMapper<CallStat> implements TableMapper<CallStat> {

    @Override
    public void setSource(List<CallStat> stats) {

    }

    @Override
    public List<Map<String, String>> getTableBody() {
        return null;
    }

    @Override
    public List<String> getTableHeaders() {
        return null;
    }
}
