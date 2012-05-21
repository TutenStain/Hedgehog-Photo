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
import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;


public class DatesView extends JPanel implements Observer{
	private static DatesView dw;
	private CalendarModel m;
	private JPanel jp;
	private DatabaseAccess da;
	private Files files;
	private DatesView(DatabaseAccess da, Files files) {
		files = files;
		this.setPreferredSize(new Dimension(150,75));
		this.setLayout(new BorderLayout());
		m = CalendarModel.getInstance(da);
		m.addObserver(this);
		jp = new JPanel();
		jp.setLayout(new GridLayout(5,7));
		this.setVisible(true);
		addDays();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		addDays();
		System.out.print("UPDATE MAINVIEW");
	}

	public static DatesView getInstance(DatabaseAccess da,Files files){
		if(dw==null){
			dw = new DatesView(da,files);
		}
		return dw;
	}
	public void addDays(){
		this.removeAll();
		jp.removeAll();
		for(int i=1;i<=m.getMaxDays();i++){
			Button j = new Button(i+"");
			for(Integer integer:m.getList()){
				if(integer.equals(i)){
					j.setEnabled(true);
					j.setBackground(Color.BLACK);
					j.addActionListener(new DayController(da, m.getPictures(i), files));
					j.setActionCommand("Day");

				}else{
					j.setEnabled(false);
				}
			}
			j.setVisible(true);
			jp.add(j);
			this.add(jp, BorderLayout.CENTER);
			this.revalidate();

		}
	}
}
