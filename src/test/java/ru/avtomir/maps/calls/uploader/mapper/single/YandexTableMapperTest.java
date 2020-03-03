package ru.avtomir.maps.calls.uploader.mapper.single;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.TableMapperFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class YandexTableMapperTest extends AbstractTableMapper {

    private TableMapper<CallStat> mapper;

    @BeforeEach
    private void beforeEach() {
        mapper = TableMapperFactory.yandexTableMapper();
    }

    @Test
    public void correct_mapping() {
        // given
        List<CallStat> stats = getTestStats();
        List<Map<String, String>> expected = getAsYandex();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        assertEquals(expected, result);
    }

    private List<Map<String, String>> getAsYandex() {
        return List.of(
                summaryRow(3),
                rowN1Map(4),
                rowN2Map(5),
                rowN3Map(6));
    }

    private Map<String, String> rowN1Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN1Map();
        row.put("Затраты", "1,00");
        setYandexSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private void setYandexSpecificFormulaColumns(Map<String, String> row, int absoluteRowNumberOfThisRow) {
        row.put("Только Яндекс?", "Да");
        row.put("Всего звонков", format("" +
                "=(%2$s%1$s + %3$s%1$s " +
                "+ %4$s%1$s + %5$s%1$s +%6$s%1$s)", absoluteRowNumberOfThisRow, "F", "G", "H", "I", "K"));
        row.put("CPA общий", format("=%2$s%1$s/%3$s%1$s", absoluteRowNumberOfThisRow, "R", "E"));
        row.put("CPA ОП", format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", absoluteRowNumberOfThisRow, "R", "F", "G"));
        row.put("CPA сервис", format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", absoluteRowNumberOfThisRow, "R", "H", "I", "K"));
    }

    private Map<String, String> rowN2Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsOfInfoRowN2Map();
        row.put("Затраты", "2,00");
        setYandexSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private Map<String, String> rowN3Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN3Map();
        row.put("Затраты", "3,00");
        setYandexSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    // Magic numbers 4 and 16 depend on desired representation in Google Sheets table
    private Map<String, String> summaryRow(int absoluteRowNumberOfSummaryRow) {
        Map<String, String> summaryRow = generalColumnsOfInfoRowSummary(4);
        int columnIdxAfterSummary = 16;
        summaryRow.put("Только Яндекс?", "-");
        summaryRow.put("Затраты", summaryOfValueForColumn(++columnIdxAfterSummary));
        summaryRow.put("CPA общий", format("=%2$s%1$s/%3$s%1$s", absoluteRowNumberOfSummaryRow, "R", "E"));
        summaryRow.put("CPA ОП", format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", absoluteRowNumberOfSummaryRow, "R", "F", "G"));
        summaryRow.put("CPA сервис", format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", absoluteRowNumberOfSummaryRow, "R", "H", "I", "K"));
        return summaryRow;
    }

    @Test
    void headers_order() {
        assertEquals(Arrays.asList(
                "id",
                "Марка",
                "Город",
                "Только Яндекс?",
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

    @Test
    void tableBody_have_all_headers() {
        // given
        List<CallStat> stats = getTestStats();

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
