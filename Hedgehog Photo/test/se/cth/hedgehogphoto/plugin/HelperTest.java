package se.cth.hedgehogphoto.plugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

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
		File after = Helper.findFolderForFile(before);
		File resultIs = new File("this/is/the/folder");
		assertTrue(resultIs.getAbsolutePath().equals(after.getAbsolutePath()));
	}
	
	/*Questionably if this actually tests something or not*/
	@Test
	public void testCreatePluginFolder(){
		File file = Mockito.mock(File.class);
		Mockito.when(file.getPath()).thenReturn("/home/temp");
		Mockito.when(file.exists()).thenReturn(true);
		assertTrue(Helper.createPluginFolder(file));
	}
}
