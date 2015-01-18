package org.mhacks.zss;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.mhacks.zss.model.BloombergResponse;
import org.mhacks.zss.model.HistoricalDataRequest;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class BloombergApi {
    private static final Gson gson = new Gson();

    public static BloombergResponse getHistoricalData(HistoricalDataRequest dataRequest) throws IOException {
        KeyStore trustStore;
        try (InputStream instream = BloombergApi.class.getResourceAsStream("/keystore.jks")) {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(instream, "lebron".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new IOException(e);
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(trustStore, "lebron".toCharArray())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IOException(e);
        }
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                null,
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        BloombergResponse parsedResponse;
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build()) {

            HttpPost httpPost = new HttpPost("https://54.174.49.59/request/blp/refdata/HistoricalData");
            httpPost.setEntity(new StringEntity(gson.toJson(dataRequest)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                parsedResponse = gson.fromJson(convertStreamToString(entity.getContent()), BloombergResponse.class);
                EntityUtils.consume(entity);
            }
        }

        return parsedResponse;
    }

    public static JSONObject getSymbolDetail(String symbol) throws IOException {
        KeyStore trustStore;
        try (InputStream instream = BloombergApi.class.getResourceAsStream("/keystore.jks")) {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(instream, "lebron".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new IOException(e);
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(trustStore, "lebron".toCharArray())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IOException(e);
        }
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                null,
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        JSONObject object;
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build()) {

            HttpPost httpPost = new HttpPost("https://54.174.49.59/request/blp/refdata/ReferenceData");
            httpPost.setEntity(new StringEntity("{ \"securities\": [\"" + symbol + " Equity\"]," +
                    " \"fields\": [\"LONG_COMP_NAME\", \"LAST_PRICE\", \"FUND_TYP\", \"VOLUME\", \"INDUSTRY_GROUP\"," +
                    "\"INDUSTRY_SECTOR\"] }"));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                object = (JSONObject) JSONValue.parse(convertStreamToString(entity.getContent()));
                EntityUtils.consume(entity);
            }
        }
        return ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) object.get("data")).get(0))
                .get("securityData")).get(0)).get("fieldData"));
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}