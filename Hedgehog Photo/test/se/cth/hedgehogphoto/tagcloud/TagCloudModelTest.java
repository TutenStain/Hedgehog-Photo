package se.cth.hedgehogphoto.tagcloud;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TagCloudModelTest {
	private TagCloudModel model;
	
	@Before
	public void setUp(){
		this.model = new TagCloudModel(null);
	}
	
	@Test
	public void testSetTag(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("c");
		list.add("c");
		list.add("d");
		list.add("d");
		this.model.setTags(list);
		Map<String, Integer> map = this.model.getTagsOccurrence();
		
		boolean success = true;
		
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			if(map.get(entry.getKey()) != Collections.frequency(list, entry.getKey())){
				success = false;
			}
		}
		
		assertTrue(success);
	}
}
