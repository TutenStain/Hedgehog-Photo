package se.cth.hedgehogphoto;

import java.io.File;

import javax.swing.JFrame;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.gui.View;

/**
 * @author Barnabas Sapan
 */

public class Main {

	//TODO Just a skeleton of the main
	public static void main(String[] args) {
//		JFrame frame = new JFrame("TagCloud");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(600, 600);
//		frame.setVisible(true);
		DatabaseHandler.deleteAll();
		insertFileObjectsIntoDatabase();
		MainModel model = new MainModel();
		View view = new View();
		model.addObserver(view);
		model.testNotify();
		
		//TagCloud
		/*List<String> l = new ArrayList<String>();
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

		//TagCloud
		TagCloudModel tgm = new TagCloudModel();
		tgm.setTags(l);
		TagCloudView tgv = new TagCloudView(tgm);
		tgv.update();
		frame.add(tgv);*/
		
//		SearchModel sm = new SearchModel();
//		SearchView sv = new SearchView();
//		sv.setPreferredSize(new Dimension(250, 30));
//		sm.addObserver(sv);
//		SearchPreviewView spv = new SearchPreviewView();
//		sv.setSearchPreview(spv);
//		sm.addObserver(spv);
//		SearchController sc = new SearchController(sm, sv);
//		frame.add(sv);	
//		
//		//Manually refresh the view as it for some reason wont do it automatically all the time.
//		frame.revalidate();
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
