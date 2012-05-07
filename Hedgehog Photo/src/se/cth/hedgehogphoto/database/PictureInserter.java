package se.cth.hedgehogphoto.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * 
 * @author Julia
 *
 */
public class PictureInserter {
	private static PictureFetcher pictureFetcher;
	private static Metadata metadata;
	private DatabaseHandler db = DatabaseHandler.getInstance();
	private static List<ImageObject> imageObjects; 
	 
	public PictureInserter(){
		pictureFetcher = new PictureFetcher();
		metadata = new Metadata();
		imageObjects =  pictureFetcher.getImageObjects();
		insertImageObject();
		//DatabaseHandler.searchPictureNames("IMG_0175");
	}
	
	public  void insertImageObject(){	
		for(ImageObject io: imageObjects){
			
			System.out.print(io.getFileName());
			db.insertPicture((FileObject)io);
		}
		
	}
	
}
