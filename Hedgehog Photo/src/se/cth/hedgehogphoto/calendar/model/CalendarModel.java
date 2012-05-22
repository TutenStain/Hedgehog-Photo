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
	private DatabaseAccess da;
	private static List<PictureObject> pics;
	private int month;
	private int maxDays;
	private int year;
	private static CalendarModel model;
	private Map<Integer, List<PictureObject>> pictureDays;
	private List<Integer> dayswithPicture = new ArrayList<Integer>();
	private GregorianCalendar gregorianCalendar = new GregorianCalendar();
	
	private  CalendarModel(DatabaseAccess da){
		this.da = da;
		this.month = gregorianCalendar.get(gregorianCalendar.MONTH)+1;
		this.year = gregorianCalendar.get(gregorianCalendar.YEAR);
		this.maxDays = gregorianCalendar.getActualMaximum(gregorianCalendar.DAY_OF_MONTH);
		this.getDates();
	}
	
	public synchronized static CalendarModel getInstance(DatabaseAccess da2){
		if(model==null){
			model = new CalendarModel(da2);
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
		return maxDays;
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
		return this.pictureDays;
	}

	public void setPictureDays(Map<Integer, List<PictureObject>> pictureDays) {
		this.pictureDays = pictureDays;
	}

	public List<Integer> getDayswithPicture() {
		return this.dayswithPicture;
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
		
		Date date = new Date(this.year, this.month-1,1);
		this.gregorianCalendar.setTime(date);
		this.maxDays = this.gregorianCalendar.getActualMaximum(this.gregorianCalendar.DAY_OF_MONTH);
		this.getDates();
		setChanged();
		notifyObservers();
	}
	
	public void forwards(){
		if(((this.month) % 12) != 0){
			this.month = this.month +1;

		}else{
			this.month = 1;
			this.year = this.year + 1;
		}
		@SuppressWarnings("deprecation")
		Date date = new Date(this.year, this.month-1,1);
		this.gregorianCalendar.setTime(date);
		
		this.maxDays = this.gregorianCalendar.getActualMaximum(this.gregorianCalendar.DAY_OF_MONTH);
		this.getDates();
		setChanged();
		notifyObservers();
	}
	
	public void getDates(){
		this.pictureDays = new HashMap<Integer, List<PictureObject>>();
		this.dayswithPicture = new ArrayList<Integer>();
		pics = new ArrayList<PictureObject>();
		
		for(int i = 1; i <= this.maxDays; i++){
			try{
			this.pics.addAll((List<PictureObject>) this.da.findByDate(year + ".0" + month + "." + i));
			}catch(Exception e){
			}try{
			pics.addAll((List<PictureObject>) this.da.findByDate(year + ":0" + month + ":" + i));
			}catch(Exception e){	
			}
			try{
			pics.addAll((List<PictureObject>) this.da.findByDate(year + "-0" + month + "-" + i));
			}catch(Exception e){
			pics.addAll((List<PictureObject>) this.da.findByDate(year + ":" + month + ":" + i));
			}try{
			pics.addAll((List<PictureObject>) this.da.findByDate(year + "-" + month + "-" + i));
			}catch(Exception e){
			}
			try{
			pics.addAll( (List<PictureObject>) this.da.findByDate(year + "." + month + "." + i));
			}catch(Exception e){
				
			}
				if(!(this.pics.isEmpty())){
			
				this.pictureDays.put(i, this.pics); 
				this.dayswithPicture.add(i);
			}
		}
	}
	
	public List<PictureObject> getPictures(Integer key){
		if(!(this.pictureDays.isEmpty())){
			return this.pictureDays.get(key);
		}
		return null;
	}
	
	public List<Integer> getList(){
		return this.dayswithPicture;
	}
	
	public Map<Integer, List<PictureObject>> getMap(){
		return this.pictureDays;
	}

	public GregorianCalendar getCalendar() {
		return this.gregorianCalendar;
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


