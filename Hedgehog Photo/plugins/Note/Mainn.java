
import javax.swing.JPanel;

import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

@Plugin(name="Note", version="1.0", 
author="David Grankvist", description="N/A")
public class Mainn {
	private NoteView view;
	
	@InitializePlugin
	public void start() {
		NoteModel model = new NoteModel();
		view = new NoteView(model);
		new NoteController(model, view);
	}

	@Panel(placement=PluginArea.LEFT_MIDDLE)
	public JPanel get(){
		return view;	
	}
}
