package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;
import se.cth.hedgehogphoto.FileObject;
import java.util.Observable;


public class Files extends Observable{
	private static Files file;
	private List<FileObject> list = new ArrayList<FileObject>(); 

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

	public List<FileObject> getList(){
		return list;
	}

	public void setList(List<FileObject> list){
		this.list = list;
		setChanged();
		notifyObservers(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}