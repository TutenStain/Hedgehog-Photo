package se.cth.hedgehogphoto.search.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.database.PictureObject;

public class SearchModelTest {
	private SearchModel model;
	
	@Before
	public void start(){
		this.model = new SearchModel();
	}
	
	@Test
	public void testSetPictures(){
		this.model.setPictures(null);
		assertTrue(this.model.getPictures().isEmpty());
		
		List<PictureObject> list = new ArrayList<PictureObject>();
		list.add(new Picture());
		
		this.model.setPictures(list);
		assertTrue(this.model.getPictures().equals(list));
	}
}
