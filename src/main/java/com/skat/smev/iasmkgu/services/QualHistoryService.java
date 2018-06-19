package com.skat.smev.iasmkgu.services;

import com.skat.smev.iasmkgu.domain.mkgu.views.*;
import com.skat.smev.iasmkgu.model.*;
import com.skat.smev.iasmkgu.model.rates.RateType;
import com.skat.smev.iasmkgu.model.rates.RatesRequest;
import com.skat.smev.iasmkgu.repository.MkguFromFederalPacketsRepository;
import com.skat.smev.iasmkgu.repository.MkguFromHistoryPacketsRepository;
import com.skat.smev.iasmkgu.repository.MkguFromPacketsRepository;
import com.skat.smev.iasmkgu.repository.QualHistoryRepository;
import com.skat.smev.iasmkgu.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QualHistoryService {

    private final QualHistoryRepository qualHistoryRepository;

    public QualHistoryService(QualHistoryRepository qualHistoryRepository) {
        this.qualHistoryRepository = qualHistoryRepository;
    }

    @Transactional
    public void updateHistoryByIdKeyAndIdOtdel(int id_key, int id_otdel){
        qualHistoryRepository.updateHistoryByIdKeyAndIdOtdel(id_key, id_otdel);
    }
}
