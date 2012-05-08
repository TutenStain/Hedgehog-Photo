package se.cth.hedgehogphoto.plugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class HelperTest {
	@Test
	public void testStripDotAndSlashFromString(){
		String before = "this/just/works.amazingly";
		String after = "works";
		assertTrue(Helper.stripDotAndSlashFromString(before).equals(after));
	}
	
	@Test
	public void testGetDefaultPluginParsers(){
		List<?> list = Helper.getDefaultPluginParsers();
		int listSize = list.size();
		int nbrOfParsableObjects = 0;
		for(Object p : list){
			if(p instanceof Parsable){
				nbrOfParsableObjects++;
			}
		}
		
		assertTrue(listSize == nbrOfParsableObjects);
	}
	
	@Test
	public void testFindFolderForFile(){
		File before = new File("this/is/the/folder/file.java");
		File after = new File("this/is/the/folder");
		assertTrue(Helper.findFolderForFile(before).toPath().equals(after.toPath()));
	}
}
