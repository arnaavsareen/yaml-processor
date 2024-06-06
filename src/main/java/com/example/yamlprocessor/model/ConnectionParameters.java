package com.example.yamlprocessor.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionParameters {
    private String vsphere_host;
    private String vsphere_user;
    private String vsphere_password;
    private Boolean ignore_ssl;
    private Integer specs_size;
    private Boolean fetch_custom_attributes;
    private Boolean fetch_tags;
    private Boolean fetch_alarms;
    private Map<String, Boolean> collect_only = new LinkedHashMap<>();

    // Getters and Setters
    public String getVsphere_host() {
        return vsphere_host;
    }

    public void setVsphere_host(String vsphere_host) {
        this.vsphere_host = vsphere_host;
    }

    public String getVsphere_user() {
        return vsphere_user;
    }

    public void setVsphere_user(String vsphere_user) {
        this.vsphere_user = vsphere_user;
    }

    public String getVsphere_password() {
        return vsphere_password;
    }

    public void setVsphere_password(String vsphere_password) {
        this.vsphere_password = vsphere_password;
    }

    public Boolean getIgnore_ssl() {
        return ignore_ssl;
    }

    public void setIgnore_ssl(Boolean ignore_ssl) {
        this.ignore_ssl = ignore_ssl;
    }

    public Integer getSpecs_size() {
        return specs_size;
    }

    public void setSpecs_size(Integer specs_size) {
        this.specs_size = specs_size;
    }

    public Boolean getFetch_custom_attributes() {
        return fetch_custom_attributes;
    }

    public void setFetch_custom_attributes(Boolean fetch_custom_attributes) {
        this.fetch_custom_attributes = fetch_custom_attributes;
    }

    public Boolean getFetch_tags() {
        return fetch_tags;
    }

    public void setFetch_tags(Boolean fetch_tags) {
        this.fetch_tags = fetch_tags;
    }

    public Boolean getFetch_alarms() {
        return fetch_alarms;
    }

    public void setFetch_alarms(Boolean fetch_alarms) {
        this.fetch_alarms = fetch_alarms;
    }

    public Map<String, Boolean> getCollect_only() {
        return collect_only;
    }

    public void setCollect_only(Map<String, Boolean> collect_only) {
        this.collect_only = collect_only;
    }
}
