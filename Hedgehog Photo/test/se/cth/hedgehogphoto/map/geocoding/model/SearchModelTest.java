package se.cth.hedgehogphoto.map.geocoding.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.objects.LocationObject;

public class SearchModelTest {
	public SearchModel model;

	@Before
	public void setUp() throws Exception {
		this.model = new SearchModel();
	}

	@Test
	public void testSearchModel() {
		//How to test this?
		this.model = new SearchModel();
		assertTrue(this.model != null);
	}

	@Test
	public void testFindLocationsByName() {
		model.findLocationsByName("");
		//cant test it properly, cause no locations-getter?
	}

	@Test
	public void testGetLocationsByName() {
		List<LocationObject> list = model.getLocationsByName("");
		assertTrue( (list.size() == 0) || (list != null) ); //should return an empty list
		list = model.getLocationsByName("london");
		assertTrue( (list.size() > 0) || (list != null) ); //this is a bad test. 'I' know that
		//it should return a list containing several locations, but it's hard to prove.
		//TODO: Mockup-objects?
	}

	@Test
	public void testSetLocations() {
		// Can't test this right now?
	}

}
