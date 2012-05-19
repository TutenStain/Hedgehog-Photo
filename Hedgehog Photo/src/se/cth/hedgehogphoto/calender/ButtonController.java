package se.cth.hedgehogphoto.calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.cth.hedgehogphoto.database.DatabaseAccess;


public class ButtonController implements ActionListener{
	private CalendarModel m;
	
	public ButtonController(DatabaseAccess db){
		m = CalendarModel.getInstance(db);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")){
			m.backwards();
		}if(e.getActionCommand().equals("Forward")){
			m.forwards();
		}
	}
}
