package com.skat.smev.iasmkgu.domain;

/**
 * @author daria.kulaga
 * @since 27.04.2018.
 */
public class Rate {
    private String indicatorId;
    private String valueId;
    private String value;

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
