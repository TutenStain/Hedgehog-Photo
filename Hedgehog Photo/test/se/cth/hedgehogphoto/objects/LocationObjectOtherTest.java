package se.cth.hedgehogphoto.objects;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class LocationObjectOtherTest {
	private LocationObjectOther locationObject;
	
	@Before
	public void setUp() throws Exception {
		this.locationObject = new LocationObjectOther("Sweden");
	}

	@Test
	public void testLocationObjectOtherString() {
		this.locationObject = new LocationObjectOther("Tokyo");
		assertTrue(this.locationObject.getLocation().equals("Tokyo"));
	}

	@Test
	public void testLocationObjectOtherDoubleDouble() {
		this.locationObject = new LocationObjectOther(111, 122);
		assertTrue(this.locationObject.getLongitude() == 111 && this.locationObject.getLatitude() == 122);
	}

	@Test
	public void testSetLocationString() {
		this.locationObject.setLocation("Japan");
		assertTrue(this.locationObject.getLocation().equals("Japan"));
	}

	@Test
	public void testSetLocationDoubleDouble() {
		this.locationObject.setLocation(111, 122);
		assertTrue(this.locationObject.getLongitude() == 111 && this.locationObject.getLatitude() == 122);
	}

	@Test
	public void testSetLatitudeDouble() {
		this.locationObject.setLatitude(52);
		assertTrue(this.locationObject.getLatitude() == 52);
	}

	@Test
	public void testSetLatitudeString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLongitudeDouble() {
		this.locationObject.setLongitude(11);
		assertTrue(this.locationObject.getLongitude() == 11);
	}

	@Test
	public void testSetLongitudeString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLatitude() {
		this.locationObject = new LocationObjectOther(110, 190);
		assertTrue(this.locationObject.getLatitude() == 190);
	}

	@Test
	public void testGetLongitude() {
		this.locationObject = new LocationObjectOther(110, 190);
		assertTrue(this.locationObject.getLongitude() == 110);
	}

	@Test
	public void testGetLocation() {
		this.locationObject = new LocationObjectOther("Sweden");
		assertTrue(locationObject.getLocation().equals("Sweden"));
	}

}
