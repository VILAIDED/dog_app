package com.example.dogapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weight implements Serializable{
    @SerializedName("imperial")
    private String imperial;
    @SerializedName("metric")
    private String metric;

    public String getImperial() {
        return imperial;
    }

    public void setImperial(String imperial) {
        this.imperial = imperial;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Weight(String imperial, String metric) {
        this.imperial = imperial;
        this.metric = metric;
    }
}
