package com.skat.smev.iasmkgu.domain.mkgu.views;



public class SendedPackageInfo {
    private int id_key;
    private int id_otdel;

    public SendedPackageInfo(int id_key, int id_otdel) {
        this.id_key = id_key;
        this.id_otdel = id_otdel;
    }

    public int getId_key() {
        return id_key;
    }

    public void setId_key(int id_key) {
        this.id_key = id_key;
    }

    public int getId_otdel() {
        return id_otdel;
    }

    public void setId_otdel(int id_otdel) {
        this.id_otdel = id_otdel;
    }
}
