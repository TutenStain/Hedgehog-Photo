package se.cth.hedgehogphoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.gui.ModelView;
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
		ModelView view = new ModelView();
		model.addObserver(view);
		
		//TagCloud
		List<String> l = new ArrayList<String>();
		l = DatabaseHandler.getTags();
		TagCloudModel tgm = new TagCloudModel();
		TagCloudView tgv = new TagCloudView();
		tgm.addObserver(tgv);
		tgm.setTags(l);
		view.addToLeftPanel(tgv);

		model.testNotify();
	}
	
	private static void insertFileObjectsIntoDatabase() {
		//TODO This is in the DB:
		
		File directory = new File("Pictures");
		File[] files = directory.listFiles();
		for(File file : files) {
			FileObject f = new ImageObject();
			f.setComment("Gutes bild");
			f.setImageName("wei" + file.getName());
			f.setDate("2012.12.02");
			f.setTag("Bra");
			f.setTag("Battre");
			f.setImagePath(file.getAbsolutePath());
			f.setCoverPath("blo");
			f.setLocation("Japan");
			f.setAlbumName("Bra bilder");
			DatabaseHandler.insert(f);
		}
	}

}
