package ru.avtomir.maps.calls.uploader.mapper.single;

import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TestData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class AbstractTableMapper {

    private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    protected List<CallStat> getTestStats() {
        return List.of(TestData.statN1(), TestData.statN2(), TestData.statN3());
    }

    protected Map<String, String> generalColumnsInfoRowN1Map() {
        Map<String, String> row = new HashMap<>();
        row.put("id", "1;2;3");
        row.put("Марка", "Brand 1");
        row.put("Город", "Region 1");
        row.put("Продажа", "1");
        row.put("Продажа Юр. Лицо", "0");
        row.put("Сервис", "0");
        row.put("Доп. оборудование", "0");
        row.put("БУ", "0");
        row.put("Запчасти", "0");
        row.put("Страхование", "2");
        row.put("Прочее", "0");
        row.put("Нецелевой звонок", "0");
        row.put("Упущенный звонок", "0");
        row.put("Не отвеченный 50+сек", "0");
        row.put("Непереключенный звонок", "0");
        return row;
    }

    protected Map<String, String> generalColumnsOfInfoRowN2Map() {
        Map<String, String> row = new HashMap<>();
        row.put("id", "11;22;33");
        row.put("Марка", "Brand 2");
        row.put("Город", "Region 2");
        row.put("Продажа", "0");
        row.put("Продажа Юр. Лицо", "2");
        row.put("Сервис", "0");
        row.put("Доп. оборудование", "0");
        row.put("БУ", "0");
        row.put("Запчасти", "1");
        row.put("Страхование", "0");
        row.put("Прочее", "0");
        row.put("Нецелевой звонок", "0");
        row.put("Упущенный звонок", "0");
        row.put("Не отвеченный 50+сек", "0");
        row.put("Непереключенный звонок", "0");
        return row;
    }

    protected Map<String, String> generalColumnsInfoRowN3Map() {
        Map<String, String> row = new HashMap<>();
        row.put("id", "111;222;333");
        row.put("Марка", "Brand 3");
        row.put("Город", "Region 3");
        row.put("Продажа", "0");
        row.put("Продажа Юр. Лицо", "0");
        row.put("Сервис", "0");
        row.put("Доп. оборудование", "4");
        row.put("БУ", "5");
        row.put("Запчасти", "0");
        row.put("Страхование", "0");
        row.put("Прочее", "0");
        row.put("Нецелевой звонок", "0");
        row.put("Упущенный звонок", "0");
        row.put("Не отвеченный 50+сек", "0");
        row.put("Непереключенный звонок", "0");
        return row;
    }

    protected Map<String, String> generalColumnsOfInfoRowSummary(int firstColumnWithSumFormula) {
        Map<String, String> summaryRow = new HashMap<>();
        summaryRow.put("id", "-");
        summaryRow.put("Марка", "Все");
        summaryRow.put("Город", "Все");
        summaryRow.put("Всего звонков", summaryOfValueForColumn(firstColumnWithSumFormula));
        summaryRow.put("Продажа", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Продажа Юр. Лицо", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Сервис", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Доп. оборудование", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("БУ", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Запчасти", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Страхование", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Прочее", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Нецелевой звонок", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Упущенный звонок", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Не отвеченный 50+сек", summaryOfValueForColumn(++firstColumnWithSumFormula));
        summaryRow.put("Непереключенный звонок", summaryOfValueForColumn(++firstColumnWithSumFormula));
        return summaryRow;
    }

    protected String summaryOfValueForColumn(int columnIndex) {
        int absoluteFirstRowWithNonSummaryData = 4;
        int absoluteLastRowWithNonSummaryData = 6;
        return format(
                "=СУММ(%1$s%2$s:%1$s%3$s)",
                ALPHABET[columnIndex],
                absoluteFirstRowWithNonSummaryData,
                absoluteLastRowWithNonSummaryData);
    }
}
