package ru.avtomir.maps.calls.uploader.mapper.summary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avtomir.maps.calls.uploader.mapper.CallStatSummary;
import ru.avtomir.maps.calls.uploader.mapper.TestData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SummaryTableMapperTest {

    private SummaryTableMapper mapper;

    @BeforeEach
    void beforeEach() {
        mapper = new SummaryTableMapper();
    }

    @Test
    void correct_mapping() {
        // given
        List<CallStatSummary> stats = getTestData();
        List<Map<String, String>> expected = getSummary();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        Assertions.assertEquals(expected, result);
    }

    private List<CallStatSummary> getTestData() {
        return List.of(TestData.summaryN1(), TestData.summaryN2());
    }

    private List<Map<String, String>> getSummary() {
        return List.of(summaryRow(4, 2), rowN1Map(), rowN2Map());
    }

    private Map<String, String> summaryRow(int absoluteRowNumberOfSummaryRow, int countOfIndividualRowsInTable) {
        int rowFirst = absoluteRowNumberOfSummaryRow + 1;
        int rowLast = absoluteRowNumberOfSummaryRow + countOfIndividualRowsInTable;

        Map<String, String> row = new HashMap<>();
        row.put("марка", "Все");
        row.put("город", "Все");

        row.put("sale_all", sumColumnFormula('C', rowFirst, rowLast));
        row.put("service_all", sumColumnFormula('D', rowFirst, rowLast));
        row.put("cpa_all", format("=%1$.0f/(%3$s%2$s + %4$s%2$s)", getAllCostSum(), absoluteRowNumberOfSummaryRow, "C", "D"));

        row.put("sale_2gis", sumColumnFormula('F', rowFirst, rowLast));
        row.put("service_2gis", sumColumnFormula('G', rowFirst, rowLast));
        row.put("cpa_2gis", format("=%1$.0f/(%3$s%2$s + %4$s%2$s)", getTwoGisCostSum(), absoluteRowNumberOfSummaryRow, "F", "G"));

        row.put("sale_yandex", sumColumnFormula('I', rowFirst, rowLast));
        row.put("service_yandex", sumColumnFormula('J', rowFirst, rowLast));
        row.put("cpa_yandex", format("=%1$.0f/(%3$s%2$s + %4$s%2$s)", getYandexCostSum(), absoluteRowNumberOfSummaryRow, "I", "J"));

        row.put("sale_google", sumColumnFormula('L', rowFirst, rowLast));
        row.put("service_google", sumColumnFormula('M', rowFirst, rowLast));

        return row;
    }

    private double getYandexCostSum() {
        return getTestData().stream().mapToDouble(CallStatSummary::yandexCost).sum();
    }

    private double getTwoGisCostSum() {
        return getTestData().stream().mapToDouble(CallStatSummary::twoGisCost).sum();
    }

    private double getAllCostSum() {
        return getTestData().stream().mapToDouble(CallStatSummary::allCost).sum();
    }

    private String sumColumnFormula(char charLetter, Integer firstRow, Integer lastRow) {
        String columnLetter = String.valueOf(charLetter);
        return format("=СУММ(%1$s%2$s:%1$s%3$s)", columnLetter, firstRow, lastRow);
    }

    private Map<String, String> rowN1Map() {
        Map<String, String> row = new HashMap<>();
        row.put("марка", "Brand 1");
        row.put("город", "Region 1");

        row.put("sale_all", "3");
        row.put("service_all", "1");
        row.put("cpa_all", "0,75");

        row.put("sale_2gis", "0");
        row.put("service_2gis", "0");
        row.put("cpa_2gis", "0,00");

        row.put("sale_yandex", "2");
        row.put("service_yandex", "1");
        row.put("cpa_yandex", "0,67");

        row.put("sale_google", "1");
        row.put("service_google", "0");
        return row;
    }

    private Map<String, String> rowN2Map() {
        Map<String, String> row = new HashMap<>();
        row.put("марка", "Brand 2");
        row.put("город", "Region 2");

        row.put("sale_all", "6");
        row.put("service_all", "4");
        row.put("cpa_all", "2,31");

        row.put("sale_2gis", "0");
        row.put("service_2gis", "0");
        row.put("cpa_2gis", "0,00");

        row.put("sale_yandex", "2");
        row.put("service_yandex", "0");
        row.put("cpa_yandex", "10,05");

        row.put("sale_google", "4");
        row.put("service_google", "4");
        return row;
    }

    @Test
    void headers_order() {
        assertEquals(Arrays.asList("марка", "город",
                "sale_all", "service_all", "cpa_all",
                "sale_2gis", "service_2gis", "cpa_2gis",
                "sale_yandex", "service_yandex", "cpa_yandex",
                "sale_google", "service_google"),
                mapper.getTableHeaders());
    }
}
