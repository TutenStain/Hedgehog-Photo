package se.cth.hedgehogphoto.metadata;

import java.util.List;

import se.cth.hedgehogphoto.objects.ImageObject;

/**
 * 
 * @author Julia
 *
 */
public class PictureInserter {
	private /*static*/ PictureFetcher pictureFetcher = new PictureFetcher();;
	//private /*static*/ Metadata metadata;
	private  List<ImageObject> imageObjects  =  pictureFetcher.getImageObjects(); 
	 
	public PictureInserter(){

	//	metadata = new Metadata();
	
		insertImageObject();
		//DatabaseHandler.searchPictureNames("IMG_0175");
	}
	
	public synchronized  void insertImageObject(){	
		for(ImageObject io: imageObjects){
			io.setComment("BAJS");
			System.out.print(io.getFileName());
		//	DatabaseHandler.insertPicture(io);
		}
		
	}
	
}
