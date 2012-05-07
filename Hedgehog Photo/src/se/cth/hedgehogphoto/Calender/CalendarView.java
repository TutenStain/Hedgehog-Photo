package se.cth.hedgehogphoto.Calender;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.plugin.InitializePlugin;


public class CalendarView implements Observer{
	private static CalendarView cv;
	private JLabel monthText;
	private CalendarModel mainModel;
	private CalendarView(){
	}
	@InitializePlugin
	public void start() {
		JFrame j = new JFrame();
		j.setLayout(new BorderLayout());
		j.setSize(new Dimension(200,200));
		mainModel = CalendarModel.getInstance();
		ButtonController bc = new ButtonController();
		JPanel jp = new JPanel();
		mainModel.addObserver(this);
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
		GregorianCalendar g = mainModel.getCalendar();
		j.add(jp,BorderLayout.NORTH);
		j.add(DatesView.getInstance(),BorderLayout.CENTER);
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addObserverto(mainModel);
	}
	public static CalendarView getInstance(){
		if(cv == null){
			cv = new CalendarView();
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
		monthText.setText(mainModel.getMonthasString());
	}
}
