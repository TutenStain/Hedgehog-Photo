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
		this.calendarModel = CalendarModel.getInstance(da);
		this.calendarModel.addObserver(this);
		ButtonController buttonController = new ButtonController(da);
		
		setLayout(new BorderLayout());
		setSize(new Dimension(200,200));
	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.setSize(30, 30);
		
		JButton back = new JButton("Back");
		back.setMaximumSize(new Dimension(5,5));
		panel.add(back);
		back.addActionListener(buttonController);
		back.setActionCommand("Back");
		
		JButton forward = new JButton("Forward");
		forward.setSize(10,10);
		panel.add(forward);
		forward.addActionListener(buttonController);
		forward.setActionCommand("Forward");
		
		
		JPanel month = new JPanel();
		month.setLayout(new BorderLayout());
		this.monthText = new JLabel(); 
		this.changeMonthText();
		month.add(this.monthText,BorderLayout.SOUTH);
		panel.add(month, BorderLayout.SOUTH);
		
		JLabel yearText = new JLabel();
		this.changeYearText();
		month.add(yearText,BorderLayout.EAST);
	
		add(panel,BorderLayout.NORTH);
		add(DatesView.getInstance(da,files),BorderLayout.CENTER);
		
		setVisible(true);
		addObserverTo(this.calendarModel);
	}
	
	public static CalendarView getInstance(DatabaseAccess da, Files files){
		if(view == null){
			view = new CalendarView(da, files);
		}
		return view;
	}
	
	public void addObserverTo(Observable o){
		o.addObserver(this);
	}
	
	public void changeMonthText(){
		this.monthText.setText(this.calendarModel.getMonthasString());
	}
	
	public void changeYearText(){
		this.yearText.setText(this.calendarModel.getYear()+ "");
	}

	@Override
	public void update(Observable o, Object arg) {
		changeMonthText();
		changeYearText();
	}
}
