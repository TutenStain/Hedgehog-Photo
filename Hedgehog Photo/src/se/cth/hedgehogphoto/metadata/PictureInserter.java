package se.cth.hedgehogphoto.metadata;

import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseHandler;
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
			System.out.print(io.getLocationObject());
			io.setComment(Converter.getInstance().convertComment(io.getComment()));
			io.setTags(Converter.getInstance().convertTags(io.getTag()));
			io.setLocationObject(Converter.getInstance().findLocationPlace(io.getLocationObject()));
		DatabaseHandler.getInstance().insertPicture(io);
		}
	}
	
}
