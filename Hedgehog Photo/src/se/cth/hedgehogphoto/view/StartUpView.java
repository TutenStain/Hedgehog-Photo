package se.cth.hedgehogphoto.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JLabel;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.log.Log;

/**
 * 
 * @author Barnabas Sapan
 *
 */

public class StartUpView extends JFrame implements Runnable{

	@Override
	public void run(){
		setSize(new Dimension(300, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new JLabel("LOADING..."));
		
		//Center the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		setLocation(x, y);
		
		setVisible(true);
		
		//Start and wait for the database to finnish loading
		Thread t = new Thread(DatabaseHandler.getInstance());
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			Log.getLogger().log(Level.WARNING, "Interrupted", e);
		}
	}
}
