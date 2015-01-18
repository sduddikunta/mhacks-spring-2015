package org.mhacks.zss.model;

import java.io.Serializable;

public class Security implements Serializable {
    private String name;
    private String symbol;
    private double value;
    private int shares;

    private boolean isFund;
    private double yearlyGrowth;
    private double yearlyVolatility;
    private double volume;

    private String industry;

    public Security(String name) {
        this.name = name;
    }

    public Security(String name, String symbol, double value, int shares, boolean isFund,
                    double yearlyGrowth, double yearlyVolatility, double volume, String industry) {
        this.name = name;
        this.symbol = symbol;
        this.value = value;
        this.shares = shares;
        this.isFund = isFund;
        this.yearlyGrowth = yearlyGrowth;
        this.yearlyVolatility = yearlyVolatility;
        this.volume = volume;
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getValue() {
        return value;
    }

    public int getShares() {
        return shares;
    }

    public boolean isFund() {
        return isFund;
    }

    public double getYearlyGrowth() {
        return yearlyGrowth;
    }

    public double getYearlyVolatility() {
        return yearlyVolatility;
    }

    public double getVolume() {
        return volume;
    }

    public String getIndustry() {
        return industry;
    }
}
