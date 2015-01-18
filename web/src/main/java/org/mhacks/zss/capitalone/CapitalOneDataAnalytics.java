package org.mhacks.zss.capitalone;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.InputStreamReader;

public class CapitalOneDataAnalytics {

    public static double calculateSavingsAmount(String customerId) throws IOException {
        double monthlyIncome = getMonthlyIncome(customerId);
        double billTotal = getBillTotal(customerId);

        double savingsAmount = monthlyIncome - billTotal;
        return savingsAmount * 0.9;
    }

    public static double calculateCanMoveFromChecking(String customerId) throws IOException {
        return getCheckingBalance(customerId) - calculateSavingsAmount(customerId);
    }

    public static double getCheckingBalance(String customerId) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet getAccounts = new HttpGet(String.format("http://api.reimaginebanking.com/customers/%s/accounts?key=CUST7c756e0fca811a16865556032213f347", customerId));
            HttpResponse accountsResponse = httpclient.execute(getAccounts);
            JSONArray accounts = (JSONArray) JSONValue.parse(new InputStreamReader(accountsResponse.getEntity().getContent()));
            for (Object account : accounts) {
                JSONObject acc = (JSONObject) account;
                if (acc.get("type").equals("checking")) {
                    return Double.parseDouble(acc.get("balance").toString());
                }
            }

        }
        return -1;
    }

    private static double getMonthlyIncome(String customerId) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet getAccounts = new HttpGet(String.format("http://api.reimaginebanking.com/customers/%s/accounts?key=CUST7c756e0fca811a16865556032213f347", customerId));
            HttpResponse accountsResponse = httpclient.execute(getAccounts);
            JSONArray accounts = (JSONArray) JSONValue.parse(new InputStreamReader(accountsResponse.getEntity().getContent()));
            String accountId = "";
            for (Object account : accounts) {
                JSONObject acc = (JSONObject) account;
                if (acc.get("type").equals("checking")) {
                    accountId = (String) acc.get("_id");
                }
            }
            System.out.printf("Found account id %s%n", accountId);
            HttpGet getBills = new HttpGet(String.format("http://api.reimaginebanking.com/accounts/%s/bills?key=CUST7c756e0fca811a16865556032213f347", accountId));
            HttpResponse billsResponse = httpclient.execute(getBills);
            JSONArray bills = (JSONArray) JSONValue.parse(new InputStreamReader(billsResponse.getEntity().getContent()));
            for (Object bill : bills) {
                JSONObject b = (JSONObject) bill;
                if (Double.parseDouble(b.get("payment amount").toString()) < 0) {
                    System.out.printf("Found monthly income %f%n", Double.parseDouble(b.get("payment amount").toString()));
                    return -Double.parseDouble(b.get("payment amount").toString());
                }
            }
        }
        return -1;
    }

    private static double getBillTotal(String customerId) throws IOException {
        double sum = 0;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet getAccounts = new HttpGet(String.format("http://api.reimaginebanking.com/customers/%s/accounts?key=CUST7c756e0fca811a16865556032213f347", customerId));
            HttpResponse accountsResponse = httpclient.execute(getAccounts);
            JSONArray accounts = (JSONArray) JSONValue.parse(new InputStreamReader(accountsResponse.getEntity().getContent()));
            String accountId = "";
            for (Object account : accounts) {
                JSONObject acc = (JSONObject) account;
                if (acc.get("type").equals("checking")) {
                    accountId = (String) acc.get("_id");
                }
            }
            System.out.printf("Found account id %s%n", accountId);
            HttpGet getBills = new HttpGet(String.format("http://api.reimaginebanking.com/accounts/%s/bills?key=CUST7c756e0fca811a16865556032213f347", accountId));
            HttpResponse billsResponse = httpclient.execute(getBills);
            JSONArray bills = (JSONArray) JSONValue.parse(new InputStreamReader(billsResponse.getEntity().getContent()));
            for (Object bill : bills) {
                JSONObject b = (JSONObject) bill;
                if (Double.parseDouble(b.get("payment amount").toString()) > 0) {
                    System.out.printf("Found bill for %f%n", Double.parseDouble(b.get("payment amount").toString()));
                    sum += Double.parseDouble(b.get("payment amount").toString());
                }
            }
        }
        return sum / 12;
    }
}
