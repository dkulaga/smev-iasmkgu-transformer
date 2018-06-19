package com.skat.smev.iasmkgu.domain.mkgu.views;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "mkgu_from_federal_packets")
@Immutable
public class MkguFromFederalPacketsView implements Serializable{
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

    @Column(name = "date_event")
    private Date date_event;

    @Column(name = "user_id")
    private int user_id;

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
