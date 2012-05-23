package se.cth.hedgehogphoto.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;

public class DayController implements ActionListener{
	private int day;
	private CalendarModel model;
	private List<PictureObject> pc;
	private Files files;
	
	public DayController(DatabaseAccess db, List<PictureObject> pc, Files files){
		this.model = CalendarModel.getInstance(db);
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
			files.setPictureList(pc);
		}
	}

}