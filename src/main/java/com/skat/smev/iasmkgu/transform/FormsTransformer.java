package com.skat.smev.iasmkgu.transform;

import com.skat.smev.iasmkgu.domain.forms.FormsRequestModel;
import com.skat.smev.iasmkgu.model.forms.FormsObjectFactory;
import com.skat.smev.iasmkgu.model.forms.FormsRequest;
import com.skat.smev.iasmkgu.model.forms.VendorType;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.text.ParseException;

/**
 *  Класс для преобразования json-запроса в xml о схеме вида сведений
 */
public class FormsTransformer {

    /**
     * Метод преобразования запроса из JSON-модели {@link FormsRequestModel} XML-модель {@link FormsRequest}
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public FormsRequest createFormsRequestFromModel(
            FormsRequestModel model) throws ParseException,
            DatatypeConfigurationException {
        FormsObjectFactory factory = new FormsObjectFactory();
        FormsRequest formsRequest = factory.createFormsRequest();

        VendorType vendor = factory.createVendorType();
        vendor.setId(BigInteger.valueOf(Long.valueOf(model.getVendorId())));
        formsRequest.setVendor(vendor);
        formsRequest.setFormType(model.getFormType());
        return formsRequest;
    }

}
