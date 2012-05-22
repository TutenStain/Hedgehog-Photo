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
	private PictureFetcher pictureFetcher = new PictureFetcher();
	private  List<ImageObject> imageObjects  =  this.pictureFetcher.getImageObjects(); 

	public PictureInserter(){
		insertImageObject();
	}

	public synchronized void insertImageObject(){	
		for(ImageObject io : this.imageObjects){
			io.setDate(Converter.convertDate(io.getDate()));
			io.setComment(Converter.convertComment(io.getComment()));
			io.setTags(Converter.convertTags(io.getTag()));
			io.setLocationObject(Converter.findLocationPlace(io.getLocationObject()));
			DatabaseHandler.getInstance().insertPicture(io);
		}
	}

}
