package se.cth.hedgehogphoto.geocoding.model;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class XMLParserTest {
	public XMLParser instance;

	@Before
	public void setUp() throws Exception {
		instance = XMLParser.getInstance();
	}

	@Test
	public void testGetInstance() {
		instance = XMLParser.getInstance();
		assertTrue(instance != null);
		instance = null;
		instance = XMLParser.getInstance();
		assertTrue(instance != null);
	}

	/**
	 * When this class is implemented correctly, one should
	 * avoid to run it too many times. Repeated request for the
	 * same page on the nominatim-server are not encouraged,
	 * and could ultimately result in a ban.
	 */
	@Test
	public void testProcessSearch() {
		String basePath = "http://nominatim.openstreetmap.org/search?format=xml&addressdetails=1&q=";
		assertTrue(possibleToCreateURL(basePath)); //basic search, no query
		assertTrue(!possibleToCreateURL(null)); //null-input
		
		String appendPath = "london";
		assertTrue(possibleToCreateURL(basePath + appendPath));
		
		appendPath = "new+york";
		assertTrue(possibleToCreateURL(basePath + appendPath));
		
		//--------------------------------------------------------
		
		
//		fail("Not yet implemented");
	}
	
	/**
	 * This method is not suited for the XMLParser.
	 * The XMLParser shuold test if the XML-parsing
	 * works, nothing else.
	 * @param path
	 * @return
	 */
	private boolean possibleToCreateURL(String path) {
		try {
			URL url = new URL(path);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

}
