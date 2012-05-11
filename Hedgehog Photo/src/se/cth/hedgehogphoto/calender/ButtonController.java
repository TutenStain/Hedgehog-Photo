package se.cth.hedgehogphoto.calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.DatabaseAccess;

public class ButtonController  implements ActionListener{
	CalendarModel m;
	public ButtonController(DaoFactory df){
		 m = CalendarModel.getInstance(df);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")){
			m.backwards();
			System.out.print("Back");
		}if(e.getActionCommand().equals("Forward")){
			m.forwards();
			System.out.print("Forwards");
		}
		
		
	}

}
