package com.walmart.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	private int code;  //business code useful to client.
	private String message;
	private int status;  //http status.

	public ErrorMessage(){}

	public ErrorMessage(int code, String message, int status) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
