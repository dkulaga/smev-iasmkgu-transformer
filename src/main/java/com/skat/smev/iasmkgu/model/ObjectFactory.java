//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.21 at 06:35:14 PM MSK 
//


package com.skat.smev.iasmkgu.model;

import com.skat.smev.iasmkgu.model.events.EventsRequest;
import com.skat.smev.iasmkgu.model.events.EventsResponse;
import com.skat.smev.iasmkgu.model.rates.RatesRequest;
import com.skat.smev.iasmkgu.model.rates.RatesResponse;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.skat.smev.election package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.skat.smev.election
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EventsResponse }
     * 
     */
    public EventsResponse createEventsResponse() {
        return new EventsResponse();
    }

    /**
     * Create an instance of {@link EventsRequest }
     * 
     */
    public EventsRequest createEventsRequest() {
        return new EventsRequest();
    }

    /**
     * Create an instance of {@link EventsRequest.Events }
     * 
     */
    public EventsRequest.Events createEventsRequestEvents() {
        return new EventsRequest.Events();
    }

    /**
     * Create an instance of {@link EventsResponse.Packet }
     * 
     */
    public EventsResponse.Packet createEventsResponsePacket() {
        return new EventsResponse.Packet();
    }

    /**
     * Create an instance of {@link EventsRequest.Vendor }
     * 
     */
    public EventsRequest.Vendor createEventsRequestVendor() {
        return new EventsRequest.Vendor();
    }

    /**
     * Create an instance of {@link RatesRequest }
     *
     */
    public RatesRequest createRatesRequest() {
        return new RatesRequest();
    }

    /**
     * Create an instance of {@link RatesResponse }
     *
     */
    public RatesResponse createRatesResponse() {
        return new RatesResponse();
    }

    /**
     * Create an instance of {@link RatesRequest.Forms }
     *
     */
    public RatesRequest.Forms createRatesRequestForms() {
        return new RatesRequest.Forms();
    }

    /**
     * Create an instance of {@link RatesRequest.Forms.Form }
     *
     */
    public RatesRequest.Forms.Form createRatesRequestFormsForm() {
        return new RatesRequest.Forms.Form();
    }

     /**
     * Create an instance of {@link RatesResponse.Packet }
     *
     */
    public RatesResponse.Packet createRatesResponsePacket() {
        return new RatesResponse.Packet();
    }

    /**
     * Create an instance of {@link VendorType }
     * 
     */
    public VendorType createVendorType() {
        return new VendorType();
    }

    /**
     * Create an instance of {@link ProcedureType }
     * 
     */
    public ProcedureType createProcedureType() {
        return new ProcedureType();
    }

    /**
     * Create an instance of {@link ServiceType }
     * 
     */
    public ServiceType createServiceType() {
        return new ServiceType();
    }

    /**
     * Create an instance of {@link AuthorityType }
     * 
     */
    public AuthorityType createAuthorityType() {
        return new AuthorityType();
    }

    /**
     * Create an instance of {@link EventsRequest.Events.Event }
     * 
     */
    public EventsRequest.Events.Event createEventsRequestEventsEvent() {
        return new EventsRequest.Events.Event();
    }

    /**
     * Create an instance of {@link DataType }
     *
     */
    public DataType createDataType() {
        return new DataType();
    }

    /**
     * Create an instance of {@link RatesRequest.Forms.Form.Rates }
     *
     */
    public RatesRequest.Forms.Form.Rates createRatesRequestFormsFormRates() {
        return new RatesRequest.Forms.Form.Rates();
    }

    /**
     * Create an instance of {@link UserType }
     *
     */
    public UserType createUserType() {
        return new UserType();
    }


}
