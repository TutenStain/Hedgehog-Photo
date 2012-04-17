package se.cth.hedgehogphoto.metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import se.cth.hedgehogphoto.database.DatabaseHandler;

/**
 * 
 * @author Julia
 *
 */
public class PictureInserter {
	private static PictureFetcher pictureFetcher;
	private static Metadata metadata;
	private static List<ImageObject> imageObjects; 
	 
	public PictureInserter(){
		pictureFetcher = new PictureFetcher();
		metadata = new Metadata();
		imageObjects =  pictureFetcher.getImageObjects();
		insertImageObject();
		//DatabaseHandler.searchPictureNames("IMG_0175");
	}
	
	public static void insertImageObject(){	
		for(ImageObject io: imageObjects){
			io.setComment("BAJS");
			System.out.print(io.getFileName());
		//	DatabaseHandler.insertPicture(io);
		}
		
	}
	
}
