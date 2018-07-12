package com.skat.smev.iasmkgu.domain.events;

import java.util.List;

/**
 * 
 * Модель json-запроса
 *
 */
public class EventsRequestModel {
	private String vendorId;
	private String listVersion;
	private List<Event> events;

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getListVersion() {
		return listVersion;
	}

	public void setListVersion(String listVersion) {
		this.listVersion = listVersion;
	}
}