package org.mhacks.zss.portfolio;

import java.util.List;

public class Portfolio {
    private final String customerId;
    private final double totalCash;

    private final double savings;
    private final double invested;
    private final List<Security> securities;
    private final List<Double> composites;

    public Portfolio(String customerId, double totalCash, double savings, double invested,
                     List<Security> securities, List<Double> composites) {
        this.customerId = customerId;
        this.totalCash = totalCash;
        this.savings = savings;
        this.invested = invested;
        this.securities = securities;
        this.composites = composites;
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
