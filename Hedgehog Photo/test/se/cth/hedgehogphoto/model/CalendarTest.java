package se.cth.hedgehogphoto.model;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;
public class CalendarTest {
	private CalendarModel model;
	
	@Before
	public void setUp(){
		model = CalendarModel.getInstance(null);
		
	}
	
	@Test
	public void testSetMonth(){
		model.setMonth(12);
		int month = model.getMonth();
		assertTrue(month == 12);
	}
	@Test
	public void testSetYear(){
		model.setYear(12);
		int year = model.getYear();
		assertTrue(year == 12);
	}
	
	@Test
	public void testForwars(){
		model.setYear(2011);
		model.setMonth(12);
		model.forwards();
		assertTrue(model.getMaxDays() == 31 && model.getMonth() == 1 && model.getYear() == 2012);
		
	}
	@Test
	public void testBackwards(){
		model.setYear(2011);
		model.setMonth(12);
		model.backwards();
		assertTrue(model.getMaxDays() == 30 && model.getMonth() == 11 && model.getYear() == 2011);
		
	}
	@Test
	public void testSetDayswithPicture(){
		List<Integer> dayswithPicture = new ArrayList<Integer>();
		dayswithPicture.add(11);
		model.setDayswithPicture(dayswithPicture);
		assertTrue(model.getDayswithPicture().get(0) == 11);
		
	}

}
