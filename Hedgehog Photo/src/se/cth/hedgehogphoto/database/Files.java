package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.FileObject;



public class Files extends Observable{
	private static Files file;
	private List<Picture> pictureList = new ArrayList<Picture>();
	private List<Album> albumList = new ArrayList<Album>();
	
	private Files(){
	} 
	
	public static Files getInstance() {
		if(file == null)
			file = new Files();
		return file;
	}
	
	public void setPictureList(List<Picture> list){
		pictureList = list;
		setChanged();
		notifyObservers(this);
		Log.getLogger().info("Files now contain " + pictureList.size() + " pictures.");
	}
	
	public List<Picture> getPictureList(){
		return pictureList;
	}
	
	public void setAlbumList(List <Album> list){
		albumList = list;
		setChanged();
		notifyObservers(this);
	}
	
	public List<Album> getAlbumList(){
		return albumList;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
