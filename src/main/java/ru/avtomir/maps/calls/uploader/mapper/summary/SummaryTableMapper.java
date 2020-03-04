package ru.avtomir.maps.calls.uploader.mapper.summary;

import ru.avtomir.maps.calls.uploader.mapper.CallStatSummary;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;

import java.util.*;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class SummaryTableMapper implements TableMapper<CallStatSummary> {

    private final int firstNonHeadersRow;
    private List<Map<String, String>> asTable = new ArrayList<>();

    public SummaryTableMapper(int absoluteRowIndexOfFirstRowWithData) {
        this.firstNonHeadersRow = absoluteRowIndexOfFirstRowWithData;
    }

    @Override
    public void setSource(List<CallStatSummary> stats) {
        addSummaryRow(stats);
        addStatRows(stats);
    }

    private void addSummaryRow(List<CallStatSummary> stats) {
        asTable.add(summaryRow(stats));
    }

    private Map<String, String> summaryRow(List<CallStatSummary> stats) {
        int countOfStatRows = stats.size();
        Double allCostSum = stats.stream().mapToDouble(CallStatSummary::allCost).sum();
        Double twoGisCostSum = stats.stream().mapToDouble(CallStatSummary::twoGisCost).sum();
        Double yandexCostSum = stats.stream().mapToDouble(CallStatSummary::yandexCost).sum();
        return summaryRow(countOfStatRows, allCostSum, twoGisCostSum, yandexCostSum);
    }

    private Map<String, String> summaryRow(int countOfStatRows,
                                           Double allCostSum,
                                           Double twoGisCostSum,
                                           Double yandexCostSum) {
        Map<String, String> map = new HashMap<>();
        int firstStatRow = firstNonHeadersRow + 1;
        int lastStatRow = firstNonHeadersRow + countOfStatRows;
        map.put("марка", "Все");
        map.put("город", "Все");
        map.put("sale_all", sumColumnFormula('C', firstStatRow, lastStatRow));
        map.put("service_all", sumColumnFormula('D', firstStatRow, lastStatRow));
        map.put("cpa_all", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", allCostSum.intValue(), firstNonHeadersRow, "C", "D"));
        map.put("sale_2gis", sumColumnFormula('F', firstStatRow, lastStatRow));
        map.put("service_2gis", sumColumnFormula('G', firstStatRow, lastStatRow));
        map.put("cpa_2gis", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", twoGisCostSum.intValue(), firstNonHeadersRow, "F", "G"));
        map.put("sale_yandex", sumColumnFormula('I', firstStatRow, lastStatRow));
        map.put("service_yandex", sumColumnFormula('J', firstStatRow, lastStatRow));
        map.put("cpa_yandex", String.format("=%1$s/(%3$s%2$s + %4$s%2$s)", yandexCostSum.intValue(), firstNonHeadersRow, "I", "J"));
        map.put("sale_google", sumColumnFormula('L', firstStatRow, lastStatRow));
        map.put("service_google", sumColumnFormula('M', firstStatRow, lastStatRow));
        return map;
    }

    private void addStatRows(List<CallStatSummary> stats) {
        stats.forEach(this::statRow);
    }

    private void statRow(CallStatSummary summary) {
        Map<String, String> row = new HashMap<>();
        row.put("марка", summary.getBrand());
        row.put("город", summary.getRegion());

        row.put("sale_all", valueOf(summary.allSale()));
        row.put("service_all", valueOf(summary.allService()));
        row.put("cpa_all", format(LOCALE, "%.2f", summary.allCpa()));

        row.put("sale_2gis", valueOf(summary.twoGisSale()));
        row.put("service_2gis", valueOf(summary.twoGisService()));
        row.put("cpa_2gis", format(LOCALE, "%.2f", summary.twoGisCpa()));

        row.put("sale_yandex", valueOf(summary.yandexSale()));
        row.put("service_yandex", valueOf(summary.yandexService()));
        row.put("cpa_yandex", format(LOCALE, "%.2f", summary.yandexCpa()));

        row.put("sale_google", valueOf(summary.googleSale()));
        row.put("service_google", valueOf(summary.googleService()));

        asTable.add(row);
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
