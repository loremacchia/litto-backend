package com.macchiarini.lorenzo.litto_backend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 

public class DateHandler {
	public String toString(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}
	
	public Date toDate(String string) throws ParseException {
		return new SimpleDateFormat("dd-MM-yyyy").parse(string);  
	}
	
	public Date incrementDate(Date date, int weeks) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, weeks*7);
		return c.getTime();
	}
}