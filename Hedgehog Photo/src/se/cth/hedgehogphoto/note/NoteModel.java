package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.util.Observable;

public class NoteModel extends Observable{
	
	private int circleDiam;
	private Color color;
	
	public NoteModel(int circleDiam, Color color){
		this.circleDiam = circleDiam;
		this.color = color;
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
