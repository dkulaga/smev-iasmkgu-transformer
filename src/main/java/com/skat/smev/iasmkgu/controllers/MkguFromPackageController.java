package com.skat.smev.iasmkgu.controllers;

import com.skat.smev.iasmkgu.domain.mkgu.views.BaseMkguPacketsView;
import com.skat.smev.iasmkgu.domain.mkgu.views.MkguFormInfo;
import com.skat.smev.iasmkgu.domain.mkgu.views.MkguIds;
import com.skat.smev.iasmkgu.domain.mkgu.views.SendedPackageInfo;
import com.skat.smev.iasmkgu.model.rates.*;
import com.skat.smev.iasmkgu.services.MkguBasePackageService;
import com.skat.smev.iasmkgu.services.QualHistoryService;
import com.skat.smev.iasmkgu.services.Smev3Service;
import com.skat.smev.iasmkgu.services.impl.MkguFromFederalPackageService;
import com.skat.smev.iasmkgu.services.impl.MkguFromHistoryPackageService;
import com.skat.smev.iasmkgu.services.impl.MkguFromPackageService;
import com.skat.smev.iasmkgu.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author daria.kulaga
 * @since 22.05.2018.
 */

@RestController
@RequestMapping("/db")
public class MkguFromPackageController {

    private static final Logger LOGGER = Logger.getLogger(MkguFromPackageController.class.getName());
    private final List<SendedPackageInfo> sendedPackageInfoList = new ArrayList<>();

    @Autowired
    private Smev3Service smev3Service;

    @Autowired
    private QualHistoryService qualHistoryService;

    private MkguFromPackageService mkguFromPackageService;
    private MkguFromFederalPackageService mkguFromFederalPackageService;
    private MkguFromHistoryPackageService mkguFromHistoryPackageService;

    public MkguFromPackageController(MkguFromPackageService mkguFromPackageService,
                                     MkguFromFederalPackageService mkguFromFederalPackageService,
                                     MkguFromHistoryPackageService mkguFromHistoryPackageService) {
        this.mkguFromPackageService = mkguFromPackageService;
        this.mkguFromFederalPackageService = mkguFromFederalPackageService;
        this.mkguFromHistoryPackageService = mkguFromHistoryPackageService;
    }

    /**
     * Метод оформирования и отправки запроса в адаптер с использованием данных mkgu_from_packets
     */
    @PostMapping("/send/packets")
    public void preparePacketsAndSend() {
        LOGGER.info("Try to prepare and send request from mkgu_from_packets view");
        prepareRequestAndSend(mkguFromPackageService);
    }


    /**
     * Метод оформирования и отправки запроса в адаптер с использованием данных mkgu_from_federal_packets
     */
    @PostMapping("/send/fedpackets")
    public void prepareFederalPacketsAndSend(){
        LOGGER.info("Try to prepare and send request from mkgu_from_federal_packets view");
        prepareRequestAndSend(mkguFromFederalPackageService);
    }

    /**
     * Метод оформирования и отправки запроса в адаптер с использованием данных mkgu_from_history
     */
    @PostMapping("/send/history")
    public void prepareHistoryPacketsAndSend(){
        LOGGER.info("Try to prepare and send request from mkgu_from_history view");
        prepareRequestAndSend(mkguFromHistoryPackageService);
    }

    private void prepareRequestAndSend(MkguBasePackageService mkguBasePackageService){
        // находим список всех возможных mkgu_id для создания отдельных xml-запросов
        final List<String> allMkguIds = mkguBasePackageService.findAllMkguIdsFromPackets();
        if(allMkguIds.isEmpty()){
            LOGGER.info("Could not find not sended packages!");
            return;
        }
        for (String mkguId : allMkguIds) {
            RatesRequest ratesRequest = null;
            try {
                ratesRequest = prepareRatesRequest(mkguId, mkguBasePackageService);
            } catch (DatatypeConfigurationException e) {
                LOGGER.error("Error while preparing request for mkgu_id = " + mkguId + " :" + e.getMessage());
                continue;
            }
            try {
                smev3Service.sendRatesRequest(ratesRequest);
            } catch (ParseException | DatatypeConfigurationException | JAXBException e) {
                LOGGER.error("Error while trying to send request for mkgu_id = " + mkguId + " from mkgu_packets_view :" + e.getMessage());
                continue;
            }
            for (SendedPackageInfo sendedPackageInfo : sendedPackageInfoList) {
                qualHistoryService.updateHistoryByIdKeyAndIdOtdel(sendedPackageInfo.getId_key(), sendedPackageInfo.getId_otdel());
            }
        }
    }

