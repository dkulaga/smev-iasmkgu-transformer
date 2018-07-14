package com.skat.smev.iasmkgu.transform;

import com.skat.smev.iasmkgu.domain.AdapterResponseModel;
import com.skat.smev.iasmkgu.domain.BaseMessageModel;
import com.skat.smev.iasmkgu.domain.forms.*;
import com.skat.smev.iasmkgu.model.forms.FormsObjectFactory;
import com.skat.smev.iasmkgu.model.forms.FormsRequest;
import com.skat.smev.iasmkgu.model.forms.FormsResponse;
import com.skat.smev.iasmkgu.model.forms.VendorType;
import com.skat.smev.iasmkgu.util.XmlUtil;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *  Класс для преобразования json-запроса в xml о схеме вида сведений
 */
public class FormsTransformer extends BaseTransform {

    private static final Logger LOGGER = Logger.getLogger(FormsTransformer.class.getName());

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

    public BaseMessageModel parseFormsResponseFromAdapter(AdapterResponseModel adapterResponseModel) throws JAXBException {
        LOGGER.info("Try to parse response from adapter");
        LOGGER.info("Response: " + adapterResponseModel);

        if(adapterResponseModel.getResponse() != null){
            String xml = getXmlFromBase64(adapterResponseModel.getResponse());
            FormsResponse responseType = null;
            responseType = XmlUtil.unmarshal(xml, FormsResponse.class);
            FormsResponseModel formsResponseModel = createResponseForAis(responseType);
            formsResponseModel.setMessageId(adapterResponseModel.getMessageId());
            return formsResponseModel;
        } else if(adapterResponseModel.getRejects() != null && !adapterResponseModel.getRejects().isEmpty()){
            return parseRegectsResponse(adapterResponseModel);
        } else {
            return parseStatusResponse(adapterResponseModel);
        }
    }

    private FormsResponseModel createResponseForAis(FormsResponse response){
        FormsResponseModel formsResponseModel = new FormsResponseModel();
        formsResponseModel.setServiceType(response.getForm().getServiceType());
        formsResponseModel.setVersion(response.getForm().getVersion());
        formsResponseModel.setUpdatedAt(response.getForm().getUpdatedAt());

        final FormsResponse.Blocks blocks = response.getBlocks();
        List<Block> blockList = new ArrayList<>();
        for (FormsResponse.Blocks.Block block : blocks.getBlock()) {
            Block formBlock = new Block();
            formBlock.setId(block.getId().toString());
            formBlock.setOptional(block.getOptional());
            formBlock.setQueue(block.getQueue());
            formBlock.setValue(block.getValue());
            blockList.add(formBlock);
        }
        formsResponseModel.setBlocks(blockList);

        final List<FormsResponse.Indicators.Indicator> indicator = response.getIndicators().getIndicator();
        List<FormIndicator> formIndicators = new ArrayList<>();
        for (FormsResponse.Indicators.Indicator ind : indicator) {
            FormIndicator formIndicator = new FormIndicator();
            formIndicator.setBlockId(ind.getBlockId().toString());
            formIndicator.setDescription(ind.getDescription());
            formIndicator.setId(ind.getId().toString());
            formIndicator.setQueue(ind.getQueue().toString());
            formIndicator.setTitle(ind.getTitle());

            final List<FormsResponse.Indicators.Indicator.Values.Value> values = ind.getValues().getValue();
            List<FormValue> formValues = new ArrayList<>();
            for (FormsResponse.Indicators.Indicator.Values.Value value : values) {
                FormValue formValue = new FormValue();
                formValue.setId(value.getId().toString());
                formValue.setDataType(value.getDataType());
                formValue.setDescription(value.getDescription());
                formValue.setTitle(value.getTitle());
                formValue.setAltTitle(value.getAltTitle());
                formValue.setMin(value.getMin());
                formValue.setMax(value.getMax());
                formValues.add(formValue);
            }
            formIndicator.setValues(formValues);
            formIndicators.add(formIndicator);
        }
        formsResponseModel.setFormIndicators(formIndicators);

        return formsResponseModel;
    }
}
