package com.skat.smev.iasmkgu.util.snils;

import com.skat.smev.iasmkgu.domain.Form;
import com.skat.smev.iasmkgu.domain.FormData;
import com.skat.smev.iasmkgu.domain.Rate;
import com.skat.smev.iasmkgu.domain.RatesRequestModel;
import com.skat.smev.iasmkgu.model.events.EventsResponse;
import com.skat.smev.iasmkgu.model.rates.*;
import com.skat.smev.iasmkgu.util.DateUtil;
import com.skat.smev.iasmkgu.util.XmlUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Класс для преобразования json-запроса в xml о схеме вида сведений
 */
public class IasmkguRequestTransformer {

    /**
     * Метод преобразования запроса из JSON-модели XML-модель формата вида сведения
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
//    public EventsRequest createEventsRequest(
//            EventsRequestModel model) throws ParseException,
//            DatatypeConfigurationException {
//        ObjectFactory factory = new ObjectFactory();
//        EventsRequest eventsRequest = factory.createEventsRequest();
//        XMLGregorianCalendar currentDate =
//                DateUtil.dateToXMLGregorianCalendar(new Date());
//
//        EventsRequest.Vendor vendor = factory.createEventsRequestVendor();
//        vendor.setId(BigInteger.valueOf(Long.valueOf(model.getVendorId())));
//        vendor.setListVersion(model.getListVersion());
//        eventsRequest.setVendor(vendor);
//
//        EventsRequest.Events events = factory.createEventsRequestEvents();
//        for(Event event: model.getEvents()){
//            EventsRequest.Events.Event iasmkguEvent = factory.createEventsRequestEventsEvent();
//            iasmkguEvent.setForeignId(event.getForeignId());
//            iasmkguEvent.setMobile(event.getMobile());
//            iasmkguEvent.setEmail(event.getEmail());
//
//            AuthorityType authorityType = factory.createAuthorityType();
//            authorityType.setId(event.getAuthorityId());
//            iasmkguEvent.setAuthority(authorityType);
//
//            ServiceType serviceType = factory.createServiceType();
//            serviceType.setId(event.getServiceId());
//            serviceType.setValue(event.getServiceName());
//            iasmkguEvent.setService(serviceType);
//
//            ProcedureType procedureType = factory.createProcedureType();
//            procedureType.setId(event.getProcedureId());
//            procedureType.setValue(event.getProcedureName());
//            iasmkguEvent.setProcedure(procedureType);
//
//            iasmkguEvent.setOkato(event.getOkato());
//            iasmkguEvent.setReceivedDate(currentDate);
//            events.getEvent().add(iasmkguEvent);
//        }
//        eventsRequest.setEvents(events);
//
//        return eventsRequest;
//    }

    /**
     * Метод преобразования запроса из JSON-модели XML-модель формата вида сведения
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public RatesRequest createRatesRequestFromModel(
            RatesRequestModel model) throws ParseException,
            DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        RatesRequest ratesRequest = factory.createRatesRequest();
        XMLGregorianCalendar currentDate =
                DateUtil.dateToXMLGregorianCalendar(new Date());

        VendorType vendor = factory.createVendorType();
        vendor.setId(BigInteger.valueOf(Long.valueOf(model.getVendorId())));
        ratesRequest.setVendor(vendor);

        RatesRequest.Forms forms = factory.createRatesRequestForms();
        for(Form form: model.getForms()){
            RatesRequest.Forms.Form iasmkguForm = factory.createRatesRequestFormsForm();
            iasmkguForm.setForeignId(form.getForeignId());
            iasmkguForm.setMkguId(BigInteger.valueOf(Long.valueOf(form.getMkguId())));

            DataType dataType = factory.createDataType();
            final FormData formData = form.getFormData();

            UserType userType = factory.createUserType();
            userType.setId(formData.getUserId());
            userType.setValue(formData.getUserEmail());
            dataType.setUser(userType);

            AuthorityType authorityType = factory.createAuthorityType();
            authorityType.setId(formData.getAuthorityId());
            authorityType.setValue(formData.getAuthorityName());
            dataType.setAuthority(authorityType);

            ServiceType serviceType = factory.createServiceType();
            serviceType.setId(formData.getServiceId());
            serviceType.setValue(formData.getServiceName());
            dataType.setService(serviceType);

            ProcedureType procedureType = factory.createProcedureType();
            procedureType.setId(formData.getProcedureId());
            procedureType.setValue(formData.getProcedureName());
            dataType.setProcedure(procedureType);

            dataType.setOkato(formData.getOkato());
            dataType.setReceivedDate(currentDate);
            dataType.setDate(currentDate);

            iasmkguForm.setData(dataType);

            List<Rate> rateList = form.getRates();
            List<RateType> rates = new ArrayList<>();
            for (Rate rateModel : rateList) {
                RateType rateType = new RateType();
                rateType.setIndicatorId(BigInteger.valueOf(Long.valueOf(rateModel.getIndicatorId())));
                rateType.setValue(rateModel.getValue());
                rateType.setValueId(BigInteger.valueOf(Long.valueOf(rateModel.getValueId())));
                rates.add(rateType);
            }
            iasmkguForm.setRates(factory.createRatesRequestFormsFormRates());
            iasmkguForm.getRates().getRate().addAll(rates);
            forms.getForm().add(iasmkguForm);
        }
        ratesRequest.setForms(forms);

        return ratesRequest;
    }

    /**
     * Метод преобразования запроса из JSON-модели XML-модель формата вида сведения
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
//    public RatesRequest createRatesRequestFromViewItems(
//            String id_mkgu, List<MkguFromPacketsView> viewPackets) throws ParseException,
//            DatatypeConfigurationException {
//        ObjectFactory factory = new ObjectFactory();
//        RatesRequest ratesRequest = factory.createRatesRequest();
//        XMLGregorianCalendar currentDate =
//                DateUtil.dateToXMLGregorianCalendar(new Date());
//
//        VendorType vendor = factory.createVendorType();
//        vendor.setId(BigInteger.valueOf(Long.valueOf(id_mkgu)));
//        ratesRequest.setVendor(vendor);
//
//        RatesRequest.Forms forms = factory.createRatesRequestForms();
//        for(MkguFromPacketsView packet: viewPackets){
//            RatesRequest.Forms.Form iasmkguForm = factory.createRatesRequestFormsForm();
//            iasmkguForm.setForeignId(packet.getForeignId());
//            iasmkguForm.setMkguId(BigInteger.valueOf(Long.valueOf(form.getMkguId())));
//
//            DataType dataType = factory.createDataType();
//
//            UserType userType = factory.createUserType();
//            userType.setId();
//            userType.setValue(formData.getUserEmail());
//            dataType.setUser(userType);
//
//            AuthorityType authorityType = factory.createAuthorityType();
//            authorityType.setId(formData.getAuthorityId());
//            authorityType.setValue(formData.getAuthorityName());
//            dataType.setAuthority(authorityType);
//
//            ServiceType serviceType = factory.createServiceType();
//            serviceType.setId(formData.getServiceId());
//            serviceType.setValue(formData.getServiceName());
//            dataType.setService(serviceType);
//
//            ProcedureType procedureType = factory.createProcedureType();
//            procedureType.setId(formData.getProcedureId());
//            procedureType.setValue(formData.getProcedureName());
//            dataType.setProcedure(procedureType);
//
//            dataType.setOkato(formData.getOkato());
//            dataType.setReceivedDate(currentDate);
//            dataType.setDate(currentDate);
//
//            iasmkguForm.setData(dataType);
//
//            List<Rate> rateList = form.getRates();
//            List<RateType> rates = new ArrayList<>();
//            for (Rate rateModel : rateList) {
//                RateType rateType = new RateType();
//                rateType.setIndicatorId(BigInteger.valueOf(Long.valueOf(rateModel.getIndicatorId())));
//                rateType.setValue(rateModel.getValue());
//                rateType.setValueId(BigInteger.valueOf(Long.valueOf(rateModel.getValueId())));
//                rates.add(rateType);
//            }
//            iasmkguForm.setRates(factory.createRatesRequestFormsFormRates());
//            iasmkguForm.getRates().getRate().addAll(rates);
//            forms.getForm().add(iasmkguForm);
//        }
//        ratesRequest.setForms(forms);
//
//        return ratesRequest;
//    }

    /**
     * Метод преобразования ответа от сервиса
     * @param xml тело xml-ответа
     * @return возвращает сущность ответа необходимого вида сведений
     * @throws Exception
     */
    public EventsResponse parseContent(String xml)
            throws Exception {
        return XmlUtil.unmarshal(xml, EventsResponse.class);
    }
}
