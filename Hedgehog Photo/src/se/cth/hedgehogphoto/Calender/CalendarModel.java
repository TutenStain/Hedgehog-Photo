package se.cth.hedgehogphoto.Calender;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import se.cth.hedgehogphoto.database.*;



public class CalendarModel extends Observable {
	private DatabaseHandler db = DatabaseHandler.getInstance();
	private static List<Picture> pics;
	private int month;
	private int maxDays;
	private int year;
	private static CalendarModel m ;
	private Map<Integer, List<Picture>> pictureDays;
	private List<Integer> dayswithPicture = new ArrayList<Integer>();
	private GregorianCalendar g= new GregorianCalendar();
	private  CalendarModel(){
		month = g.get(g.MONTH)+1;
		System.out.print(g.get(g.MONTH)+1);
		year = g.get(g.YEAR);
		System.out.println(year);
		maxDays = g.getActualMaximum(g.DAY_OF_MONTH);
		System.out.print(maxDays);
		getDates();


	}
	public static CalendarModel getInstance(){
	//	if(m==null){
			m = new CalendarModel();
		//}
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

	public Map<Integer, List<Picture>> getPictureDays() {
		return pictureDays;
	}

	public void setPictureDays(Map<Integer, List<Picture>> pictureDays) {
		this.pictureDays = pictureDays;
	}

	public List<Integer> getDayswithPicture() {
		return dayswithPicture;
	}

	public void setDayswithPicture(List<Integer> dayswithPicture) {
		this.dayswithPicture = dayswithPicture;
	}

	public void backwards(){
		if(((month)%12)!=1){
			//g.set(year,month-1,1);
			month = month -1;

		}else{
		//	g.set((year-1),12,1);
			month = 12;
			year = year -1;
		}
		Date date = new Date(year,month-1,1);
		g.setTime(date);
		maxDays = g.getActualMaximum(g.DAY_OF_MONTH);
		getDates();
		setChanged();
		notifyObservers();
		//	System.out.print("Månad " + month + "år" + year + "back");
	}
	public void forwards(){
		if(((month)%12)!=0){
			month = month +1;

		}else{
			month = 1;
			year = year +1;
		}
		@SuppressWarnings("deprecation")
		Date date = new Date(year,month-1,1);
		g.setTime(date);
	//g.setLenient(false);
		
	//	g.set(GregorianCalendar.YEAR, year);
		//g.set(GregorianCalendar.MONTH, month+1);
		//g.set(GregorianCalendar.DATE, 1);
		
		maxDays = g.getActualMaximum(g.DAY_OF_MONTH);
		getDates();
		setChanged();
		notifyObservers();
	}
	public void getDates(){
		pictureDays = new HashMap<Integer, List<Picture>>();
		dayswithPicture = new ArrayList<Integer>(); 
		for(int i = 1; i<=maxDays;i++){
			List<Picture> pics=  db.searchPicturesfromDates(year + "-" + month + "-" + i);
			pics.addAll(db.searchPicturesfromDates(year + ":" + month + ":" + i));
			if(!(pics.isEmpty()) && pics != null){
				pictureDays.put(i,pics);
				dayswithPicture.add(i);
			}
		}
	}
	public List<Picture> getPictures(Integer key){
		if(!(pictureDays.isEmpty())){
			return pictureDays.get(key);
		}
		return null;
	}
	public List<Integer> getList(){
		return dayswithPicture;
	}
	public Map<Integer, List<Picture>> getMap(){
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


