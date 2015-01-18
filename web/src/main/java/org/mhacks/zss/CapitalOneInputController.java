package org.mhacks.zss;

import org.mhacks.zss.capitalone.CapitalOneDataAnalytics;
import org.mhacks.zss.data.SecurityAnalytics;
import org.mhacks.zss.model.BloombergResponse;
import org.mhacks.zss.model.HistoricalDataRequest;
import org.mhacks.zss.portfolio.NoSuchCustomerException;
import org.mhacks.zss.portfolio.Portfolio;
import org.mhacks.zss.portfolio.Security;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class CapitalOneInputController {

    @RequestMapping(value = "/portfolio/from/capitalone/{customerId}", produces = "application/json")
    public Portfolio getPortfolioForCustomer(@PathVariable String customerId) {
        try {
            double amountToInvest = CapitalOneDataAnalytics.calculateCanMoveFromChecking(customerId);

            List<Security> securities = SecurityAnalytics.generatePortfolio(amountToInvest / 5, 20, 1.1);

            List<Double> prices = new LinkedList<>();

            for (Security security : securities) {
                BloombergResponse response = BloombergApi
                        .getHistoricalData(new HistoricalDataRequest(security.getSymbol() + " Equity", "20140118"));
                int size = response.getData().get(0).getSecurityData().getFieldData().size();
                for (int i = 0; i < size; i++) {
                    double price = response.getData().get(0).getSecurityData().getFieldData().get(i).getLAST_PRICE();
                    if (prices.size() <= i) {
                        prices.add(price * security.getShares());
                    } else {
                        prices.set(i, prices.get(i) + (price * security.getShares()));
                    }
                }
            }

            return new Portfolio(customerId, amountToInvest, amountToInvest * 4 / 5, amountToInvest / 5, securities,
                    prices);
        } catch (Exception ex) {
            throw new NoSuchCustomerException();
        }
    }

    @RequestMapping(value = "/portfolio/from/capitalone/{customerId}/{risk}/{value}", produces = "application/json")
    public Portfolio getPortfolioForCustomer(@PathVariable String customerId, @PathVariable int risk,
                                             @PathVariable int value) {
        try {
            value += 20;
            value = Math.min(value, 100);

            double amountToInvest = CapitalOneDataAnalytics.calculateCanMoveFromChecking(customerId);

            List<Security> securities = SecurityAnalytics.generatePortfolio(amountToInvest * value / 100, 20, risk / 100);

            List<Double> prices = new LinkedList<>();

            for (Security security : securities) {
                BloombergResponse response = BloombergApi
                        .getHistoricalData(new HistoricalDataRequest(security.getSymbol() + " Equity", "20140118"));
                int size = response.getData().get(0).getSecurityData().getFieldData().size();
                for (int i = 0; i < size; i++) {
                    double price = response.getData().get(0).getSecurityData().getFieldData().get(i).getLAST_PRICE();
                    if (prices.size() <= i) {
                        prices.add(price * security.getShares());
                    } else {
                        prices.set(i, prices.get(i) + (price * security.getShares()));
                    }
                }
            }

            return new Portfolio(customerId, amountToInvest, amountToInvest * ((100 - value) / 100), amountToInvest *
                    value / 100, securities, prices);
        } catch (Exception ex) {
            throw new NoSuchCustomerException();
        }
    }
}
