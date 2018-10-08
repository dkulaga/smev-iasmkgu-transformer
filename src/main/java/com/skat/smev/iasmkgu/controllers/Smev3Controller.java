package com.skat.smev.iasmkgu.controllers;


import com.skat.smev.iasmkgu.domain.AdapterResponseModel;
import com.skat.smev.iasmkgu.domain.BaseMessageModel;
import com.skat.smev.iasmkgu.domain.events.EventsRequestModel;
import com.skat.smev.iasmkgu.domain.forms.FormsRequestModel;
import com.skat.smev.iasmkgu.domain.packets.PacketsRequestModel;
import com.skat.smev.iasmkgu.domain.rates.RatesRequestModel;
import com.skat.smev.iasmkgu.services.Smev3Service;
import com.skat.smev.iasmkgu.transform.BaseTransform;
import com.skat.smev.iasmkgu.transform.FormsTransformer;
import com.skat.smev.iasmkgu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/iasmkgu")
public class Smev3Controller {


    @Autowired
    private Smev3Service smev3Service;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    /**
     * Метод преобразования запроса для сервиса
     * "Прием данных о фактах оказания госуслуг из внешних ИС в ИАС МКГУ"
     * и отправки в СМЭВ-адаптер
     * @param request модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    @PostMapping("/events/request")
    public String sendEventsRequest(@RequestBody EventsRequestModel request) throws Exception {
        return smev3Service.sendEventsRequest(request);
    }

    /**
     * Метод преобразования запроса для сервиса
     * "Сервис приема оценок из внешних систем в ИАС МКГУ"
     * и отправки в СМЭВ-адаптер
     * @param request модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    @PostMapping("/rates/request")
    public String sendRatesRequest(@RequestBody RatesRequestModel request) throws Exception {
        return smev3Service.sendRatesRequest(request);
    }

    /**
     * Метод преобразования запроса для сервиса
     * "Сервис проверки статуса пакета в ИАС МКГУ"
     * и отправки в СМЭВ-адаптер
     * @param request модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    @PostMapping("/packets/request")
    public String sendPacketsRequest(@RequestBody PacketsRequestModel request) throws Exception {
        return smev3Service.sendPacketsRequest(request);
    }

    /**
     * Метод преобразования запроса для сервиса
     * "Сервис актуализации опросной формы ИАС МКГУ о качестве предоставления государственных услуг"
     * и отправки в СМЭВ-адаптер
     * @param request модель запроса в формате JSON
     * @return  возвращает сведения об успешности отправки запроса
     * @throws Exception
     */
    @PostMapping("/forms/request")
    public String sendFormsRequest(@RequestBody FormsRequestModel request) throws Exception {
        return smev3Service.sendFormsRequest(request);
    }

    /**
     * Метод для приема ответа от СМЭВ-адаптера, его парсинга и отправки в ВИС
     * @param adapterResponse модель ответа от СМЭВ-адаптера
     * @return сведения об успешной отправке либо об ошибке отправки
     * @throws Exception
     */
    @PostMapping("/response/send")
    public String sendConsumerResponse(@RequestBody AdapterResponseModel adapterResponse) throws Exception {
        final BaseTransform baseTransform = new BaseTransform();
        final BaseMessageModel baseMessageModel = baseTransform.parseResponseFromAdapter(adapterResponse);
        return smev3Service.sendResponse(JsonUtil.stringify(baseMessageModel));
    }

    /**
     * Метод для приема ответа от СМЭВ-адаптера, его парсинга и отправки в ВИС
     * @param adapterResponse модель ответа от СМЭВ-адаптера
     * @return сведения об успешной отправке либо об ошибке отправки
     * @throws Exception
     */
    @PostMapping("/forms/response")
    public String sendFormsResponse(@RequestBody AdapterResponseModel adapterResponse) throws Exception {
        final FormsTransformer formsTransformer = new FormsTransformer();
        final BaseMessageModel baseMessageModel = formsTransformer.parseFormsResponseFromAdapter(adapterResponse);
        return smev3Service.sendResponse(JsonUtil.stringify(baseMessageModel));
    }


}
