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
import se.cth.hedgehogphoto.calendar.model.CalendarModel;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;


@SuppressWarnings("serial")
public class DatesView extends JPanel implements Observer{
	private static DatesView datesView;
	private CalendarModel model;
	private JPanel jp;
	private DatabaseAccess da;
	private Files files;
	private DatesView(DatabaseAccess da, Files files) {
		this.files = files;
		this.setPreferredSize(new Dimension(150,75));
		this.setLayout(new BorderLayout());
		this.model = CalendarModel.getInstance(da);
		this.model.addObserver(this);
		this.jp = new JPanel();
		this.jp.setLayout(new GridLayout(5,7));
		this.setVisible(true);
		addDays();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		addDays();
	}

	public static DatesView getInstance(DatabaseAccess da,Files files){
		if(datesView==null){
			datesView = new DatesView(da,files);
		}
		return datesView;
	}
	
	public void addDays(){
		this.removeAll();
		this.jp.removeAll();
		
		for(int i= 1; i <= model.getMaxDays(); i++){
			Button j = new Button(i + "");
			for(Integer integer : model.getList()){
				if(integer.equals(i)){
					j.setEnabled(true);
					j.setBackground(Color.BLACK);
					j.addActionListener(new DayController(da, model.getPictures(i), files));
					j.setActionCommand("Day");

				}else{
					j.setEnabled(false);
				}
			}
			j.setVisible(true);
			this.jp.add(j);
			this.add(jp, BorderLayout.CENTER);
			this.revalidate();
		}
	}
}
