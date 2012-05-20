import java.awt.Color;

import javax.swing.JPanel;

/**
 * 
 * @author David Grankvist
 */

@SuppressWarnings("serial")
public class NotePreview extends JPanel{
	
	public NotePreview(){
		this.setBackground(Color.white);
	}
	
	public void paintPreview(int diam, Color c){
        PaintUtils.erasePainting(this);
		PaintUtils.paintCircle(this.getGraphics(), this.getWidth()/2, this.getHeight()/2, diam, c);
	}
}
