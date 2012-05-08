package se.cth.hedgehogphoto.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.tagcloud.TagCloudModel;
import se.cth.hedgehogphoto.tagcloud.TagCloudView;
import se.cth.hedgehogphoto.view.PluginArea;

/**
 * @author Barnabas Sapan
 */

@Plugin(name="TagCloud", version="1.0", 
author="Barnabas Sapan", description="N/A")
public class Main {
	private TagCloudView tgv;
	private DatabaseAccess db;

	@InitializePlugin
	public void start() {
		List<String> l = new ArrayList<String>();
		l = db.getTags();
		TagCloudModel tgm = new TagCloudModel(db);
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
	
	@GetDatabase
	public void setDB(DatabaseAccess db){
		this.db = db;
	}
}