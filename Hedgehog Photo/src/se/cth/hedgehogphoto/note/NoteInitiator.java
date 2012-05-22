package se.cth.hedgehogphoto.note;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.note.controller.NoteController;
import se.cth.hedgehogphoto.note.model.NoteModel;
import se.cth.hedgehogphoto.note.view.NoteView;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

/**
 * @author David
 */

@Plugin(name="Note", version="1.0", 
author="David Grankvist", description="N/A")
public class NoteInitiator {
	private NoteView view;
	
	@InitializePlugin
	public void start() {
		NoteModel model = new NoteModel();
		this.view = new NoteView(model);
		new NoteController(model, this.view);
	}

	@Panel(placement=PluginArea.LEFT_MIDDLE)
	public JPanel get(){
		return this.view;	
	}
}
