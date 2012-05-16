package se.cth.hedgehogphoto.map.geocoding.model;

import java.util.ArrayList;
import java.util.List;

public class MaxOneRequestPerSecondTest implements Runnable {
	private static MaxOneRequestPerSecondTest instance;
	public static final long WAIT_MS = 100;
	
	public static synchronized MaxOneRequestPerSecondTest getInstance() {
		if (instance == null) 
			instance = new MaxOneRequestPerSecondTest();
		return instance;
	}
	
	private MaxOneRequestPerSecondTest() {
		
	}

	public synchronized long processSearch() {
		try {
			return System.currentTimeMillis();
		} finally {
			try {
				Thread thread = new Thread(this);
				thread.start();
				thread.join();	
			} catch (InterruptedException ie) {
				//should not happen 
			}
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(WAIT_MS);
		} catch (InterruptedException ie) {
			//should not happen :(
		}
		
	}

}
