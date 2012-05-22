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
	private JPanel panel;
	private DatabaseAccess da;
	private Files files;
	
	private DatesView(DatabaseAccess da, Files files) {
		this.files = files;
		this.setPreferredSize(new Dimension(150,75));
		this.setLayout(new BorderLayout());
		this.model = CalendarModel.getInstance(da);
		this.model.addObserver(this);
		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(5,7));
		this.setVisible(true);
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
		this.panel.removeAll();
		
		for(int i = 1; i <= this.model.getMaxDays(); i++){
			Button button = new Button(i + "");
			for(Integer integer : this.model.getList()){
				if(integer.equals(i)){
					button.setEnabled(true);
					button.setBackground(Color.BLACK);
					button.addActionListener(new DayController(this.da, this.model.getPictures(i), this.files));
					button.setActionCommand("Day");
				}else{
					button.setEnabled(false);
				}
			}
			button.setVisible(true);
			this.panel.add(button);
			this.add(this.panel, BorderLayout.CENTER);
			revalidate();
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		this.addDays();
	}
}
