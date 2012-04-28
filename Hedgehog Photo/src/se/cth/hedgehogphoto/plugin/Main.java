package se.cth.hedgehogphoto.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.text.TableView;

import se.cth.hedgehogphoto.tagcloud.TagCloudModel;
import se.cth.hedgehogphoto.tagcloud.TagCloudView;

/**
 * @author Barnabas Sapan
 */

@Plugin(name="TagCloud", version="1.0", 
author="Barnabas Sapan", description="N/A")
public class Main {
	private TagCloudView tgv;

	@InitializePlugin
	public void start() {
		List<String> l = new ArrayList<String>();
		//l = DatabaseHandler.getTags();
		TagCloudModel tgm = new TagCloudModel();
		tgv = new TagCloudView();
	
		tgm.addObserver(tgv);
		
		l.add("hej");
		
		l.add("whiii");
		l.add("sfsdf");
		l.add("hej");
		l.add("whiii");
		l.add("whiii");
		l.add("Yay");
		l.add("Sweden");
		l.add("Sweden");
		l.add("Sweden");
		l.add("Sweden");
		
		tgm.setTags(l);
	}

	@Panel(placement=PluginArea.LEFT_BOTTOM)
	public JPanel get(){
		return tgv;	
	}
}