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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleTableMapperTest extends AbstractTableMapper {

    private TableMapper<CallStat> mapper;

    @BeforeEach
    void beforeEach() {
        mapper = TableMapperFactory.googleTableMapper();
    }

    @Test
    void correct_mapping() {
        // given
        List<CallStat> stats = getTestStats();
        List<Map<String, String>> expected = getAsGoogle();

        // when
        mapper.setSource(stats);
        List<Map<String, String>> result = mapper.getTableBody();

        // then
        assertEquals(expected, result);
    }

    private List<Map<String, String>> getAsGoogle() {
        return List.of(
                summaryRow(),
                rowN1Map(4),
                rowN2Map(5),
                rowN3Map(6));
    }

    private Map<String, String> rowN1Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN1Map();
        row.put("Только Google?", "Да");
        setGoogleSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private void setGoogleSpecificFormulaColumns(Map<String, String> row, int absoluteRowNumberOfThisRow) {
        row.put("Всего звонков", format("=СУММ(%s%s:%s%s)", "F", absoluteRowNumberOfThisRow, "M", absoluteRowNumberOfThisRow));
    }

    private Map<String, String> rowN2Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsOfInfoRowN2Map();
        row.put("Только Google?", "Да");
        setGoogleSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    private Map<String, String> rowN3Map(int absoluteRowNumberOfThisRow) {
        Map<String, String> row = generalColumnsInfoRowN3Map();
        row.put("Только Google?", "Да");
        setGoogleSpecificFormulaColumns(row, absoluteRowNumberOfThisRow);
        return row;
    }

    // Magic number 4 depends on desired representation in Google Sheets table
    private Map<String, String> summaryRow() {
        Map<String, String> summaryRow = generalColumnsOfInfoRowSummary(4);
        summaryRow.put("Только Google?", "-");
        return summaryRow;
    }

    @Test
    void headers_order() {
        assertEquals(Arrays.asList(
                "id",
                "Марка",
                "Город",
                "Только Google?",
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
                "Непереключенный звонок"),
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

    @Test
    void correctIndexOfFirstRow() {
        assertTrue(mapper.dependsOnIndexOfFirstNonHeadersRow());
        assertEquals(3, mapper.getIndexOfFirstNonHeadersRow());
    }
}
