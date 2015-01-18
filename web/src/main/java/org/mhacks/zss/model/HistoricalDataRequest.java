package org.mhacks.zss.model;

import java.util.Arrays;
import java.util.List;

public class HistoricalDataRequest {
    private List<String> securities;
    private List<String> fields;
    private String startDate;
    private String periodicitySelection;

    public HistoricalDataRequest() {
        // default constructor required for model classes
    }

    public HistoricalDataRequest(String security, String startDate) {
        this(Arrays.asList(security), Arrays.asList("LAST_PRICE", "OPEN"), startDate, "DAILY");
    }

    public HistoricalDataRequest(List<String> securities, List<String> fields, String startDate, String periodicitySelection) {
        this.securities = securities;
        this.fields = fields;
        this.startDate = startDate;
        this.periodicitySelection = periodicitySelection;
    }

    public List<String> getSecurities() {
        return securities;
    }

    public void setSecurities(List<String> securities) {
        this.securities = securities;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPeriodicitySelection() {
        return periodicitySelection;
    }

    public void setPeriodicitySelection(String periodicitySelection) {
        this.periodicitySelection = periodicitySelection;
    }
}
