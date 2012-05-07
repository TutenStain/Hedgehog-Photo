package se.cth.hedgehogphoto.Calender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController  implements ActionListener{
	CalendarModel m = CalendarModel.getInstance();
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
