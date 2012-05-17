package se.cth.hedgehogphoto.map.geocoding.model;


/**
 * This class represents the XMLParser. The reason
 * I don't test it directly, is that I don't want to 
 * make multiple requests to the nominatim-server,
 * IF the tests would fail (since that would contradict 
 * to the usage policy). This class is built in
 * the same way, but doesn't process any XML documents.
 * @author Florian Minges
 */
public class FakeXMLParserMock implements Runnable {
	private static FakeXMLParserMock instance;
	public static final long WAIT_MS = 100;
	
	public static synchronized FakeXMLParserMock getInstance() {
		if (instance == null) 
			instance = new FakeXMLParserMock();
		return instance;
	}
	
	private FakeXMLParserMock() {
		
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
