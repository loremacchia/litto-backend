package com.macchiarini.lorenzo.litto_backend.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public String toString(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}
	
	public Date toDate(String string) {
		try {
			Date date = new SimpleDateFormat("dd.MM.yyyy").parse(string);
			return date;   
		} catch (Exception e) {
			return null;
		}
	}
	
	public Date incrementDate(Date date, int weeks) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, weeks*7);
		return c.getTime();
	}
}