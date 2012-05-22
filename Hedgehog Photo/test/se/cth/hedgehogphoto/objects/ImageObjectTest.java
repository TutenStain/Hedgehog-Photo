package se.cth.hedgehogphoto.objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ImageObjectTest {
	private ImageObject imageObject;
	
	@Before
	public void setUp() throws Exception {
		this.imageObject = new ImageObject();
	}

	@Test
	public void testImageObject() {
		assertTrue(this.imageObject instanceof FileObject);
	}

	@Test
	public void testSetAlbumName() {
		this.imageObject.setAlbumName("Album");
		assertTrue(this.imageObject.getAlbumName().equals("Album"));
	}

	@Test
	public void testSetTag() {
		this.imageObject.setProperty("XPKeywords", "Tagg");
		assertTrue(this.imageObject.getTag().equals("Tagg"));
	}

	@Test
	public void testSetTags() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		this.imageObject.setTags(list);
		assertTrue(this.imageObject.getTags().equals(list));
	}


	@Test
	public void testSetDate() {
		this.imageObject.setProperty("Modify Date", "2012-01-01");
		assertTrue(this.imageObject.getDate().equals("2012-01-01"));
	}


	@Test
	public void testSetComment() {
		this.imageObject.setProperty("XPComment", "Comment");
		assertTrue(this.imageObject.getComment().equals("Comment"));
	}

	@Test
	public void testSetFilePath() {
		this.imageObject.setProperty("File Path", "usr/test.java");
		assertTrue(this.imageObject.getFilePath().equals("usr/test.java"));
	}

	@Test
	public void testSetFileName() {
		this.imageObject.setProperty("File Name", "Name");
		assertTrue(this.imageObject.getFileName().equals("Name"));
	}

	@Test
	public void testGetLocationObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLocationObject() {
		fail("Not yet implemented");
	}


	@Test
	public void testConvertComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertTags() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertDecimalNumbersToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCoverPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCoverPath() {
		fail("Not yet implemented");
	}

}
