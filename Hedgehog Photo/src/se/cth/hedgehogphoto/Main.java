package se.cth.hedgehogphoto;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.gui.MainView;
import se.cth.hedgehogphoto.search.SearchController;
import se.cth.hedgehogphoto.search.SearchModel;
import se.cth.hedgehogphoto.search.SearchPreviewView;
import se.cth.hedgehogphoto.search.SearchView;
import se.cth.hedgehogphoto.tagcloud.TagCloudModel;
import se.cth.hedgehogphoto.tagcloud.TagCloudView;

/**
 * @author Barnabas Sapan
 */

public class Main {

	//TODO Just a skeleton of the main
	public static void main(String[] args) {
		DatabaseHandler.deleteAll();
		insertFileObjectsIntoDatabase();
		MainModel model = new MainModel();
		MainView view = new MainView();
		model.addObserver(view);
		
		//TagCloud
		List<String> l = new ArrayList<String>();
		l = DatabaseHandler.getTags();
		TagCloudModel tgm = new TagCloudModel();
		TagCloudView tgv = new TagCloudView();
		tgm.addObserver(tgv);
		tgm.setTags(l);
		view.addToLeftPanel(tgv);
		
		//Search
		SearchModel sm = new SearchModel();
		SearchView sv = new SearchView();
		sv.setPreferredSize(new Dimension(250, 30));
		sm.addObserver(sv);
		SearchPreviewView spv = new SearchPreviewView();
		sv.setSearchPreview(spv);
		sm.addObserver(spv);
		new SearchController(sm, sv);
		view.addToTopPanel(sv);

		model.testNotify();
	}
	
	private static void insertFileObjectsIntoDatabase() {
		//TODO This is in the DB:
		
		File directory = new File("Pictures");
		File[] files = directory.listFiles();
		int i = 0;
		for(File file : files) {
			FileObject f = new ImageObject();
			f.setComment("Gutes bild");
			f.setImageName("wei" + file.getName());
			f.setDate("2012.12.02");
			//Just som random tags to test the TagCloud
			f.setTag("Fint");
			if(i == 0){
				f.setTag("Snyggt");
			}
			if(i == 3 || i == 4){
				f.setTag("Trevligt");
			}
			if(i % 2 == 0){
				f.setTag("Festligt");
			}
			f.setImagePath(file.getAbsolutePath());
			f.setCoverPath("blo");
			f.setLocation("Japan");
			f.setAlbumName("Bra bilder");
			DatabaseHandler.insert(f);
			i++;
		}
	}

}
