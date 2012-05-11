package se.cth.hedgehogphoto.calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;

public class DayController implements ActionListener{
	private int day;
	private CalendarModel mm;
	public DayController(DaoFactory df){
		mm = CalendarModel.getInstance(df);
	}
	public DayController(int day){
		this.day = day;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Day")){
			Files f = Files.getInstance();
		f.setPictureList(mm.getPictures(day));//S�TT LISTA FILES TILL DETTA
		System.out.print("Get Picture" + mm.getPictures(day));
		}
	}

}
