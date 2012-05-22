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
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;



@SuppressWarnings("serial")
public class CalendarView extends JPanel implements Observer{
	private static CalendarView view;
	private JLabel monthText;
	private CalendarModel calendarModel;
	private JLabel yearText;
	
	private CalendarView(){
	}

	private CalendarView(DatabaseAccess da, Files files){
		setLayout(new BorderLayout());
		setSize(new Dimension(200,200));
		this.calendarModel = CalendarModel.getInstance(da);
		ButtonController bc = new ButtonController(da);
		JPanel jp = new JPanel();
		this.calendarModel.addObserver(this);
		jp.setLayout(new GridLayout(1,3));
		jp.setSize(30, 30);
		JButton back = new JButton("Back");
		back.setMaximumSize(new Dimension(5,5));
		JButton forward = new JButton("Forward");
		forward.setSize(10,10);
		jp.add(back);
		JPanel month = new JPanel();
		month.setLayout(new BorderLayout());
		this.monthText = new JLabel(); 
		changeMonthText();
		month.add(monthText,BorderLayout.SOUTH);
		JLabel yearText = new JLabel();
		changeYearText();
		month.add(yearText,BorderLayout.EAST);
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
		if(view == null){
			view = new CalendarView(da, files);
		}
		return view;
	}
	
	public void addObserverto(Observable o){
		o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		changeMonthText();
		changeYearText();
	}
	
	public void changeMonthText(){
		this.monthText.setText(this.calendarModel.getMonthasString());
	}
	public void changeYearText(){
		this.yearText.setText(this.calendarModel.getYear()+"");
	}
}
