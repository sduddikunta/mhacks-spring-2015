package org.mhacks.zss;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mhacks.zss.model.Portfolio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Network {

    public static final String API_ENDPOINT = "http://zss.cloudapp.net/portfolio/from/capitalone/";
    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public static StringRequest getPortfolio(String customerId, final Response.Listener<Portfolio> listener) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, API_ENDPOINT + customerId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                listener.onResponse(gson.fromJson(response, Portfolio.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(240000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return stringRequest;
    }

    public static StringRequest getPortfolio(String customerId, int risk, int value,
                                             final Response.Listener<Portfolio> listener) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, API_ENDPOINT + customerId + "/" + risk + "/" + value,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                listener.onResponse(gson.fromJson(response, Portfolio.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(240000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return stringRequest;
    }
}