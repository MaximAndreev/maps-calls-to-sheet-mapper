package ru.avtomir.maps.calls.uploader.mapper;

import ru.avtomir.maps.calls.uploader.mapper.graphic.GraphicTableMapperWithLinearPredict;
import ru.avtomir.maps.calls.uploader.mapper.single.GoogleTableMapper;
import ru.avtomir.maps.calls.uploader.mapper.single.TwoGisTableMapper;
import ru.avtomir.maps.calls.uploader.mapper.single.YandexTableMapper;
import ru.avtomir.maps.calls.uploader.mapper.summary.SummaryTableMapper;

public class TableMapperFactory {

    private TableMapperFactory() {
    }

    public static TableMapper<CallStat> yandexTableMapper() {
        return new YandexTableMapper(3);
    }

    public static TableMapper<CallStat> googleTableMapper() {
        return new GoogleTableMapper(3);
    }

    public static TableMapper<CallStat> twoGisTableMapper() {
        return new TwoGisTableMapper(3);
    }

    public static TableMapper<CallStatSummary> summaryTableMapper() {
        return new SummaryTableMapper(4);
    }

    public static TableMapper<MonthSummary> graphicTableMapperWithLinearPredict() {
        return new GraphicTableMapperWithLinearPredict();
    }
}
