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
	
	public void setCircleDiam(int diameter){
		this.circleDiam = diameter;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setColor(Color color){
		this.color = color;
		this.setChanged();
		this.notifyObservers();
	}
}
