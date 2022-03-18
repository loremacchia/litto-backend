package com.macchiarini.lorenzo.litto_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 

/**
 * @author lorem
 *
 */
public class DateHandler {
	
	/**
	 * @param string
	 * @return
	 */
	public static String fromDBtoClient (String string) { 
		return toString(fromDBDate(string));
	}
	
	/**
	 * @param string
	 * @return
	 */
	public static String fromClientToDB(String string) {
		return toDBDate(toDate(string));
	}
	
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
	 * @param string
	 * @param weeks
	 * @return
	 */
	public static String incrementDate(String string, int weeks) {
		Date date = toDate(string);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, weeks*7);
		return toString(c.getTime());
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String toDBDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date); 
	}
	
	/**
	 * @param string
	 * @return
	 */
	public static Date fromDBDate(String string) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(string);
			return date;  
		} catch (Exception e) {
			return null;
		}
	}
}