package com.skat.smev.iasmkgu.util.snils;

import com.skat.smev.iasmkgu.domain.RequestModel;
import com.skat.smev.iasmkgu.model.*;
import com.skat.smev.iasmkgu.util.DateUtil;
import com.skat.smev.iasmkgu.util.XmlUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;

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
    public SnilsByDataRequest createSnilsByDataRequest(
            RequestModel model) throws ParseException,
            DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        SnilsByDataRequest snilsByDataRequest = factory.createSnilsByDataRequest();
        XMLGregorianCalendar formattedBirthDate = DateUtil.parseXMLGregorianCalendar(
                model.getBirthDate(), "yyyy-MM-dd");
        snilsByDataRequest.setBirthDate(formattedBirthDate);
        snilsByDataRequest.setFamilyName(model.getFamilyName());
        snilsByDataRequest.setFirstName(model.getFirstName());
        snilsByDataRequest.setPatronymic(model.getPatronymic());
        snilsByDataRequest.setGender(model.getGender() == null? GenderType.MALE :
                GenderType.FEMALE.value().equalsIgnoreCase(model.getGender().toString()) ?
                        GenderType.FEMALE : GenderType.MALE);

        return snilsByDataRequest;
    }

    /**
     * Метод преобразования ответа от сервиса
     * @param xml тело xml-ответа
     * @return возвращает сущность ответа необходимого вида сведений
     * @throws Exception
     */
    public SnilsByDataResponse parseContent(String xml)
            throws Exception {
        return XmlUtil.unmarshal(xml, SnilsByDataResponse.class);
    }
}
