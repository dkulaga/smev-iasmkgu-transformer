package com.skat.smev.iasmkgu.domain.mkgu.views;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "qual_history")
public class QualHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_key")
    private int id_key;

    @Column(name = "id_quest")
    private int id_quest;

    @Column(name = "valuation")
    private int valuation;

    @Column(name = "date_event")
    private Date date_event;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "id_otdel")
    private int id_otdel;
//    private int mkgu_id
}
