package se.cth.hedgehogphoto.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.log.Log;

/**
 * A simple startup/loaing screen that gets
 * displayed until the database is up and running.
 * This is also the part of the program where the main
 * frame is constructed and initialized. 
 * @author Barnabas Sapan
 */

//JWindow
public class StartUpView extends JFrame implements Runnable{
	
	public StartUpView(){
		super("Hedgehog Photo");
	}
	
	@Override
	public void run(){
		setSize(new Dimension(Constants.PREFERRED_STARTUP_WINDOW_WIDTH, Constants.PREFERRED_STARTUP_WINDOW_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(StartUpView.class.getResource("/se/cth/hedgehogphoto/resources/boot.gif")));
		p.add(image);
		
		LoadingLayer layer = new LoadingLayer(p);
		add(layer.getDecoratedPanel());
		
		//Center the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		setLocation(x, y);
		
		setVisible(true);
		layer.start();
		
		//Start and wait for the database to finish loading
		Thread t = new Thread(DatabaseHandler.getInstance());
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			Log.getLogger().log(Level.WARNING, "Interrupted", e);
		}
		
		layer.stopAndRemove();
	}
}
