package com.skat.smev.iasmkgu.domain;

import java.util.List;

public class Form {
    private String mkguId;
    private String foreignId;
    private FormData formData;
    private List<Rate> rates;

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public String getMkguId() {
        return mkguId;
    }

    public void setMkguId(String mkguId) {
        this.mkguId = mkguId;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
