package se.cth.hedgehogphoto.geocoding.model;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
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
	public void testQueryGeocodingURL() {
		String path = "http://nominatim.openstreetmap.org/search?format=xml&addressdetails=0&email=hedgehogphoto.chalmers@gmail.com&q=";
		
		String query = null; //handle null-query
		assertTrue(path.equals(instance.queryGeocodingURL(query).toString()));
		
		query = ""; //handle empty query
		assertTrue(path.equals(instance.queryGeocodingURL(query).toString()));
		
		query = "london"; //handle query
		String queryPath = path + query;
		assertTrue(queryPath.equals(instance.queryGeocodingURL(query).toString()));
		
		query = "new york"; //handle space in search
		queryPath = path + query;
		assertTrue(!queryPath.equals(instance.queryGeocodingURL(query).toString()));
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be 'new+york' now, ie URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryGeocodingURL(query).toString());
			assertTrue(queryPath.equals(instance.queryGeocodingURL("new york").toString())); //has to use old query-string
		}
		
		
		query = "göteborg"; //handle scandinavian letter ö
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryGeocodingURL("göteborg").toString());
			assertTrue(queryPath.equals(instance.queryGeocodingURL("göteborg").toString())); //has to use old query-string
		}
	}

}
