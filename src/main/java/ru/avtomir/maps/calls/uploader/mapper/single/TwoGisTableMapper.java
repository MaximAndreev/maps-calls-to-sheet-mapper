package ru.avtomir.maps.calls.uploader.mapper.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;
import ru.avtomir.maps.calls.uploader.mapper.Tag;

import java.util.*;

public class TwoGisTableMapper implements TableMapper<CallStat> {
    private static final Logger log = LoggerFactory.getLogger(TwoGisTableMapper.class);
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
            map.put("Всего звонков", String.format("" +
                    "=(%2$s%1$s + %3$s%1$s + " +
                    "%4$s%1$s + %5$s%1$s + %6$s%1$s)", i, "E", "F", "G", "H", "J"));
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
            map.put("Затраты", stat.getCostAsString());
            map.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", i, "Q", "D"));
            map.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", i, "Q", "E", "F"));
            map.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", i, "Q", "G", "H", "J"));
            asTable.add(map);
        }
        asTable.add(0, summary(startIdx, i));
    }

    private Map<String, String> summary(int startIdx, int i) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "-");
        map.put("Марка", "Все");
        map.put("Город", "Все");
        int columnIdx = 3;
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
        map.put("Затраты", sumColumnFormula(ALPHABET[++columnIdx], startIdx + 1, i));
        map.put("CPA общий", String.format("=%2$s%1$s/%3$s%1$s", startIdx, "Q", "D"));
        map.put("CPA ОП", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s)", startIdx, "Q", "E", "F"));
        map.put("CPA сервис", String.format("=%2$s%1$s/(%3$s%1$s + %4$s%1$s + %5$s%1$s)", startIdx, "Q", "G", "H", "J"));
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
        return Arrays.asList(
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
                "CPA сервис");
    }
}
