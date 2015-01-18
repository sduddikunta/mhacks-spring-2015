package org.mhacks.zss.data;

public class SymbolName {
    private String name;
    private String symbol;

    public SymbolName() {
    }

    public SymbolName(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
