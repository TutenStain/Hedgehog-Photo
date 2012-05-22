package se.cth.hedgehogphoto.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;
import se.cth.hedgehogphoto.database.DatabaseAccess;

public class ButtonController implements ActionListener{
	private CalendarModel model;

	public ButtonController(DatabaseAccess da){
		 this.model = CalendarModel.getInstance(da);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")){
			model.backwards();
		}if(e.getActionCommand().equals("Forward")){
			model.forwards();
		}
	}

}
