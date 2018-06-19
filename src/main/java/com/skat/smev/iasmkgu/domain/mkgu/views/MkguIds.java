package com.skat.smev.iasmkgu.domain.mkgu.views;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

public class MkguIds {
    @Column(name = "id_key")
    private int id_key;

    @Column(name = "id_otdel")
    private int id_otdel;

    @Column(name = "counts")
    private long counts;

    @Column(name = "id_mkgu")
    private String id_mkgu;

    public MkguIds(int id_key, int id_otdel, long counts, String id_mkgu) {
        this.id_key = id_key;
        this.id_otdel = id_otdel;
        this.counts = counts;
        this.id_mkgu = id_mkgu;
    }

    public MkguIds(int id_key, long counts, String id_mkgu) {
        this.id_key = id_key;
        this.counts = counts;
        this.id_mkgu = id_mkgu;
    }

    public int getId_key() {
        return id_key;
    }

    public int getId_otdel() {
        return id_otdel;
    }

    public long getCounts() {
        return counts;
    }

    public String getId_mkgu() {
        return id_mkgu;
    }
}
