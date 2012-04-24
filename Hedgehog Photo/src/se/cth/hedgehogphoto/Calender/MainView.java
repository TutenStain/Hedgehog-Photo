package se.cth.hedgehogphoto.Calender;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainView extends JPanel implements Observer{
	private static MainView mw;
	private MainModel m;
	private JPanel jp;
	private MainView() {
		//MainController mc = new MainController(); 
		this.setPreferredSize(new Dimension(150,75));
		this.setLayout(new BorderLayout());
		m = MainModel.getInstance();
		m.addObserver(this);
		jp = new JPanel();
		jp.setLayout(new GridLayout(5,7));
		this.setVisible(true);
		addDays();
		}
	//public void addObserverto(Observable o){
		//o.addObserver(this);
	//}
	@Override
	public void update(Observable arg0, Object arg1) {
		//this.mw= new MainView();
		addDays();
		System.out.print("UPDATE MAINVIEW");
	}
	
	public static MainView getInstance(){
	if(mw==null){
			mw = new MainView();
		}
			return mw;
		}
	public void addDays(){
		this.removeAll();
		jp.removeAll();
		//jp = new JPanel();
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
