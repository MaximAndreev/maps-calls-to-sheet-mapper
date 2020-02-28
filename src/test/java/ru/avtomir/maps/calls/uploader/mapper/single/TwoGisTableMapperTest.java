package ru.avtomir.maps.calls.uploader.mapper.single;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avtomir.maps.calls.uploader.mapper.CallStat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoGisTableMapperTest extends AbstractTableMapper {

    private TwoGisTableMapper mapper;

    @BeforeEach
    void beforeEach() {
        mapper = new TwoGisTableMapper();
    }

    @Test
    void correct_mapping() {
        // given
        List<CallStat> stats = getTestStats();
        List<Map<String, String>> expected = getAsTwoGis();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        assertEquals(expected, result);
    }

    private List<Map<String, String>> getAsTwoGis() {
        return List.of(
                summaryRow(3),
                rowN1Map(4),
                rowN2Map(5),
                rowN3Map(6));
    }

    private Map<String, String> rowN1Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN1Map();
        row.put("Затраты", "1,00");
        setTwoGisSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private void setTwoGisSpecificFormulaColumns(Map<String, String> row, int absoluteRowNumberOfThisRow) {
        row.put("Всего звонков", String.format("" +
                "=(%2$s%1$s + %3$s%1$s + " +
                "%4$s%1$s + %5$s%1$s + %6$s%1$s)", absoluteRowNumberOfThisRow, "E", "F", "G", "H", "J"));
        row.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", absoluteRowNumberOfThisRow, "Q", "D"));
        row.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", absoluteRowNumberOfThisRow, "Q", "E", "F"));
        row.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", absoluteRowNumberOfThisRow, "Q", "G", "H", "J"));
    }

    private Map<String, String> rowN2Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsOfInfoRowN2Map();
        row.put("Затраты", "2,00");
        setTwoGisSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private Map<String, String> rowN3Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN3Map();
        row.put("Затраты", "3,00");
        setTwoGisSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    // Magic number 3 and 15 depends on desired representation in Google Sheets table
    private Map<String, String> summaryRow(int absoluteRowNumberOfSummaryRow) {
        Map<String, String> summaryRow = generalColumnsOfInfoRowSummary(3);
        int columnIdxAfterSummary = 15;
        summaryRow.put("Затраты", summaryOfValueForColumn(++columnIdxAfterSummary));
        summaryRow.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", absoluteRowNumberOfSummaryRow, "Q", "D"));
        summaryRow.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", absoluteRowNumberOfSummaryRow, "Q", "E", "F"));
        summaryRow.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", absoluteRowNumberOfSummaryRow, "Q", "G", "H", "J"));
        return summaryRow;
    }

    @Test
    void headers_order() {
        assertEquals(Arrays.asList(
                "id",
                "Марка",
                "Город",
                "Всего звонков",
                "Продажа",
                "Продажа Юр. Лицо",
                "Сервис",
                "Доп. оборудование",
                "БУ",
                "Запчасти",
                "Страхование",
                "Прочее",
                "Нецелевой звонок",
                "Упущенный звонок",
                "Не отвеченный 50+сек",
                "Непереключенный звонок",
                "Затраты",
                "CPA общий",
                "CPA ОП",
                "CPA сервис"),
                mapper.getTableHeaders());
    }
}
