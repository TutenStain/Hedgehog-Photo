package se.cth.hedgehogphoto.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;
import se.cth.hedgehogphoto.calendar.view.CalendarView;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.database.PictureObject;

public class DayController implements ActionListener{
	private int day;
	private CalendarModel mm;
	List<PictureObject> pc;
	private Files files;
	public DayController(DatabaseAccess da, List<PictureObject> pc, Files files){
		mm = CalendarModel.getInstance(da);
		this.pc = pc;
		this.files=files;
	}
	public DayController(int day){
		this.day = day;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Day")){
		files = Files.getInstance();
		files.setPictureList(pc);//SÄTT LISTA FILES TILL DETTA
		System.out.print("Get Picture" + pc);
		CalendarModel.getInstance(DatabaseHandler.getInstance()).importDates();
		}
	}

}