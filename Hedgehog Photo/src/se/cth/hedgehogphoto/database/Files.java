package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.objects.FileObject;



public class Files extends Observable{
	private static Files file;
	@Deprecated
	private List<FileObject> list = new ArrayList<FileObject>(); 
	
	private List<Picture> pictureList = new ArrayList<Picture>();
	private List<Album> albumList = new ArrayList<Album>();
	
	private Files(){
	} 
	
	public static Files getInstance(){
		if(file == null){
			file = new Files();
			return file;
		} else {
			return file;
		}
	}
	
	@Deprecated
	public List<FileObject> getList(){
		return list;
	}
	
	
	@Deprecated
	public void setList(List<FileObject> list){
		this.list = list;
		setChanged();
		notifyObservers(this);
	}
	
	public void setPictureList(List<Picture> list){
		pictureList = list;
		setChanged();
		notifyObservers(this);
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
