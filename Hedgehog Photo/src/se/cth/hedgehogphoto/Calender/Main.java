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


public class Main implements Observer{
	private static Main m;
	private JLabel monthText;
	private MainModel mainModel;
	 public static void main(String[] arg){
		 m = new Main();
	 }
	 private Main(){
		 JFrame j = new JFrame();
		 j.setLayout(new BorderLayout());
		 j.setSize(new Dimension(200,200));
		// j.setPreferredSize(new Dimension(200,200));
		mainModel = MainModel.getInstance();
	// m.forward();
		 ButtonController bc = new ButtonController();
		 JPanel jp = new JPanel();
		 mainModel.addObserver(this);
		 jp.setLayout(new GridLayout(1,3));
		 jp.setSize(30, 30);
		 //jp.setPreferredSize(new Dimension(150,75));
		//jp.add(new JLabel(m.getMonthasString())/*, BorderLayout.CENTER*/);
		JButton back = new JButton("Back");
		back.setMaximumSize(new Dimension(5,5));
		//back.setSize(10,10);
		JButton forward = new JButton("Forward");
		forward.setSize(10,10);
		jp.add(back);
		JPanel month = new JPanel();
		month.setLayout(new BorderLayout());
		monthText = new JLabel(); 
		changeMonthText();
		month.add(monthText,BorderLayout.SOUTH);
		jp.add(month, BorderLayout.CENTER);
		jp.add(forward);
		back.addActionListener(bc);
		back.setActionCommand("Back");
		forward.addActionListener(bc);
		forward.setActionCommand("Forward");
		GregorianCalendar g = mainModel.getCalendar();
		j.add(jp,BorderLayout.NORTH);
		 j.add(MainView.getInstance(),BorderLayout.CENTER);
		 j.setVisible(true);
		 j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 addObserverto(mainModel);
	 }
	 public static Main getInstance(){
		 if(m == null){
			 m = new Main();
		 }
		 return m;
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
		monthText.setText(mainModel.getMonthasString());//HTML DIREKT I KODEN
	}
}
