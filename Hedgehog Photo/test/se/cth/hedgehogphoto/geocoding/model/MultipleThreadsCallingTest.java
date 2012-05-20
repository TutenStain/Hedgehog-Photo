package se.cth.hedgehogphoto.geocoding.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MultipleThreadsCallingTest {
	private List<Thread> threads;
	private FakeXMLParserMock instance;
	private List<Long> timesInMs = new ArrayList<Long>();
	private final int nbrOfThreads = 10;
	
	
	
	@Before
	public void setUp() throws Exception {
		instance = FakeXMLParserMock.getInstance();
		createThreads();
	}
	
	/**
	 * Simulates calls from multiple threads from outside this
	 * class to one specific method in here.
	 */
	public void createThreads() {
		this.threads = new ArrayList<Thread>();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				long time_ms = instance.processSearch();
				time_ms = System.currentTimeMillis() - time_ms;
				timesInMs.add(time_ms);
			}
		};
		for (int index = 0; index < nbrOfThreads; index++) { //create some Threads
			threads.add(new Thread(runnable));
		}
	}
	
	public void startAllThreads() {
		for (Thread t: threads) { //start all Threads
			t.start();
		}
	}

	@Test
	public void test() {
		startAllThreads();
		for (Long ms : timesInMs) {
			assertTrue(ms > FakeXMLParserMock.WAIT_MS); //threads wait at least as much as they have to
		}
	}

}
