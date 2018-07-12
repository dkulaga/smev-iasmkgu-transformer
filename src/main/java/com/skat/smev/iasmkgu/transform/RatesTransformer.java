package com.skat.smev.iasmkgu.transform;

import com.skat.smev.iasmkgu.domain.rates.Form;
import com.skat.smev.iasmkgu.domain.rates.FormData;
import com.skat.smev.iasmkgu.domain.rates.Rate;
import com.skat.smev.iasmkgu.domain.rates.RatesRequestModel;
import com.skat.smev.iasmkgu.model.rates.*;
import com.skat.smev.iasmkgu.util.DateUtil;

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
public class RatesTransformer {

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
        RatesObjectFactory factory = new RatesObjectFactory();
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

}
