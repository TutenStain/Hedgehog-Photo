package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.util.Observable;

/**
 * 
 * @author David Grankvist
 */

public class NoteModel extends Observable{
	
	private int circleDiam;
	private Color color;
	
	public NoteModel(){
		this.circleDiam = 10;
		this.color = Color.black;
	}
	
	public int getCircleDiam(){
		return this.circleDiam;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setCircleDiam(int diam){
		this.circleDiam = diam;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setColor(Color c){
		this.color = c;
		this.setChanged();
		this.notifyObservers();
	}

}
