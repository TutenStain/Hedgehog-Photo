package se.cth.hedgehogphoto.note;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.tagcloud.TagCloudModel;
import se.cth.hedgehogphoto.view.PluginArea;

@Plugin(name="Note", version="1.0", 
author="David Grankvist", description="N/A")
public class Main {
	private NoteView view;
	
	@InitializePlugin
	public void start() {
		view = new NoteView();
	}

	@Panel(placement=PluginArea.LEFT_MIDDLE)
	public JPanel get(){
		return view;	
	}
}
