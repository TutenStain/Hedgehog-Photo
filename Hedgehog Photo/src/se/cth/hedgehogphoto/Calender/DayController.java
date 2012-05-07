package se.cth.hedgehogphoto.Calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayController implements ActionListener{
	private int day;
	private CalendarModel mm = CalendarModel.getInstance();
	
	public DayController(int day){
		this.day = day;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Day")){
			Files f = Files.getInstance();
		f.setPictureList(mm.getPictures(day));//SÄTT LISTA FILES TILL DETTA
		System.out.print("Get Picture" + mm.getPictures(day));
		}
	}

}
