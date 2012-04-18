package se.cth.hedgehogphoto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.gui.MainView;
import se.cth.hedgehogphoto.ImageObject;
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
		//Main
		insertFileObjectsIntoDatabase();
		System.out.print(DatabaseHandler.getAllPictures());
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
		SearchPreviewView spv = new SearchPreviewView();
		SearchView sv = new SearchView(spv);
		SearchModel sm = new SearchModel(sv, spv);
		sv.setPreferredSize(new Dimension(250, 30));
		new SearchController(sm, sv);

		Files.getInstance().addObserver(view);

		view.addToTopPanel(sv, BorderLayout.EAST);

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
			f.setFileName("wei" + file.getName());
			f.setDate("2012.12.02");
			//Just some random tags to test the TagCloud
			List<String> l = new ArrayList<String>();
			l.add("Snyggt");
			if(i == 0){
				l.add("Snyggt");
			}
			if(i == 3 || i == 4){
				l.add("Trevligt");
			}
			if(i % 2 == 0){
				l.add("Festligt");
			}
			f.setTags(l);
			
			try {
				f.setFilePath(file.getCanonicalPath());
				System.out.println(file.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f.setCoverPath("blo");
			f.setLocation(new LocationObject("Japan"));
			f.setAlbumName("Bra bilder");
			DatabaseHandler.insertPicture(f);
			i++;
			
		}
	}

}
