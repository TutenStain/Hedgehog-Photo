package se.cth.hedgehogphoto.calendar.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.calendar.controller.DayController;
import se.cth.hedgehogphoto.calender.model.CalendarModel;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;

@SuppressWarnings("serial")
public class DatesView extends JPanel implements Observer{
	private static DatesView dw;
	private CalendarModel model;
	private JPanel jp;
	private Files f;
	
	private DatesView(DatabaseAccess db, Files f) { 
		this.f = f;
		this.setPreferredSize(new Dimension(150,75));
		this.setLayout(new BorderLayout());
		this.model = CalendarModel.getInstance(db);
		this.model.addObserver(this);
		this.jp = new JPanel();
		this.jp.setLayout(new GridLayout(5,7));
		setVisible(true);
		addDays();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		addDays();
	}

	public static DatesView getInstance(DatabaseAccess db, Files f){
		if(dw==null){
			dw = new DatesView(db, f);
		}
		return dw;
	}
	
	private void addDays(){
		this.removeAll();
		this.jp.removeAll();
		for(int i=1 ; i<=model.getMaxDays(); i++){
			Button j = new Button(i + "");
			for(Integer integer : model.getList()){
				if(integer.equals(i)){
					j.setBackground(Color.BLACK);
					j.addActionListener(new DayController(i, f));
					j.setActionCommand("Day");

				}else{
					j.setEnabled(false);
				}
			}
			j.setVisible(true);
			this.jp.add(j);
			add(this.jp, BorderLayout.CENTER);
			revalidate();
		}
	}
}
