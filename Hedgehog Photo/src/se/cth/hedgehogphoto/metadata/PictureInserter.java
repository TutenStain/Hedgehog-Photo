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
	private PictureFetcher pictureFetcher = new PictureFetcher();;
	private  List<ImageObject> imageObjects  =  this.pictureFetcher.getImageObjects(); 

	public PictureInserter(){
		insertImageObject();
	}

	public synchronized void insertImageObject(){	
		for(ImageObject io : this.imageObjects){
			io.setComment(Converter.getInstance().convertComment(io.getComment()));
			io.setTags(Converter.getInstance().convertTags(io.getTag()));
			io.setLocationObject(Converter.getInstance().findLocationPlace(io.getLocationObject()));
			DatabaseHandler.getInstance().insertPicture(io);
		}
	}

}
