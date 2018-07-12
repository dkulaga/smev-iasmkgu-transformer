package com.skat.smev.iasmkgu.transform;

import com.skat.smev.iasmkgu.domain.packets.PacketsRequestModel;
import com.skat.smev.iasmkgu.model.packets.PacketsObjectFactory;
import com.skat.smev.iasmkgu.model.packets.PacketsRequest;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.text.ParseException;

/**
 *  Класс для преобразования json-запроса в xml о схеме вида сведений
 */
public class PacketsTransformer {

    /**
     * Метод преобразования запроса из JSON-модели {@link PacketsRequestModel} XML-модель {@link PacketsRequest}
     * @param model модель запроса из JSON
     * @return XML-модель вида сведения
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public PacketsRequest createPacketsRequestFromModel(
            PacketsRequestModel model) throws ParseException,
            DatatypeConfigurationException {
        PacketsObjectFactory factory = new PacketsObjectFactory();
        PacketsRequest packetsRequest = factory.createPacketsRequest();

        com.skat.smev.iasmkgu.model.packets.VendorType vendor = factory.createVendorType();
        vendor.setId(BigInteger.valueOf(Long.valueOf(model.getVendorId())));
        packetsRequest.setVendor(vendor);
        packetsRequest.setPacketId(BigInteger.valueOf(Long.valueOf(model.getPacketId())));
        return packetsRequest;
    }

}
