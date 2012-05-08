package se.cth.hedgehogphoto.calender;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;

public class DatesView extends JPanel implements Observer{
	private static DatesView dw;
	private CalendarModel m;
	private JPanel jp;
	private DatesView(DatabaseAccess da) { 
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

	public static DatesView getInstance(DatabaseAccess da){
		if(dw==null){
			dw = new DatesView(da);
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
					j.setBackground(Color.BLACK);
					j.addActionListener(new DayController(i));
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
