package se.cth.hedgehogphoto.note;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

@Plugin(name="Note", version="1.0", 
author="David Grankvist", description="N/A")
public class Main {
	private NoteView view;
	@InitializePlugin
	public void start() {
		//TODO
	}

	@Panel(placement=PluginArea.LEFT_MIDDLE)
	public JPanel get(){
		return view;	
	}
}
