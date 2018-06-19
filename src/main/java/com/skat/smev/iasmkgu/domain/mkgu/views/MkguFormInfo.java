package com.skat.smev.iasmkgu.domain.mkgu.views;


import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class MkguFormInfo implements Serializable {
    @Column(name = "id_key")
    private int id_key;

    @Column(name = "okato")
    private String okato;

    @Column(name = "id_service")
    private String id_service;

    @Column(name = "name_service")
    private String name_service;

    @Column(name = "id_authority")
    private String id_authority;

    @Column(name = "name_authority")
    private String name_authority;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_event", columnDefinition="DATETIME")
    private Date date_event;

    @Column(name = "user_id")
    private int user_id;

    public MkguFormInfo() {
    }

    public MkguFormInfo(Date date_event) {
        this.date_event = date_event;
    }

    public MkguFormInfo(int id_key, String okato, String id_service, String name_service, String id_authority, String name_authority, Date date_event, int user_id) {
        this.id_key = id_key;
        this.okato = okato;
        this.id_service = id_service;
        this.name_service = name_service;
        this.id_authority = id_authority;
        this.name_authority = name_authority;
        this.date_event = date_event;
        this.user_id = user_id;
    }

    public int getId_key() {
        return id_key;
    }

    public String getOkato() {
        return okato;
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

    public Date getDate_event() {
        return date_event;
    }

    public int getUser_id() {
        return user_id;
    }
}
