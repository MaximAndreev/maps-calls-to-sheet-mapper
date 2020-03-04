package ru.avtomir.maps.calls.uploader.mapper.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.avtomir.maps.calls.uploader.mapper.CallStat;
import ru.avtomir.maps.calls.uploader.mapper.TableMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractSingleTableMapper implements TableMapper<CallStat> {
    private final static Logger log = LoggerFactory.getLogger(AbstractSingleTableMapper.class);
    protected final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    protected List<Map<String, String>> asTable = new ArrayList<>();
    protected int firstNonHeadersRow;

    public AbstractSingleTableMapper(int absoluteRowIndexOfFirstRowWithData) {
        firstNonHeadersRow = absoluteRowIndexOfFirstRowWithData;
    }

    abstract Map<String, String> summaryRow(Integer countOfStatRows);

    abstract Map<String, String> statsRow(int currentRow, CallStat stat);

    @Override
    public void setSource(List<CallStat> stats) {
        log.trace("convert to Google Sheets table");
        int countOfStats = stats.size();
        addSummaryRow(countOfStats);
        addStatRows(stats);
    }

    private void addSummaryRow(int countOfStats) {
        asTable.add(summaryRow(countOfStats));
    }

    private void addStatRows(List<CallStat> stats) {
        for (int i = 0; i < stats.size(); i++) {
            int currentRow = getCurrentRow(i);
            CallStat stat = stats.get(i);
            asTable.add(statsRow(currentRow, stat));
        }
    }

    private int getCurrentRow(int i) {
        int countOfSummaryRows = 1;
        return firstNonHeadersRow + countOfSummaryRows + i;
    }

    public String sumColumnFormula(char charLetter, Integer firstRow, Integer lastRow) {
        String columnLetter = String.valueOf(charLetter);
        return String.format("=СУММ(%s%s:%s%s)", columnLetter, firstRow, columnLetter, lastRow);
    }

    @Override
    public List<Map<String, String>> getTableBody() {
        return Collections.unmodifiableList(asTable);
    }
}
