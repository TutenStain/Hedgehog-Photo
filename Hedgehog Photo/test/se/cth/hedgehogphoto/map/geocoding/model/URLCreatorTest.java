package se.cth.hedgehogphoto.map.geocoding.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.Before;
import org.junit.Test;

public class URLCreatorTest {
	public URLCreator instance;

	@Before
	public void setUp() throws Exception {
		instance = URLCreator.getInstance();
	}

	@Test
	public void testGetInstance() {
		instance = URLCreator.getInstance();
		assertTrue(instance != null);
		instance = null;
		instance = URLCreator.getInstance();
		assertTrue(instance != null);
	}

	@Test
	public void testInvokeLater() {
		//do i need to test this?
	}

	@Test
	public void testQueryURL() {
		String path = "http://nominatim.openstreetmap.org/search?format=xml&addressdetails=1&q=";
		
		String query = null; //handle null-query
		assertTrue(path.equals(instance.queryURL(query).toString()));
		
		query = ""; //handle empty query
		assertTrue(path.equals(instance.queryURL(query).toString()));
		
		query = "london"; //handle query
		String queryPath = path + query;
		assertTrue(queryPath.equals(instance.queryURL(query).toString()));
		
		query = "new york"; //handle space in search
		queryPath = path + query;
		assertTrue(!queryPath.equals(instance.queryURL(query).toString()));
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be 'new+york' now, ie URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryURL(query).toString());
			assertTrue(queryPath.equals(instance.queryURL("new york").toString())); //has to use old query-string
		}
		
		
		query = "göteborg"; //handle scandinavian letter ö
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryURL("göteborg").toString());
			assertTrue(queryPath.equals(instance.queryURL("göteborg").toString())); //has to use old query-string
		}
	}
	
	private URL createURL(String path) {
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			url = null;
			fail("Could not create URL");
		} finally {
			return url;
		}
	}

}
