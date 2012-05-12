package se.cth.hedgehogphoto.calender;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DaoFactory;


public class CalendarView extends JPanel implements Observer{
	private static CalendarView cv;
	private JLabel monthText;
	private CalendarModel calendarModel;
	private CalendarView(){
	}

	private CalendarView(DaoFactory df){
		setLayout(new BorderLayout());
		setSize(new Dimension(200,200));
		calendarModel = CalendarModel.getInstance(df);
		ButtonController bc = new ButtonController(df);
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
		add(DatesView.getInstance(df),BorderLayout.CENTER);
		setVisible(true);
		addObserverto(calendarModel);
	}
	public static CalendarView getInstance(DaoFactory df){
		if(cv == null){
			cv = new CalendarView(df);
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
