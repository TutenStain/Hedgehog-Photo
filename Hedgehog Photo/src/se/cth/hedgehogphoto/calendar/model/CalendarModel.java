package se.cth.hedgehogphoto.calendar.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.PictureObject;

public class CalendarModel extends Observable implements Observer {
	private DatabaseAccess db;
	private static List<PictureObject> pics;
	private int month;
	private int maxDays;
	private int year;
	private static CalendarModel model;
	private Map<Integer, List<PictureObject>> pictureDays;
	private List<Integer> dayswithPicture = new ArrayList<Integer>();
	private GregorianCalendar georgianCalendar = new GregorianCalendar();

	private CalendarModel(DatabaseAccess db){
		this.db = db;
		this.month = this.georgianCalendar.get(this.georgianCalendar.MONTH) + 1;
		this.year = this.georgianCalendar.get(this.georgianCalendar.YEAR);
		this.maxDays = this.georgianCalendar.getActualMaximum(this.georgianCalendar.DAY_OF_MONTH);
		this.importDates();
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
		return this.maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	public int getYear() {
		return this. year;
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
		if(((this.month)%12)!=1){
			this.month = this.month - 1;

		}else{
			this.month = 12;
			this.year = this.year - 1;
		}
		Date date = new Date(this.year, this.month - 1, 1);
		this.georgianCalendar.setTime(date);
		this.maxDays = this.georgianCalendar.getActualMaximum(georgianCalendar.DAY_OF_MONTH);
		this.importDates();

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
		Date date = new Date(this.year, this.month - 1, 1);
		this.georgianCalendar.setTime(date);

		this.maxDays = this.georgianCalendar.getActualMaximum(georgianCalendar.DAY_OF_MONTH);
		this.importDates();

		setChanged();
		notifyObservers();
	}

	public void importDates(){
		this.pictureDays = new HashMap<Integer, List<PictureObject>>();
		this.dayswithPicture = new ArrayList<Integer>();
		this.pics = new ArrayList<PictureObject>();

		for(int i = 1; i <= this.maxDays;i++){
			String beforeMonth;
			if(this.month < 10){
				beforeMonth = "-0";
			}else{
				beforeMonth = "-";
			}
			String beforeDay;
			if(i < 10){
				beforeDay = "-0";
			}else{
				beforeDay = "-";
			}
			
			this.pics = (List<PictureObject>) this.db.findByDate(this.year + beforeMonth + this.month + beforeDay + i);
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
		return this.georgianCalendar;
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
	
	@Override
	public void update(Observable o, Object arg) {
		this.importDates();
		
		setChanged();
		notifyObservers();
	}
}

