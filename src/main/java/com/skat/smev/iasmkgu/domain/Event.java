package com.skat.smev.iasmkgu.domain;


import java.util.Date;

public class Event {
    private String foreignId;
    private String mobile;
    private String email;
    private String authorityId;
    private String serviceId;
    private String serviceName;
    private String procedureId;
    private String procedureName;
    private String okato;
    private Date receivedDate;

    public String getForeignId() {
        return foreignId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public String getOkato() {
        return okato;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }
}


