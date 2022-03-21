package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;

public class DateDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dateFrom;
	private String dateTo;

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
}
