package com.skat.smev.iasmkgu.services;


import com.skat.smev.iasmkgu.domain.events.EventsRequestModel;
import com.skat.smev.iasmkgu.domain.forms.FormsRequestModel;
import com.skat.smev.iasmkgu.domain.packets.PacketsRequestModel;
import com.skat.smev.iasmkgu.domain.rates.RatesRequestModel;
import com.skat.smev.iasmkgu.model.events.EventsRequest;
import com.skat.smev.iasmkgu.model.forms.FormsRequest;
import com.skat.smev.iasmkgu.model.packets.PacketsRequest;
import com.skat.smev.iasmkgu.model.rates.RatesRequest;
import com.skat.smev.iasmkgu.transform.EventsTransformer;
import com.skat.smev.iasmkgu.transform.FormsTransformer;
import com.skat.smev.iasmkgu.transform.PacketsTransformer;
import com.skat.smev.iasmkgu.transform.RatesTransformer;
import com.skat.smev.iasmkgu.transmitter.impl.ResponseTransmitterService;
import com.skat.smev.iasmkgu.transmitter.impl.Smev3AdapterService;
import com.skat.smev.iasmkgu.util.XmlUtil;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.nio.charset.Charset;
import java.text.ParseException;

/**
 * Сервис для осуществления взаимодействия данного сервиса-трансформатора
 * со СМЭВ-адаптером и с ВИС
 */
@Service
public class Smev3Service {

    private static final Logger LOGGER = Logger.getLogger(Smev3Service.class.getName());

    @Autowired
    private Smev3AdapterService smev3AdapterService;

    @Autowired
    private ResponseTransmitterService responseTransmitterService;

    /**
     * Метод преобразования и отправки запроса от ВИС и отправки в СМЭВ-адаптер
     * @param eventsRequestModel модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    public String sendEventsRequest(EventsRequestModel eventsRequestModel) throws ParseException, JAXBException, DatatypeConfigurationException {
        final String adapterRequest = createEventsXmlFromModel(eventsRequestModel);
        final String base64request = convertToBase64(adapterRequest);
        try {
            return smev3AdapterService.sendRequest(base64request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error while sending request";
    }

    /**
     * Метод преобразования и отправки запроса от ВИС и отправки в СМЭВ-адаптер
     * @param ratesRequestModel модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    public String sendRatesRequest(RatesRequestModel ratesRequestModel) throws ParseException, JAXBException, DatatypeConfigurationException {
        final String adapterRequest = createRatesXmlFromModel(ratesRequestModel);
        final String base64request = convertToBase64(adapterRequest);
        try {
            return smev3AdapterService.sendRequest(base64request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error while sending request";
    }

    /**
     * Метод преобразования и отправки запроса от ВИС и отправки в СМЭВ-адаптер
     * @param packetsRequestModel модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    public String sendPacketsRequest(PacketsRequestModel packetsRequestModel) throws ParseException, JAXBException, DatatypeConfigurationException {
        final String adapterRequest = createPacketsXmlFromModel(packetsRequestModel);
        final String base64request = convertToBase64(adapterRequest);
        try {
            return smev3AdapterService.sendRequest(base64request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error while sending request";
    }

    /**
     * Метод преобразования и отправки запроса от ВИС и отправки в СМЭВ-адаптер
     * @param formsRequestModel модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    public String sendFormsRequest(FormsRequestModel formsRequestModel) throws ParseException, JAXBException, DatatypeConfigurationException {
        final String adapterRequest = createFormsXmlFromModel(formsRequestModel);
        final String base64request = convertToBase64(adapterRequest);
        try {
            return smev3AdapterService.sendRequest(base64request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error while sending request";
    }

    /**
     * Метод отправки запроса от ВИС и отправки в СМЭВ-адаптер
     * @param ratesRequest модель запроса в формате XML
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    public String sendRatesRequest(RatesRequest ratesRequest) throws ParseException, JAXBException, DatatypeConfigurationException {
        LOGGER.info("sendRatesRequest started");
        String xml = XmlUtil.ratesJaxbObjectToXML(ratesRequest);
        LOGGER.info("ratesJaxbObjectToXML success: "+xml);
        final String base64request = convertToBase64(xml);
        LOGGER.info("base64request success: "+base64request);
        try {
            return smev3AdapterService.sendRequest(base64request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error while sending request";
    }



    public String sendResponse(String stringMessage)  {
        return responseTransmitterService.sendResponse(stringMessage);
    }

    /**
     * Метод выполняет преобразование строки данный в формат base64
     * @param request строка данных
     * @return строка данный в формате base64
     */
    private String convertToBase64(String request){
        byte[] bytesInfo = request.getBytes(Charset.forName("UTF-8"));
        return bytesInfo != null ? DatatypeConverter.printBase64Binary(bytesInfo) : "";
    }



    /**
     * Метод выпоняет преобразование модели запроса от ВИС в формате SON
     * в модель вида сведений
     * @param model модель запроса от ВИС
     * @return  преобразованная модель вида сведений
     * @throws JAXBException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    private String createEventsXmlFromModel(EventsRequestModel model) throws JAXBException,
            ParseException, DatatypeConfigurationException {
        final EventsTransformer eventsTransformer = new EventsTransformer();
        final EventsRequest element = eventsTransformer.createEventsRequest(model);
        return XmlUtil.jaxbObjectToXML(element, EventsRequest.class);
    }

     /* Метод выпоняет преобразование модели запроса от ВИС в формате SON
     * в модель вида сведений
     * @param model модель запроса от ВИС
     * @return  преобразованная модель вида сведений
     * @throws JAXBException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    private String createRatesXmlFromModel(RatesRequestModel model) throws JAXBException,
            ParseException, DatatypeConfigurationException {
        final RatesTransformer ratesTransformer = new RatesTransformer();
        final RatesRequest element = ratesTransformer.createRatesRequestFromModel(model);
        return XmlUtil.jaxbObjectToXML(element, RatesRequest.class);
    }

    /* Метод выпоняет преобразование модели запроса от ВИС в формате SON
     * в модель вида сведений
     * @param model модель запроса от ВИС
     * @return  преобразованная модель вида сведений {@link PacketsRequest}
     * @throws JAXBException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    private String createPacketsXmlFromModel(PacketsRequestModel model) throws JAXBException,
            ParseException, DatatypeConfigurationException {
        final PacketsTransformer packetsTransformer = new PacketsTransformer();
        final PacketsRequest element = packetsTransformer.createPacketsRequestFromModel(model);
        return XmlUtil.jaxbObjectToXML(element, PacketsRequest.class);
    }

    /* Метод выпоняет преобразование модели запроса от ВИС в формате SON
     * в модель вида сведений
     * @param model модель запроса от ВИС
     * @return  преобразованная модель вида сведений {@link PacketsRequest}
     * @throws JAXBException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    private String createFormsXmlFromModel(FormsRequestModel model) throws JAXBException,
            ParseException, DatatypeConfigurationException {
        final FormsTransformer formsTransformer = new FormsTransformer();
        final FormsRequest element = formsTransformer.createFormsRequestFromModel(model);
        return XmlUtil.jaxbObjectToXML(element, FormsRequest.class);
    }




}
