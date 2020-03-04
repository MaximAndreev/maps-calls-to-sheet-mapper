package ru.avtomir.maps.calls.uploader.mapper.single;

import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexTableMapper extends AbstractSingleTableMapper implements TableMapper<CallStat> {

    public YandexTableMapper(int absoluteRowIndexOfFirstRowWithData) {
        super(absoluteRowIndexOfFirstRowWithData);
    }

    @Override
    protected Map<String, String> summaryRow(Integer countOfStatRows) {
        int firstStatRow = firstNonHeadersRow + 1;
        int lastStatRow = firstNonHeadersRow + countOfStatRows;
        Map<String, String> summaryRow = new HashMap<>();
        summaryRow.put("id", "-");
        summaryRow.put("Марка", "Все");
        summaryRow.put("Город", "Все");
        summaryRow.put("Только Яндекс?", "-");
        int columnIdx = 4;
        summaryRow.put("Всего звонков", sumColumnFormula(ALPHABET[columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Продажа", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Продажа Юр. Лицо", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Сервис", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Доп. оборудование", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("БУ", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Запчасти", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Страхование", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Прочее", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Нецелевой звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Упущенный звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Не отвеченный 50+сек", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Непереключенный звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("Затраты", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        summaryRow.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", firstNonHeadersRow, "R", "E"));
        summaryRow.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", firstNonHeadersRow, "R", "F", "G"));
        summaryRow.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", firstNonHeadersRow, "R", "H", "I", "K"));
        return summaryRow;
    }

    @Override
    protected Map<String, String> statsRow(int currentRow, CallStat stat) {
        Map<String, String> row = new HashMap<>();
        row.put("id", String.join(";", stat.getProfileIds()));
        row.put("Марка", stat.getBrand());
        row.put("Город", stat.getRegion());
        row.put("Только Яндекс?", stat.isCostSourceOnly() ? "Да" : "Нет");
        row.put("Всего звонков", String.format("" +
                "=(%2$s%1$s + %3$s%1$s " +
                "+ %4$s%1$s + %5$s%1$s +%6$s%1$s)", currentRow, "F", "G", "H", "I", "K"));
        row.put("Продажа", stat.getCountAsString(Tag.SALE));
        row.put("Продажа Юр. Лицо", stat.getCountAsString(Tag.SALE_FLEET));
        row.put("Сервис", stat.getCountAsString(Tag.SERVICE));
        row.put("Доп. оборудование", stat.getCountAsString(Tag.EQUIPMENT));
        row.put("БУ", stat.getCountAsString(Tag.USED));
        row.put("Запчасти", stat.getCountAsString(Tag.PARTS));
        row.put("Страхование", stat.getCountAsString(Tag.INSURANCE));
        row.put("Прочее", stat.getCountAsString(Tag.OTHERS));
        row.put("Нецелевой звонок", stat.getCountAsString(Tag.NON_TARGET));
        row.put("Упущенный звонок", stat.getCountAsString(Tag.MISSED_CALLS));
        row.put("Не отвеченный 50+сек", stat.getCountAsString(Tag.NOT_ANSWERED50));
        row.put("Непереключенный звонок", stat.getCountAsString(Tag.NOT_SWITCHED_CALLS));
        row.put("Затраты", stat.getCostAsString());
        row.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", currentRow, "R", "E"));
        row.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", currentRow, "R", "F", "G"));
        row.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", currentRow, "R", "H", "I", "K"));
        return row;
    }

    @Override
    public List<String> getTableHeaders() {
        return Arrays.asList(
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
                "CPA сервис");
    }
}
