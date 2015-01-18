package org.mhacks.zss.data;

public class AnalyzedSecurityData {
    private Double volatility;
    private Double growth;

    public AnalyzedSecurityData() {
    }

    public AnalyzedSecurityData(Double volatility, Double growth) {
        this.volatility = volatility;
        this.growth = growth;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getGrowth() {
        return growth;
    }

    public void setGrowth(Double growth) {
        this.growth = growth;
    }
}
