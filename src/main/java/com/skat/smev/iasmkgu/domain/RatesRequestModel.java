package com.skat.smev.iasmkgu.domain;

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

	public List<Form> getForms() {
		return forms;
	}

}