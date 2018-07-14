package com.skat.smev.iasmkgu.domain.forms;


import com.skat.smev.iasmkgu.domain.BaseMessageModel;

import java.util.List;

public class FormsResponseModel extends BaseMessageModel {
    private String serviceType;
    private String version;
    private String updatedAt;
    private List<Block> blocks;
    private List<FormIndicator> formIndicators;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<FormIndicator> getFormIndicators() {
        return formIndicators;
    }

    public void setFormIndicators(List<FormIndicator> formIndicators) {
        this.formIndicators = formIndicators;
    }
}
