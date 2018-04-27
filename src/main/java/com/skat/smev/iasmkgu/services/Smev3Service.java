package com.skat.smev.iasmkgu.services;


import com.skat.smev.iasmkgu.domain.*;
import com.skat.smev.iasmkgu.model.events.EventsRequest;
import com.skat.smev.iasmkgu.model.events.EventsResponse;
import com.skat.smev.iasmkgu.model.rates.RatesRequest;
import com.skat.smev.iasmkgu.transmitter.impl.ResponseTransmitterService;
import com.skat.smev.iasmkgu.transmitter.impl.Smev3AdapterService;
import com.skat.smev.iasmkgu.util.JsonUtil;
import com.skat.smev.iasmkgu.util.XmlUtil;
import com.skat.smev.iasmkgu.util.snils.IasmkguRequestTransformer;
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
     * Метод для приема ответа от СМЭВ-адаптера, его парсинга и отправки в ВИС
     * @param adapterResponse модель ответа от СМЭВ-адаптера
     * @return сведения об успешной отправке либо об ошибке отправки
     * @throws Exception
     */
    public String sendResponse(AdapterResponseModel adapterResponse)  {
        BaseMessageModel baseMessageModel = null;
        String stringMessage = "";
        try {
            baseMessageModel = parseResponseFromAdapter(adapterResponse);
            stringMessage = JsonUtil.stringify(baseMessageModel);
        } catch (Exception e) {
            LOGGER.error("Error while parsing adapter response: " + e.getMessage());
        }
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
     * Метод выполняет преобразование строки данный из формата base64
     * в cтроку UTF-8
     * @param xmlBase64 строка данных
     * @return строка данный UTF-8
     */
    private String getXmlFromBase64(String xmlBase64) {
        String request = "";
        if (xmlBase64.isEmpty()) {
            return request;
        }
        final String requestInBase64 = new String(DatatypeConverter.parseBase64Binary(xmlBase64), Charset.forName("UTF-8"));
        LOGGER.info("Decoded response from base64: " + requestInBase64);
        return requestInBase64;
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
        IasmkguRequestTransformer iasmkguRequestTransformer = new IasmkguRequestTransformer();
        EventsRequest element = iasmkguRequestTransformer.createEventsRequest(model);
        String xml = XmlUtil.jaxbObjectToXML(element, EventsRequest.class);
        return xml;
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
        IasmkguRequestTransformer iasmkguRequestTransformer = new IasmkguRequestTransformer();
        RatesRequest element = iasmkguRequestTransformer.createRatesRequest(model);
        String xml = XmlUtil.jaxbObjectToXML(element, RatesRequest.class);
        return xml;
    }

    /**
     * Метод выполняет преобразование ответа от СМЭВ-адаптера
     * из формата {@link EventsRequest} в формат {@link BaseMessageModel}
     * @param adapterResponseModel ответ от СМЭВ-адаптера
     * @return формированный ответ для дальнейшей отправки в ВИС
     * @throws Exception
     */
    private BaseMessageModel parseResponseFromAdapter(AdapterResponseModel adapterResponseModel) throws Exception {
        LOGGER.info("Try to parse response from adapter");
        LOGGER.info("Response: " + adapterResponseModel);

        if(adapterResponseModel.getResponse() != null){
            String xml = getXmlFromBase64(adapterResponseModel.getResponse());
            IasmkguRequestTransformer iasmkguRequestTransformer = new IasmkguRequestTransformer();
            EventsResponse responseType = iasmkguRequestTransformer.parseContent(xml);
            String responseNumber = String.valueOf(responseType.getPacket().getId());
            ResponseMessageModel responseMessageModel = new ResponseMessageModel();
            responseMessageModel.setResponseNumber(responseNumber);
            responseMessageModel.setMessageId(adapterResponseModel.getMessageId());
            return responseMessageModel;
        } else if(adapterResponseModel.getRejects() != null && !adapterResponseModel.getRejects().isEmpty()){
            RejectMessageModel rejectMessageModel = new RejectMessageModel();
            rejectMessageModel.setMessageId(adapterResponseModel.getMessageId());
            rejectMessageModel.setRejects(adapterResponseModel.getRejects());
            return rejectMessageModel;
        } else {
            StatusMessageModel statusMessageModel = new StatusMessageModel();
            statusMessageModel.setMessageId(adapterResponseModel.getMessageId());
            statusMessageModel.setDescription(adapterResponseModel.getDescription());
            return statusMessageModel;
        }
    }
}