    /**
     * Метод подготовки запроса по пакетом из вьюшки mkgu_from_packets
     * @param mkguId идениификатор мкгу из БД
     * @return готовый к отправке объект запроса
     * @throws DatatypeConfigurationException
     */
    private RatesRequest prepareRatesRequest(String mkguId, MkguBasePackageService mkguBasePackageService) throws DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        sendedPackageInfoList.clear();
        final Pageable pageable = new PageRequest(0, 5);
        RatesRequest ratesRequest = factory.createRatesRequest();

        // заполняем базовые данные запроса
        // id_mkgu - это vendor_id
        // данные для vendor_id из поля id_mkgu не проходят валидацию по xsd-схеме rates.xsd
        // !!!!схема в проекте скорректирована!!!!
        VendorType vendor = factory.createVendorType();
        String formattedMkguId = mkguId.split("-")[0];
        vendor.setId(BigInteger.valueOf(Long.valueOf(formattedMkguId)));
        ratesRequest.setVendor(vendor);

        RatesRequest.Forms forms = factory.createRatesRequestForms();
        // по каждому mkgu_id ищем список id_key для дальнейшего формирования блоков сообщений Form
        final List<MkguIds> packetsGrouppedByIdKey = mkguBasePackageService.findPacketsByIdMkguGroupByIdKey(mkguId, pageable);
        if(packetsGrouppedByIdKey.isEmpty()){
            return null;
        }
        for (MkguIds packet : packetsGrouppedByIdKey) {
            // находим информацию по пакету для заполнения тега Form
            final List<MkguFormInfo> packetInfoList = mkguBasePackageService.findPacketsByIdMkguAndIdKey(packet.getId_mkgu(), packet.getId_key());
            if(packetInfoList.isEmpty()){
                continue;
            }
            final MkguFormInfo mkguFormInfo = packetInfoList.get(0);
            final RatesRequest.Forms.Form iasmkguForm = prepareMkguForm(mkguFormInfo, factory);

            // Поиск записей с оценками для напонения тегов Rates
            final List<BaseMkguPacketsView> fullPackages = mkguBasePackageService.findPackagesByIdKeyAndMkguId(packet.getId_key(), packet.getId_mkgu());
            if(fullPackages.isEmpty()){
                continue;
            }

            final List<RateType> rates = prepareRates(fullPackages);
            iasmkguForm.setRates(factory.createRatesRequestFormsFormRates());
            iasmkguForm.getRates().getRate().addAll(rates);
            forms.getForm().add(iasmkguForm);

            SendedPackageInfo sendedPackageInfo = new SendedPackageInfo(packet.getId_key(), packet.getId_otdel());
            sendedPackageInfoList.add(sendedPackageInfo);
        }
        ratesRequest.setForms(forms);
        return ratesRequest;
    }

    private List<RateType> prepareRates(List<BaseMkguPacketsView> fullPackages){
        List<RateType> rates = new ArrayList<>();
        for (BaseMkguPacketsView fullPackage : fullPackages) {
            RateType rateType = new RateType();
            rateType.setIndicatorId(BigInteger.valueOf(Long.valueOf(fullPackage.getIndicator_id())));
            rateType.setValueId(BigInteger.valueOf(Long.valueOf(fullPackage.getValue_id())));
            rates.add(rateType);
        }
        return rates;
    }

    private RatesRequest.Forms.Form prepareMkguForm(MkguFormInfo mkguFormInfo, ObjectFactory factory) throws DatatypeConfigurationException {
        RatesRequest.Forms.Form iasmkguForm = factory.createRatesRequestFormsForm();
        XMLGregorianCalendar currentDate =
                DateUtil.dateToXMLGregorianCalendar(new Date());

        iasmkguForm.setForeignId(String.valueOf(mkguFormInfo.getId_key()));
        iasmkguForm.setMkguId(BigInteger.valueOf(0));

        DataType dataType = factory.createDataType();

        UserType userType = factory.createUserType();
        userType.setId(String.valueOf(mkguFormInfo.getUser_id()));
        dataType.setUser(userType);

        AuthorityType authorityType = factory.createAuthorityType();
        authorityType.setId(mkguFormInfo.getId_authority());
        authorityType.setValue(mkguFormInfo.getName_authority());
        dataType.setAuthority(authorityType);

        ServiceType serviceType = factory.createServiceType();
        serviceType.setId(mkguFormInfo.getId_service());
        serviceType.setValue(mkguFormInfo.getName_service());
        dataType.setService(serviceType);

        ProcedureType procedureType = factory.createProcedureType();
        procedureType.setId("");
        dataType.setProcedure(procedureType);

        dataType.setOkato(mkguFormInfo.getOkato());
        dataType.setReceivedDate(DateUtil.dateToXMLGregorianCalendar(mkguFormInfo.getDate_event()));
        dataType.setDate(currentDate);

        iasmkguForm.setData(dataType);
        return iasmkguForm;
    }
}
