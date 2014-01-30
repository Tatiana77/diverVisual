package com.tatiana.web;

public class Status {
	private String message;
	private String status = "WORKING";
	private String progress = "0";
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public String getProgress() {
		return progress;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Status(final String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setProgress(final int value) {
		this.progress = new Integer(value).toString() + "%";
	}
}
