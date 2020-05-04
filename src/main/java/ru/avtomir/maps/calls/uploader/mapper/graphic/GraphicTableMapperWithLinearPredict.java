package ru.avtomir.maps.calls.uploader.mapper.graphic;

import ru.avtomir.maps.calls.uploader.mapper.MonthSummary;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class GraphicTableMapperWithLinearPredict implements TableMapper<MonthSummary> {
    private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<Map<String, String>> asTable = new ArrayList<>();

    @Override
    public void setSource(List<MonthSummary> stats) {
        stats.stream()
                .map(this::toRow)
                .map(this::predict)
                .forEach(this::add);
    }

    private Row toRow(MonthSummary monthSummary) {
        Row result = new Row();
        result.month = monthSummary.getMonth();
        result.date = monthSummary.getDate();
        result.brand = monthSummary.getBrand();
        result.city = monthSummary.getCity();
        result.leadsSale = monthSummary.getLeadsSale();
        result.leadsService = monthSummary.getLeadsService();
        result.cost = monthSummary.getCost();
        return result;
    }

    private Row predict(Row source) {
        if (!source.predictable()) {
            return source;
        }
        return getPredicted(source);
    }

    private Row getPredicted(Row source) {
        double linearCoefficient = (double) source.daysInMonth() / source.currentDay();
        Row target = new Row();
        target.month = source.month;
        target.date = source.date;
        target.brand = source.brand;
        target.city = source.city;
        target.leadsSale = Double.valueOf(source.leadsSale * linearCoefficient).intValue();
        target.leadsService = Double.valueOf(source.leadsService * linearCoefficient).intValue();
        target.cost = source.cost;
        return target;
    }

    private void add(Row rowDto) {
        Map<String, String> row = new HashMap<>();
        row.put("Месяц", rowDto.month.format(YYYY_MM_DD));
        row.put("Марка", rowDto.brand);
        row.put("Город", rowDto.city);
        row.put("Продажи и Сервис Все", valueOf(rowDto.leadsServiceAndSale()));
        row.put("Продажи Все", valueOf(rowDto.leadsSale));
        row.put("Сервис Все", valueOf(rowDto.leadsService));
        row.put("CPA", format(LOCALE, "%.2f", rowDto.cpa()));
        row.put("Затраты", format("%.0f", rowDto.cost));
        asTable.add(row);
    }

    @Override
    public List<Map<String, String>> getTableBody() {
        return Collections.unmodifiableList(asTable);
    }

    @Override
    public List<String> getTableHeaders() {
        return Arrays.asList(
                "Месяц", "Марка", "Город",
                "Продажи и Сервис Все", "Продажи Все", "Сервис Все", "CPA", "Затраты");
    }

    @Override
    public boolean dependsOnIndexOfFirstNonHeadersRow() {
        return false;
    }

    @Override
    public int getIndexOfFirstNonHeadersRow() {
        return -1;
    }

    private static class Row {
        public LocalDate month;
        public LocalDate date;
        public String brand;
        public String city;
        public Integer leadsSale;
        public Integer leadsService;
        public Double cost;

        public Double cpa() {
            return leadsServiceAndSale() != 0 ? cost / leadsServiceAndSale() : 0;
        }

        public Integer leadsServiceAndSale() {
            return leadsSale + leadsService;
        }

        public boolean predictable() {
            return month.lengthOfMonth() != date.getDayOfMonth();
        }

        public Integer daysInMonth() {
            return month.getMonth().length(month.isLeapYear());
        }

        public Integer currentDay() {
            return date.getDayOfMonth();
        }
    }
}
