package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.log.Log;



public final class Files extends Observable{
	private static Files file;
	private List<PictureObject> pictureList = new ArrayList<PictureObject>();
	private List<AlbumObject> albumList = new ArrayList<AlbumObject>();
	
	private Files(){
	} 
	
	public static Files getInstance() {
		if(file == null)
			file = new Files();
		return file;
	}
	
	public void setPictureList(List<PictureObject> list){
		pictureList = list;
		setChanged();
		notifyObservers(this);
		Log.getLogger().info("Files now contain " + pictureList.size() + " pictures.");
	}
	
	public List<PictureObject> getPictureList(){
		return pictureList;
	}
	
	public void setAlbumList(List<AlbumObject> list){
		albumList = list;
		setChanged();
		notifyObservers(this);
	}
	
	public List<AlbumObject> getAlbumList(){
		return albumList;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
