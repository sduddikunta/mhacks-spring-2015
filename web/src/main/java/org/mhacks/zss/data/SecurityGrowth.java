package org.mhacks.zss.data;

public class SecurityGrowth implements Comparable<SecurityGrowth> {
    private String security;
    private Double growth;

    public SecurityGrowth() {
    }

    public SecurityGrowth(String security, double growth) {
        this.security = security;
        this.growth = growth;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public Double getGrowth() {
        return growth;
    }

    public void setGrowth(Double growth) {
        this.growth = growth;
    }

    @Override
    public int compareTo(SecurityGrowth o) {
        return Double.compare(o.growth, growth);
    }
}