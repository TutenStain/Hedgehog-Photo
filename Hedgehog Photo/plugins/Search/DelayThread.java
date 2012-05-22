/**
 * Just a simple class that is used for delaying events
 *  in conjunction with the function join().
 */
public class DelayThread extends Thread {
	private long delay;
	
	public DelayThread(long delay) {
		this.delay = delay;
	}

	@Override
	public void run() {
		try {
			sleep(this.delay);
		} catch (InterruptedException ie) {

		}
	}
}
