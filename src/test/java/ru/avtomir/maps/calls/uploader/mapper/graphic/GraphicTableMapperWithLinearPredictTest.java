package ru.avtomir.maps.calls.uploader.mapper.graphic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avtomir.maps.calls.uploader.mapper.MonthSummary;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.TableMapperFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.avtomir.maps.calls.uploader.mapper.TestData.*;

public class GraphicTableMapperWithLinearPredictTest {

    private TableMapper<MonthSummary> mapper;

    @BeforeEach
    void beforeEach() {
        mapper = TableMapperFactory.graphicTableMapperWithLinearPredict();
    }

    @Test
    void correct_mapping_for_linear_predict() {
        // given
        List<MonthSummary> stats = getTestData();
        List<Map<String, String>> expected = getExpected();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        Assertions.assertEquals(expected, result);
    }

    private List<MonthSummary> getTestData() {
        return List.of(monthSummaryN1(), monthSummaryN2(), monthSummaryN3());
    }

    private List<Map<String, String>> getExpected() {
        return List.of(monthSummaryRowN1(), monthSummaryRowN2(), monthSummaryRowN3WithLinearPrediction());
    }

    private Map<String, String> monthSummaryRowN1() {
        Map<String, String> row = new HashMap<>();
        row.put("Месяц", "2019-01-01");
        row.put("Марка", "Brand 1");
        row.put("Город", "Region 1");
        row.put("Продажи и Сервис Все", "4");
        row.put("Продажи Все", "3");
        row.put("Сервис Все", "1");
        row.put("CPA", "0,75");
        row.put("Затраты", "3");
        return row;
    }

    private Map<String, String> monthSummaryRowN2() {
        Map<String, String> row = new HashMap<>();
        row.put("Месяц", "2019-01-01");
        row.put("Марка", "Brand 2");
        row.put("Город", "Region 2");
        row.put("Продажи и Сервис Все", "10");
        row.put("Продажи Все", "6");
        row.put("Сервис Все", "4");
        row.put("CPA", "2,31");
        row.put("Затраты", "23");
        return row;
    }

    private Map<String, String> monthSummaryRowN3WithLinearPrediction() {
        Map<String, String> row = new HashMap<>();
        row.put("Месяц", "2019-02-01");
        row.put("Марка", "Brand 2");
        row.put("Город", "Region 2");
        row.put("Продажи и Сервис Все", "28");  // before prediction: 14
        row.put("Продажи Все", "24");  // before prediction: 12
        row.put("Сервис Все", "4");  // before prediction: 2
        row.put("CPA", "2,44");
        row.put("Затраты", "68");  // before prediction: 34.1
        return row;
    }

    @Test
    void headers_order() {
        assertEquals(Arrays.asList(
                "Месяц", "Марка", "Город",
                "Продажи и Сервис Все", "Продажи Все", "Сервис Все", "CPA", "Затраты"),
                mapper.getTableHeaders());
    }

    @Test
    void tableBody_have_all_headers() {
        // given
        List<MonthSummary> stats = getTestData();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        assertTableBodyContainsAllHeaders(result);
    }

    private void assertTableBodyContainsAllHeaders(List<Map<String, String>> result) {
        result.forEach(this::assertHaveHeaders);

    }

    private void assertHaveHeaders(Map<String, String> row) {
        HashSet<String> headers = new HashSet<>(mapper.getTableHeaders());
        Assertions.assertEquals(headers, row.keySet());
    }
}
