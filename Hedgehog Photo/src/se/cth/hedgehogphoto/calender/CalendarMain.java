package se.cth.hedgehogphoto.calender;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.plugin.PluginArea;

@Plugin(name="Calendar", version="1.0", 
author="Julia Gustafsson", description="N/A")

public class CalendarMain {
				private DatabaseAccess db;
				private CalendarView cw;

				@InitializePlugin
				public void start(){
					cw = CalendarView.getInstance(db); 
				}

				@Panel(placement=PluginArea.LEFT_BOTTOM)
				public JPanel get(){
					return cw;	
				}
				
				@GetDatabase
				public void setDB(DatabaseAccess db){
					this.db = db;
				}
			}