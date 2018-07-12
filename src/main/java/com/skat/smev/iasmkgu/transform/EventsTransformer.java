package com.skat.smev.iasmkgu.transform;

import com.skat.smev.iasmkgu.domain.events.Event;
import com.skat.smev.iasmkgu.domain.events.EventsRequestModel;
import com.skat.smev.iasmkgu.model.events.*;
import com.skat.smev.iasmkgu.util.DateUtil;
import com.skat.smev.iasmkgu.util.XmlUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

/**
 *  Класс для преобразования json-запроса в xml о схеме вида сведений
 */
public class EventsTransformer {

    /**
     * Метод преобразования запроса из JSON-модели XML-модель формата вида сведения
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public EventsRequest createEventsRequest(
            EventsRequestModel model) throws ParseException,
            DatatypeConfigurationException {
        EventsObjectFactory factory = new EventsObjectFactory();
        EventsRequest eventsRequest = factory.createEventsRequest();
        XMLGregorianCalendar currentDate =
                DateUtil.dateToXMLGregorianCalendar(new Date());

        EventsRequest.Vendor vendor = factory.createEventsRequestVendor();
        vendor.setId(BigInteger.valueOf(Long.valueOf(model.getVendorId())));
        vendor.setListVersion(model.getListVersion());
        eventsRequest.setVendor(vendor);

        EventsRequest.Events events = factory.createEventsRequestEvents();
        for(Event event: model.getEvents()){
            EventsRequest.Events.Event iasmkguEvent = factory.createEventsRequestEventsEvent();
            iasmkguEvent.setForeignId(event.getForeignId());
            iasmkguEvent.setMobile(event.getMobile());
            iasmkguEvent.setEmail(event.getEmail());

            EventsAuthorityType eventsAuthorityType = factory.createAuthorityType();
            eventsAuthorityType.setId(event.getAuthorityId());
            iasmkguEvent.setAuthority(eventsAuthorityType);

            EventsServiceType serviceType = factory.createServiceType();
            serviceType.setId(event.getServiceId());
            serviceType.setValue(event.getServiceName());
            iasmkguEvent.setService(serviceType);

            EventsProcedureType procedureType = factory.createProcedureType();
            procedureType.setId(event.getProcedureId());
            procedureType.setValue(event.getProcedureName());
            iasmkguEvent.setProcedure(procedureType);

            iasmkguEvent.setOkato(event.getOkato());
            iasmkguEvent.setReceivedDate(currentDate);
            events.getEvent().add(iasmkguEvent);
        }
        eventsRequest.setEvents(events);

        return eventsRequest;
    }

    /**
     * Метод преобразования ответа от сервиса
     * @param xml тело xml-ответа
     * @return возвращает сущность ответа необходимого вида сведений
     * @throws Exception
     */
    public EventsResponse parseEventsResponseContent(String xml)
            throws Exception {
        return XmlUtil.unmarshal(xml, EventsResponse.class);
    }
}
