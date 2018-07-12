package com.skat.smev.iasmkgu.domain.rates;

import java.util.List;

/**
 * 
 * Модель json-запроса
 *
 */
public class RatesRequestModel {
	private String vendorId;
	private List<Form> forms;

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public void setForms(List<Form> forms) {
		this.forms = forms;
	}

	public List<Form> getForms() {
		return forms;
	}

}