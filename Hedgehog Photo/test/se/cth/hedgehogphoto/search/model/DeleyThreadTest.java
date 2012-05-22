package se.cth.hedgehogphoto.search.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DeleyThreadTest {
	private DelayThread delayThread;
	
	@Before
	public void start(){
		this.delayThread = new DelayThread(500);
	}
	
	@Test
	public void run(){
		long timeBefore = System.currentTimeMillis();
		this.delayThread.start();
		try {
			this.delayThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		long timeAfter = System.currentTimeMillis();
		
		long delta = timeAfter - timeBefore;
		/*Allow some error margin*/
		if(delta >= 500 && delta <= 510){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
