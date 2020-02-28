package ru.avtomir.maps.calls.uploader.mapper.summary;

import ru.avtomir.maps.calls.uploader.mapper.CallStatSummary;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;

import java.util.*;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class SummaryTableMapper implements TableMapper<CallStatSummary> {

    private static final int FIRST_ROW = 3;

    private List<Map<String, String>> asTable = new ArrayList<>();

    @Override
    public void setSource(List<CallStatSummary> stats) {
        stats.forEach(this::createRow);
        asTable.add(0, makeSummaryRow(stats));
    }

    private void createRow(CallStatSummary summary) {
        Map<String, String> row = new HashMap<>();
        row.put("марка", summary.getBrand());
        row.put("город", summary.getRegion());

        row.put("sale_all", valueOf(summary.allSale()));
        row.put("service_all", valueOf(summary.allService()));
        row.put("cpa_all", format("%.2f", summary.allCpa()));

        row.put("sale_2gis", valueOf(summary.twoGisSale()));
        row.put("service_2gis", valueOf(summary.twoGisService()));
        row.put("cpa_2gis", format("%.2f", summary.twoGisCpa()));

        row.put("sale_yandex", valueOf(summary.yandexSale()));
        row.put("service_yandex", valueOf(summary.yandexService()));
        row.put("cpa_yandex", format("%.2f", summary.yandexCpa()));

        row.put("sale_google", valueOf(summary.googleSale()));
        row.put("service_google", valueOf(summary.googleService()));

        asTable.add(row);
    }

    private Map<String, String> makeSummaryRow(List<CallStatSummary> stats) {
        int rows = stats.size();
        Double allCostSum = stats.stream().mapToDouble(CallStatSummary::allCost).sum();
        Double twoGisCostSum = stats.stream().mapToDouble(CallStatSummary::twoGisCost).sum();
        Double yandexCostSum = stats.stream().mapToDouble(CallStatSummary::yandexCost).sum();
        return makeSummaryRow(rows, allCostSum, twoGisCostSum, yandexCostSum);
    }

    private Map<String, String> makeSummaryRow(int rows,
                                               Double allCostSum,
                                               Double twoGisCostSum,
                                               Double yandexCostSum) {
        Map<String, String> map = new HashMap<>();
        int step = 2;
        int rowsLast = rows + FIRST_ROW + 1;
        map.put("марка", "Все");
        map.put("город", "Все");
        map.put("sale_all", sumColumnFormula('C', FIRST_ROW + step, rowsLast));
        map.put("service_all", sumColumnFormula('D', FIRST_ROW + step, rowsLast));
        map.put("cpa_all", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", allCostSum.intValue(), FIRST_ROW + 1, "C", "D"));
        map.put("sale_2gis", sumColumnFormula('F', FIRST_ROW + step, rowsLast));
        map.put("service_2gis", sumColumnFormula('G', FIRST_ROW + step, rowsLast));
        map.put("cpa_2gis", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", twoGisCostSum.intValue(), FIRST_ROW + 1, "F", "G"));
        map.put("sale_yandex", sumColumnFormula('I', FIRST_ROW + step, rowsLast));
        map.put("service_yandex", sumColumnFormula('J', FIRST_ROW + step, rowsLast));
        map.put("cpa_yandex", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", yandexCostSum.intValue(), FIRST_ROW + 1, "I", "J"));
        map.put("sale_google", sumColumnFormula('L', FIRST_ROW + step, rowsLast));
        map.put("service_google", sumColumnFormula('M', FIRST_ROW + step, rowsLast));
        return map;
    }

    private String sumColumnFormula(char charLetter, Integer firstRow, Integer lastRow) {
        String columnLetter = String.valueOf(charLetter);
        return String.format("=СУММ(%s%s:%s%s)", columnLetter, firstRow, columnLetter, lastRow);
    }

    @Override
    public List<Map<String, String>> getTableBody() {
        return Collections.unmodifiableList(asTable);
    }

    @Override
    public List<String> getTableHeaders() {
        return Arrays.asList("марка", "город",
                "sale_all", "service_all", "cpa_all",
                "sale_2gis", "service_2gis", "cpa_2gis",
                "sale_yandex", "service_yandex", "cpa_yandex",
                "sale_google", "service_google");
    }
}
