package se.cth.hedgehogphoto.calendar.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.calendar.controller.ButtonController;
import se.cth.hedgehogphoto.calendar.model.CalendarModel;
import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;



public class CalendarView extends JPanel implements Observer{
	private static CalendarView cv;
	private JLabel monthText;
	private CalendarModel calendarModel;
	private CalendarView(){
	}

	private CalendarView(DatabaseAccess da, Files files){
		setLayout(new BorderLayout());
		setSize(new Dimension(200,200));
		calendarModel = CalendarModel.getInstance(da);
		ButtonController bc = new ButtonController(da);
		JPanel jp = new JPanel();
		calendarModel.addObserver(this);
		jp.setLayout(new GridLayout(1,3));
		jp.setSize(30, 30);
		JButton back = new JButton("Back");
		back.setMaximumSize(new Dimension(5,5));
		JButton forward = new JButton("Forward");
		forward.setSize(10,10);
		jp.add(back);
		JPanel month = new JPanel();
		month.setLayout(new BorderLayout());
		monthText = new JLabel(); 
		changeMonthText();
		month.add(monthText,BorderLayout.SOUTH);
		jp.add(month, BorderLayout.SOUTH);
		jp.add(forward);
		back.addActionListener(bc);
		back.setActionCommand("Back");
		forward.addActionListener(bc);
		forward.setActionCommand("Forward");
		GregorianCalendar g = calendarModel.getCalendar();
		add(jp,BorderLayout.NORTH);
		add(DatesView.getInstance(da,files),BorderLayout.CENTER);
		setVisible(true);
		addObserverto(calendarModel);
	}
	public static CalendarView getInstance(DatabaseAccess da, Files files){
		if(cv == null){
			cv = new CalendarView(da, files);
		}
		return cv;
	}
	public void addObserverto(Observable o){
		o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		changeMonthText();
		System.out.print("UPPDATE MAIN");
	}
	public void changeMonthText(){
		monthText.setText(calendarModel.getMonthasString());
	}
}
