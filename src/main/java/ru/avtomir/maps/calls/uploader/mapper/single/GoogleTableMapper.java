package ru.avtomir.maps.calls.uploader.mapper.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.Tag;

import java.util.*;

public class GoogleTableMapper implements TableMapper<CallStat> {
    private static final Logger log = LoggerFactory.getLogger(YandexTableMapper.class);
    private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private List<Map<String, String>> asTable = new ArrayList<>();

    @Override
    public void setSource(List<CallStat> stats) {
        log.trace("convert to Google Sheets table");
        int startIdx = 3;
        int i = startIdx;
        for (CallStat stat : stats) {
            i++;
            Map<String, String> map = new HashMap<>();
            map.put("id", String.join(";", stat.getProfileIds()));
            map.put("Марка", stat.getBrand());
            map.put("Город", stat.getRegion());
            map.put("Только Google?", stat.isCostSourceOnly() ? "Да" : "Нет");
            map.put("Всего звонков", String.format("=СУММ(%s%s:%s%s)", "F", i, "M", i));
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
            asTable.add(map);
        }
        asTable.add(0, summary(startIdx, i));
    }

    @Override
    public List<Map<String, String>> getTableBody() {
        return Collections.unmodifiableList(asTable);
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

    private Map<String, String> summary(Integer startIdx, Integer i) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "-");
        map.put("Марка", "Все");
        map.put("Город", "Все");
        map.put("Только Google?", "-");
        int columnIdx = 4;
        map.put("Всего звонков", sumColumnFormula(ALPHABET[columnIdx], startIdx + 1, i));
        map.put("Продажа", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Продажа Юр. Лицо", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Сервис", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Доп. оборудование", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("БУ", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Запчасти", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Страхование", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Прочее", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Нецелевой звонок", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Упущенный звонок", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Не отвеченный 50+сек", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("Непереключенный звонок", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        return map;
    }

    private String sumColumnFormula(char charLetter, Integer firstRow, Integer lastRow) {
        String columnLetter = String.valueOf(charLetter);
        return String.format("=СУММ(%s%s:%s%s)", columnLetter, firstRow, columnLetter, lastRow);
    }
}
