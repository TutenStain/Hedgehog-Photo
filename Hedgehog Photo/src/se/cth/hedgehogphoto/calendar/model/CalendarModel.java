package se.cth.hedgehogphoto.calendar.model;

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
	private static CalendarModel model ;
	private Map<Integer, List<PictureObject>> pictureDays;
	private List<Integer> dayswithPicture = new ArrayList<Integer>();
	private GregorianCalendar gregorianCalendar= new GregorianCalendar();

	private  CalendarModel(DatabaseAccess db){
		this.db = db;
		this.month = gregorianCalendar.get(GregorianCalendar.MONTH) + 1; 
		System.out.print(gregorianCalendar.get(GregorianCalendar.MONTH) + 1);
		this.year = gregorianCalendar.get(GregorianCalendar.YEAR);
		System.out.println(this.year);
		this.maxDays = gregorianCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
	}

	public static CalendarModel getInstance(DatabaseAccess db){
		if(model == null){
			model = new CalendarModel(db);
		}
		return model;
	}


	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMaxDays() {
		return this.maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	public int getYear() {
		return this.year;
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
		if(((this.month) % 12) != 1){
			this.month = this.month - 1;

		}else{
			this.month = 12;
			this.year = this.year -1;
		}
		
		Date date = new Date(this.year,this.month-1,1); //TODO: Might want to change to Calendar.set(this.year + 1900, this.month, date) or GregorianCalendar(this.year + 1900, this.month, date)?
		gregorianCalendar.setTime(date);
		this.maxDays = gregorianCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
		
		setChanged();
		notifyObservers();
	}
	public void forwards(){
		if(((this.month) % 12) != 0){
			this.month = this.month + 1;
		}else{
			this.month = 1;
			this.year = this.year + 1;
		}
		
		@SuppressWarnings("deprecation")
		Date date = new Date(this.year,this.month-1,1);
		gregorianCalendar.setTime(date);
		this.maxDays = gregorianCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		getDates();
		
		setChanged();
		notifyObservers();
	}
	
	public void getDates(){
		pictureDays = new HashMap<Integer, List<PictureObject>>();
		dayswithPicture = new ArrayList<Integer>(); 
		
		try{
			for(int i = 1; i<=this.maxDays;i++){
				List<PictureObject> pics =  new ArrayList<PictureObject>();
				pics.addAll(db.searchPicturesfromDates(this.year + "-" + this.month + "-" + i));
				pics.addAll(db.searchPicturesfromDates(this.year + ":" + this.month + ":" + i));
				
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
		return gregorianCalendar;
	}
	
	public String getMonthasString(){
		switch (this.month){
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


