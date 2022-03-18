package com.macchiarini.lorenzo.litto_backend.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {

	/**
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
	
	/**
	 * @param string
	 * @return
	 */
	public static Date toDate(String string) {
		try {
			Date date = new SimpleDateFormat("dd.MM.yyyy").parse(string);
			return date;   
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @param date
	 * @param weeks
	 * @return
	 */
	public static Date incrementDate(Date date, int weeks) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, weeks*7);
		return c.getTime();
	}
}

