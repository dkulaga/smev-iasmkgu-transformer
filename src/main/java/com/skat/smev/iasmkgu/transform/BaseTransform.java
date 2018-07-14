package com.skat.smev.iasmkgu.transform;


import com.skat.smev.iasmkgu.domain.*;
import com.skat.smev.iasmkgu.model.events.EventsRequest;
import com.skat.smev.iasmkgu.model.events.EventsResponse;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class BaseTransform {

    private static final Logger LOGGER = Logger.getLogger(BaseTransform.class.getName());
    /**
     * Метод выполняет преобразование ответа от СМЭВ-адаптера
     * из формата {@link EventsRequest} в формат {@link BaseMessageModel}
     * @param adapterResponseModel ответ от СМЭВ-адаптера
     * @return формированный ответ для дальнейшей отправки в ВИС
     * @throws Exception
     */
    public BaseMessageModel parseResponseFromAdapter(AdapterResponseModel adapterResponseModel) throws Exception {
        LOGGER.info("Try to parse response from adapter");
        LOGGER.info("Response: " + adapterResponseModel);

        if(adapterResponseModel.getResponse() != null){
            String xml = getXmlFromBase64(adapterResponseModel.getResponse());
            EventsTransformer iasmkguRequestTransformer = new EventsTransformer();
            EventsResponse responseType = iasmkguRequestTransformer.parseEventsResponseContent(xml);
            String responseNumber = String.valueOf(responseType.getPacket().getId());
            ResponseMessageModel responseMessageModel = new ResponseMessageModel();
            responseMessageModel.setResponseNumber(responseNumber);
            responseMessageModel.setMessageId(adapterResponseModel.getMessageId());
            return responseMessageModel;
        } else if(adapterResponseModel.getRejects() != null && !adapterResponseModel.getRejects().isEmpty()){
            return parseRegectsResponse(adapterResponseModel);
        } else {
            return parseStatusResponse(adapterResponseModel);
        }
    }

    public RejectMessageModel parseRegectsResponse(AdapterResponseModel adapterResponseModel){
        RejectMessageModel rejectMessageModel = new RejectMessageModel();
        rejectMessageModel.setMessageId(adapterResponseModel.getMessageId());
        rejectMessageModel.setRejects(adapterResponseModel.getRejects());
        return rejectMessageModel;
    }

    public StatusMessageModel parseStatusResponse(AdapterResponseModel adapterResponseModel){
        StatusMessageModel statusMessageModel = new StatusMessageModel();
        statusMessageModel.setMessageId(adapterResponseModel.getMessageId());
        statusMessageModel.setDescription(adapterResponseModel.getDescription());
        return statusMessageModel;
    }

    /**
     * Метод выполняет преобразование строки данный из формата base64
     * в cтроку UTF-8
     * @param xmlBase64 строка данных
     * @return строка данный UTF-8
     */
    public String getXmlFromBase64(String xmlBase64) {
        String request = "";
        if (xmlBase64.isEmpty()) {
            return request;
        }
        final String requestInBase64 = new String(DatatypeConverter.parseBase64Binary(xmlBase64), Charset.forName("UTF-8"));
        LOGGER.info("Decoded response from base64: " + requestInBase64);
        return requestInBase64;
    }
}
