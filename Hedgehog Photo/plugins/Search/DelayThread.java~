package se.cth.hedgehogphoto.search.model;

//TODO: Use this class in the geocoding-package
public class DelayThread extends Thread {
	private long delay;
	
	public DelayThread(long delay) {
		this.delay = delay;
	}

	@Override
	public void run() {
		try {
			sleep(delay);
		} catch (InterruptedException ie) {
			
		}
	}
}
