package com.skat.smev.iasmkgu.controllers;


import com.skat.smev.iasmkgu.domain.AdapterResponseModel;
import com.skat.smev.iasmkgu.domain.EventsRequestModel;
import com.skat.smev.iasmkgu.domain.RatesRequestModel;
import com.skat.smev.iasmkgu.services.Smev3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/iasmkgu")
public class Smev3Controller {


    @Autowired
    private Smev3Service smev3Service;

    /**
     * Метод преобразования запроса ля сервиса
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
     * Метод преобразования запроса ля сервиса
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
     * Метод для приема ответа от СМЭВ-адаптера, его парсинга и отправки в ВИС
     * @param adapterResponse модель ответа от СМЭВ-адаптера
     * @return сведения об успешной отправке либо об ошибке отправки
     * @throws Exception
     */
    @PostMapping("/response/send")
    public String sendConsumerResponse(@RequestBody AdapterResponseModel adapterResponse) throws Exception {
        return smev3Service.sendResponse(adapterResponse);
    }


}
