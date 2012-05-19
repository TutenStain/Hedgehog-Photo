package se.cth.hedgehogphoto.calender;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;

@SuppressWarnings("serial")
public class CalendarView extends JPanel implements Observer{
	private static CalendarView cv;
	private JLabel monthText;
	private CalendarModel calendarModel;
	
	private CalendarView(){
	}

	private CalendarView(DatabaseAccess da, Files f){
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
		month.add(this.monthText, BorderLayout.SOUTH);
		jp.add(month, BorderLayout.SOUTH);
		jp.add(forward);
		back.addActionListener(bc);
		back.setActionCommand("Back");
		forward.addActionListener(bc);
		forward.setActionCommand("Forward");
		add(jp,BorderLayout.NORTH);
		add(DatesView.getInstance(da, f),BorderLayout.CENTER);
		setVisible(true);
		addObserverto(calendarModel);
	}
	
	public static CalendarView getInstance(DatabaseAccess db, Files f){
		if(cv == null){
			cv = new CalendarView(db, f);
		}
		return cv;
	}
	
	public void addObserverto(Observable o){
		o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		changeMonthText();
	}
	
	private void changeMonthText(){
		monthText.setText(calendarModel.getMonthasString());
	}
}
