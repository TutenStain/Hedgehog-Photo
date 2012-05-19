package se.cth.hedgehogphoto.calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import se.cth.hedgehogphoto.database.Files;

public class DayController implements ActionListener{
	private int day;
	private Files f;
	private CalendarModel model;

	public DayController(int day, Files f){
		this.day = day;
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Day")){
			this.f.setPictureList(model.getPictures(day));
		}
	}

}
