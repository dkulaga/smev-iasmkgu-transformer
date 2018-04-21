package com.skat.smev.iasmkgu.domain;

import java.util.List;

public class ResponseMessageModel extends BaseMessageModel {

	private String responseNumber;
	private List<String> attachments;

	public String getResponseNumber() {
		return responseNumber;
	}

	public void setResponseNumber(String responseNumber) {
		this.responseNumber = responseNumber;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "ResponseMessageModel [responseNumber=" + responseNumber
				+ ", attachments=" + attachments + "]";
	}

}