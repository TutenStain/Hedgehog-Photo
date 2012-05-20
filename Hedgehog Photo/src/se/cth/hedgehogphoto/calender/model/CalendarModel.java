package se.cth.hedgehogphoto.calender.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.PictureObject;

public class CalendarModel extends Observable {
	private DatabaseAccess db;
	private int month;
	private int maxDays;
	private int year;
	private static CalendarModel m ;
	private Map<Integer, List<PictureObject>> pictureDays;
	private List<Integer> dayswithPicture = new ArrayList<Integer>();
	private GregorianCalendar g= new GregorianCalendar();

	private  CalendarModel(DatabaseAccess db){
		this.db = db;
		month = g.get(GregorianCalendar.MONTH) + 1; 
		System.out.print(g.get(GregorianCalendar.MONTH) + 1);
		year = g.get(GregorianCalendar.YEAR);
		System.out.println(year);
		maxDays = g.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
	}

	public static CalendarModel getInstance(DatabaseAccess db){
		if(m==null){
			m = new CalendarModel(db);
		}
		return m;
	}


	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Map<Integer, List<PictureObject>> getPictureDays() {
		return pictureDays;
	}

	public void setPictureDays(Map<Integer, List<PictureObject>> pictureDays) {
		this.pictureDays = pictureDays;
	}

	public List<Integer> getDayswithPicture() {
		return dayswithPicture;
	}

	public void setDayswithPicture(List<Integer> dayswithPicture) {
		this.dayswithPicture = dayswithPicture;
	}

	public void backwards(){
		if(((month) % 12) != 1){
			month = month - 1;

		}else{
			month = 12;
			year = year -1;
		}
		
		Date date = new Date(year,month-1,1); //TODO: Might want to change to Calendar.set(year + 1900, month, date) or GregorianCalendar(year + 1900, month, date)?
		g.setTime(date);
		maxDays = g.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
		
		setChanged();
		notifyObservers();
	}
	public void forwards(){
		if(((month) % 12) != 0){
			month = month + 1;
		}else{
			month = 1;
			year = year + 1;
		}
		
		@SuppressWarnings("deprecation")
		Date date = new Date(year,month-1,1);
		g.setTime(date);
		maxDays = g.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
		
		setChanged();
		notifyObservers();
	}
	
	public void getDates(){
		pictureDays = new HashMap<Integer, List<PictureObject>>();
		dayswithPicture = new ArrayList<Integer>(); 
		
		try{
			for(int i = 1; i<=maxDays;i++){
				List<PictureObject> pics =  new ArrayList<PictureObject>();
				pics.addAll(db.searchPicturesfromDates(year + "-" + month + "-" + i));
				pics.addAll(db.searchPicturesfromDates(year + ":" + month + ":" + i));
				
				if(!(pics.isEmpty()) && pics != null){
					pictureDays.put(i, pics);
					dayswithPicture.add(i);
				}
			}
		}catch(Exception h){

		}
	}
	public List<PictureObject> getPictures(Integer key){
		if(!(pictureDays.isEmpty())){
			return pictureDays.get(key);
		}
		return null;
	}
	
	public List<Integer> getList(){
		return dayswithPicture;
	}
	
	public Map<Integer, List<PictureObject>> getMap(){
		return pictureDays;
	}

	public GregorianCalendar getCalendar() {
		return g;
	}
	
	public String getMonthasString(){
		switch (month){
		case 1:
			return "January";
		case 2:
			return "Febuary";
		case 3:
			return "Mars";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "Juni";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return "January";
		}
	}

}


