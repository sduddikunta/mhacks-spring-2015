package org.mhacks.zss.model;

import java.io.Serializable;
import java.util.List;

public class Portfolio implements Serializable {
    private String customerId;
    private double totalCash;

    private double savings;
    private double invested;
    private List<Security> securities;
    private List<Double> composites;

    public Portfolio() {
    }

    public Portfolio(String customerId, double totalCash, double savings, double invested, List<Security> securities) {
        this.customerId = customerId;
        this.totalCash = totalCash;
        this.savings = savings;
        this.invested = invested;
        this.securities = securities;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public List<Security> getSecurities() {
        return securities;
    }

    public double getSavings() {
        return savings;
    }

    public double getInvested() {
        return invested;
    }

    public List<Double> getComposites() {
        return composites;
    }
}
