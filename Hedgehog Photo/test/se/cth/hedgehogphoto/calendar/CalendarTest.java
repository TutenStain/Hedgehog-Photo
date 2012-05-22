package se.cth.hedgehogphoto.calendar;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;

import se.cth.hedgehogphoto.database.DatabaseHandler;

public class CalendarTest {
	private CalendarModel cm;
	
	@Before
	public void setUp(){
		cm = CalendarModel.getInstance(null);
		
	}
	
	@Test
	public void testSetMonth(){
		cm.setMonth(12);
		int month = cm.getMonth();
		assertTrue(month == 12);
	}
	@Test
	public void testSetYear(){
		cm.setYear(12);
		int year = cm.getYear();
		assertTrue(year == 12);
	}
	
	@Test
	public void testForwars(){
		cm.setYear(2011);
		cm.setMonth(12);
		cm.forwards();
		assertTrue(cm.getMaxDays() == 31 && cm.getMonth() == 1 && cm.getYear() == 2012);
		
	}
	@Test
	public void testBackwards(){
		cm.setYear(2011);
		cm.setMonth(12);
		cm.backwards();
		assertTrue(cm.getMaxDays() == 30 && cm.getMonth() == 11 && cm.getYear() == 2011);
		
	}

}
