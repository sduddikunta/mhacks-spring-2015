package org.mhacks.zss.data;

public class SecurityVolatility implements Comparable<SecurityVolatility> {
    private String security;
    private Double volatility;

    public SecurityVolatility() {
    }

    public SecurityVolatility(String security, double volatility) {
        this.security = security;
        this.volatility = volatility;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    @Override
    public int compareTo(SecurityVolatility o) {
        return Double.compare(volatility, o.volatility);
    }
}
