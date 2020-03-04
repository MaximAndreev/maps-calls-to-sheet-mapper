package ru.avtomir.maps.calls.uploader.mapper.single;

import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleTableMapper extends AbstractSingleTableMapper implements TableMapper<CallStat> {

    public GoogleTableMapper(int absoluteRowIndexOfFirstRowWithData) {
        super(absoluteRowIndexOfFirstRowWithData);
    }

    protected Map<String, String> summaryRow(Integer countOfStatRows) {
        int firstStatRow = firstNonHeadersRow + 1;
        int lastStatRow = firstNonHeadersRow + countOfStatRows;
        Map<String, String> map = new HashMap<>();
        map.put("id", "-");
        map.put("Марка", "Все");
        map.put("Город", "Все");
        map.put("Только Google?", "-");
        int columnIdx = 4;
        map.put("Всего звонков", sumColumnFormula(ALPHABET[columnIdx], firstStatRow, lastStatRow));
        map.put("Продажа", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Продажа Юр. Лицо", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Сервис", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Доп. оборудование", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("БУ", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Запчасти", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Страхование", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Прочее", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Нецелевой звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Упущенный звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Не отвеченный 50+сек", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        map.put("Непереключенный звонок", sumColumnFormula(ALPHABET[++columnIdx], firstStatRow, lastStatRow));
        return map;
    }

    protected Map<String, String> statsRow(int currentRow, CallStat stat) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.join(";", stat.getProfileIds()));
        map.put("Марка", stat.getBrand());
        map.put("Город", stat.getRegion());
        map.put("Только Google?", stat.isCostSourceOnly() ? "Да" : "Нет");
        map.put("Всего звонков", String.format("=СУММ(%s%s:%s%s)", "F", currentRow, "M", currentRow));
        map.put("Продажа", stat.getCountAsString(Tag.SALE));
        map.put("Продажа Юр. Лицо", stat.getCountAsString(Tag.SALE_FLEET));
        map.put("Сервис", stat.getCountAsString(Tag.SERVICE));
        map.put("Доп. оборудование", stat.getCountAsString(Tag.EQUIPMENT));
        map.put("БУ", stat.getCountAsString(Tag.USED));
        map.put("Запчасти", stat.getCountAsString(Tag.PARTS));
        map.put("Страхование", stat.getCountAsString(Tag.INSURANCE));
        map.put("Прочее", stat.getCountAsString(Tag.OTHERS));
        map.put("Нецелевой звонок", stat.getCountAsString(Tag.NON_TARGET));
        map.put("Упущенный звонок", stat.getCountAsString(Tag.MISSED_CALLS));
        map.put("Не отвеченный 50+сек", stat.getCountAsString(Tag.NOT_ANSWERED50));
        map.put("Непереключенный звонок", stat.getCountAsString(Tag.NOT_SWITCHED_CALLS));
        return map;
    }

    @Override
    public List<String> getTableHeaders() {
        return Arrays.asList(
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
                "Непереключенный звонок");
    }
}
