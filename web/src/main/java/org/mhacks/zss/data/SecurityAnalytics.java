package org.mhacks.zss.data;

import org.json.simple.JSONObject;
import org.mhacks.zss.BloombergApi;
import org.mhacks.zss.model.BloombergResponse;
import org.mhacks.zss.model.FieldData;
import org.mhacks.zss.model.HistoricalDataRequest;
import org.mhacks.zss.model.SecurityData;
import org.mhacks.zss.portfolio.Security;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class SecurityAnalytics {

    //@Scheduled(fixedDelay = 86400000L)
    public void doBloombergRefresh() throws IOException {
        Jedis jedis = new Jedis("localhost");

        BloombergResponse base = BloombergApi.getHistoricalData(new HistoricalDataRequest("SPX Index", "20140118"));
        double baseVolatility = volatilityScore(base.getData().get(0).getSecurityData(), 1);

        for (SymbolName security : SecuritiesUtil.getAllSecurities()) {
            BloombergResponse response = BloombergApi
                    .getHistoricalData(new HistoricalDataRequest(security.getSymbol() + " Equity", "20140117"));
            double volatility = volatilityScore(response.getData().get(0).getSecurityData(), baseVolatility);
            double growth = growthScore(response.getData().get(0).getSecurityData());
            if (!Double.isNaN(volatility)) {
                jedis.hset(security.getSymbol(), "volatility", Double.toString(volatility));
                jedis.hset(security.getSymbol(), "growth", Double.toString(growth));
                jedis.zadd("volatilities", volatility, security.getSymbol());
                jedis.zadd("growths", -growth, security.getSymbol());
                System.out.printf("%s: %.6f %.6f%n", security.getSymbol(), volatility, growth);
            }
        }
        jedis.close();
    }

    public static List<Security> generatePortfolio(double totalCash, int n, double risk) throws IOException {
        Jedis jedis = new Jedis("localhost");
        List<String> securities = new LinkedList<>();
        doGeneratePortfolio(securities, n, totalCash / n, risk);

        List<Security> outputSecurities = new LinkedList<>();
        for (String security : securities) {
            JSONObject symbolDetail = BloombergApi.getSymbolDetail(security);
            String name = (String) symbolDetail.get("LONG_COMP_NAME");
            double value = Double.POSITIVE_INFINITY;
            if (symbolDetail.get("LAST_PRICE") != null) {
                value = Double.valueOf(symbolDetail.get("LAST_PRICE").toString());
            }
            int shares = (int) Math.round((totalCash / securities.size()) / value);
            boolean fund = symbolDetail.containsKey("FUND_TYP");
            double growth = Double.parseDouble(jedis.hget(security, "growth"));
            double volatility = Double.parseDouble(jedis.hget(security, "volatility"));
            long volume = (Long) symbolDetail.get("VOLUME");
            String industry = (String) symbolDetail.get("INDUSTRY_GROUP");
            outputSecurities.add(new Security(name, security, value, shares, fund, growth, volatility, volume, industry));
        }
        return outputSecurities;
    }

    private static void doGeneratePortfolio(List<String> securities, int n, double pricePer, double maxVolatility) throws IOException {
        if (n > 0) {
            HashSet<String> groups = new HashSet<>();
            Jedis jedis = new Jedis("localhost");

            SortedSet<SecurityGrowth> sortedSecurities = jedis.zrangeByScore("volatilities", 0, maxVolatility)
                    .stream()
                    .map(security -> new SecurityGrowth(security, Double.parseDouble(jedis.hget(security, "growth"))))
                    .collect(Collectors.toCollection(TreeSet::new));

            for (SecurityGrowth securityGrowth : sortedSecurities) {
                String security = securityGrowth.getSecurity();
                if (securities.size() < n && !securities.contains(security)) {
                    double growth = Double.parseDouble(jedis.hget(security, "growth"));
                    JSONObject symbolDetail = BloombergApi.getSymbolDetail(security);
                    long volume = (Long) symbolDetail.get("VOLUME");
                    boolean fund = symbolDetail.containsKey("FUND_TYP");
                    double lastPrice = Double.POSITIVE_INFINITY;
                    if (symbolDetail.get("LAST_PRICE") != null) {
                        lastPrice = Double.valueOf(symbolDetail.get("LAST_PRICE").toString());
                    }

                    if (growth > 0.04
                            && volume > 150000
                            && !fund
                            && !groups.contains(symbolDetail.get("INDUSTRY_GROUP"))
                            && pricePer > lastPrice) {
                        // looking for positive, reasonable growths and non-fund securities
                        securities.add(security);
                        groups.add((String) symbolDetail.get("INDUSTRY_GROUP"));
                        System.out.printf("%s %.6f %.6f%n", security, Double.parseDouble(jedis.hget(security, "volatility")), growth);
                    }
                }
            }
            jedis.close();
        }
    }

    private double volatilityScore(SecurityData data, double baseVolatility) {
        double mean = 0;
        for (FieldData i : data.getFieldData()) {
            mean += (i.getLAST_PRICE() - i.getOPEN()) / (i.getOPEN());
        }
        mean /= data.getFieldData().size();

        double stDev = 0;
        for (FieldData i : data.getFieldData()) {
            stDev += Math.pow(((i.getLAST_PRICE() - i.getOPEN()) / (i.getOPEN())) - mean, 2);
        }
        stDev /= data.getFieldData().size();
        stDev = Math.sqrt(stDev);

        return (stDev * Math.sqrt(252)) / baseVolatility;
    }

    private double growthScore(SecurityData data) {
        if (data.getFieldData().size() == 0) {
            return 0;
        }
        return (data.getFieldData().get(data.getFieldData().size() - 1).getLAST_PRICE()
                - data.getFieldData().get(0).getOPEN()) / (data.getFieldData().get(0).getOPEN());
    }
}
