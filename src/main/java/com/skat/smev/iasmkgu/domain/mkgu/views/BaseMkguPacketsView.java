package com.skat.smev.iasmkgu.domain.mkgu.views;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


public class BaseMkguPacketsView implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "id_key")
    private int id_key;

    @Column(name = "id_otdel")
    private int id_otdel;

    @Column(name = "id_mkgu")
    private String id_mkgu;

    @Column(name = "okato")
    private String okato;

    @Column(name = "indicator_id")
    private int indicator_id;

    @Column(name = "id_service")
    private String id_service;

    @Column(name = "name_service")
    private String name_service;

    @Column(name = "id_authority")
    private String id_authority;

    @Column(name = "name_authority")
    private String name_authority;

    @Column(name = "value_id")
    private int value_id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_event", columnDefinition="DATETIME")
    private Date date_event;

    @Column(name = "user_id")
    private int user_id;

    public BaseMkguPacketsView(int id_key, int id_otdel, String id_mkgu, String okato, int indicator_id, String id_service, String name_service, String id_authority, String name_authority, int value_id, Date date_event, int user_id) {
        this.id_key = id_key;
        this.id_otdel = id_otdel;
        this.id_mkgu = id_mkgu;
        this.okato = okato;
        this.indicator_id = indicator_id;
        this.id_service = id_service;
        this.name_service = name_service;
        this.id_authority = id_authority;
        this.name_authority = name_authority;
        this.value_id = value_id;
        this.date_event = date_event;
        this.user_id = user_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public int getId_key() {
        return id_key;
    }

    public int getId_otdel() {
        return id_otdel;
    }

    public String getId_mkgu() {
        return id_mkgu;
    }

    public String getOkato() {
        return okato;
    }

    public int getIndicator_id() {
        return indicator_id;
    }

    public String getId_service() {
        return id_service;
    }

    public String getName_service() {
        return name_service;
    }

    public String getId_authority() {
        return id_authority;
    }

    public String getName_authority() {
        return name_authority;
    }

    public int getValue_id() {
        return value_id;
    }

    public Date getDate_event() {
        return date_event;
    }

    public int getUser_id() {
        return user_id;
    }
}
