package se.cth.hedgehogphoto.calendar;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.calendar.view.CalendarView;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.GetVisibleFiles;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

@Plugin(name="Calendar", version="1.0", 
author="Julia Gustafsson", description="N/A")
public class CalendarMain {
	private DatabaseAccess db;
	private Files f;
	private CalendarView cw;

	@InitializePlugin
	public void start(){
		this.cw = CalendarView.getInstance(this.db, this.f); 
	}

	@Panel(placement=PluginArea.LEFT_BOTTOM)
	public JPanel get(){
		return this.cw;	
	}

	@GetDatabase
	public void setDB(DatabaseAccess db){
		this.db = db;
	}
	
	@GetVisibleFiles
	public void setFiles(Files f){
		this.f = f;
	}
}