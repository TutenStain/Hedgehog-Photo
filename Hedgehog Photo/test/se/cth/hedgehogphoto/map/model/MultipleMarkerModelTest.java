package se.cth.hedgehogphoto.map.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;

public class MultipleMarkerModelTest {
	private MultipleMarkerModel model;
	private MarkerModel submodelOne;
	private MarkerModel submodelTwo;
	private Picture pictureOne;
	private Picture pictureTwo;
	private Location locationOne;
	private Location locationTwo;
	private int xPos = 50;
	private int yPos = 70;
	private List<Picture> pictures;

	@Before
	public void setUp() throws Exception {
		cheatHelp();
		this.pictureOne = new Picture();
		this.pictureOne.setName("Bild");
		this.pictureOne.setPath(Global.ZOOM); //just a string
		this.locationOne = new Location();
		this.locationOne.setLocation("Sverige");
		this.locationOne.setLongitude(13);
		this.locationOne.setLatitude(37);
		this.pictureOne.setLocation(this.locationOne);
		this.submodelOne = new MarkerModel(this.pictureOne);
		this.submodelOne.setPosition(xPos, yPos);
		
		this.pictureTwo = new Picture();
		this.pictureTwo.setName("Bild");
		this.pictureTwo.setPath(Global.ZOOM); //just a string
		this.locationTwo = new Location();
		this.locationTwo.setLocation("Sverige");
		this.locationTwo.setLongitude(13);
		this.locationTwo.setLatitude(37);
		this.pictureTwo.setLocation(this.locationTwo);
		this.submodelTwo = new MarkerModel(this.pictureTwo);
		this.submodelTwo.setPosition(xPos+10, yPos+10);
	}
	
	@Test
	public void cheatHelp() {
		ArrayList<Location> locations = new ArrayList<Location>();
    	Location temp = new Location();
    	temp.setLongitude(20.0);
    	temp.setLatitude(30.0);
    	locations.add(temp);
    	Location temp0 = new Location();
    	temp0.setLongitude(20.0);
    	temp0.setLatitude(30.0);
    	locations.add(temp0);
    	Location temp1 = new Location();
    	temp1.setLongitude(15.0);
    	temp1.setLatitude(40.0);
    	locations.add(temp1);
    	Location temp2 = new Location();
    	temp2.setLongitude(20.0);
    	temp2.setLatitude(30.1);
    	locations.add(temp2);
    	Location temp3 = new Location();
    	temp3.setLongitude(22.0);
    	temp3.setLatitude(33.1);
    	locations.add(temp3);
    	Location temp4 = new Location();
    	temp4.setLongitude(22.1);
    	temp4.setLatitude(33.0);
    	locations.add(temp4);
    	Location temp5 = new Location();
    	temp5.setLongitude(22.1);
    	temp5.setLatitude(33.2);
    	locations.add(temp5);
		pictures = new ArrayList<Picture>();
		int nbrOfLocations = locations.size();
		for (int index = 0; index < nbrOfLocations; index++) {
			Picture pic = new Picture();
			pic.setLocation(locations.get(index));
			pictures.add(pic);
		}
		//TODO Jag kommenterade bort det, fixa.
		//Files.getInstance().setPictureList(pictures);
		assertTrue(pictures.equals(Files.getInstance().getPictureList()));
	}

	@Test
	public void testGetXOffset() {
		assertTrue(true);
	}

	@Test
	public void testGetYOffset() {
		fail("Not yet implemented");
	}

	@Test
	public void testPropertyChange() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPictures() {
		fail("Not yet implemented");
	}

	@Test
	public void testComputeNumberOfPictures() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTotalVisible() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandleZoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultipleMarkerModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMarkerModels() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfPictures() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNumberOfPictures() {
		fail("Not yet implemented");
	}

}
