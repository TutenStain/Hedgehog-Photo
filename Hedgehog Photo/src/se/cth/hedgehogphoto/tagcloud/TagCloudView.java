package se.cth.hedgehogphoto.tagcloud;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.view.WrapLayout;

/**
 * The view representation of the tagcloud.
 * setPreferredSize should always be set!
 * @author Barnabas Sapan
 */
@SuppressWarnings("serial")
public class TagCloudView extends JPanel implements Observer {
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Font baseFont = new Font("Serif", Font.PLAIN, 11);
	private float fontMax = 20f;
	private float fontMin = baseFont.getSize();
	//getFontSize() helpers
	private int removeFromMax = 0;
	private int addToMin = 0;
	
	private MouseListener tagComponentMouseListener;
	
	public TagCloudView(){
		setLayout(new WrapLayout(FlowLayout.LEFT));	
		setPreferredSize(new Dimension(150, 75));
	}
	
	/**
	 * Returns a font size based on the tag occurrence.
	 * @param tagOccurrence the number of times the tag occurs.
	 * @return the font size based on the tag occurrence.
	 */
	//TODO Maybe a better implementation fontSize based on max/min size.
	private float getFontSize(int tagOccurrence){
		float size = this.map.size() * tagOccurrence;
		
		if(size > this.fontMax){
			size = this.fontMax - this.removeFromMax;
			this.removeFromMax += 2;
		}
		
		if(size < this.fontMin){
			size = this.fontMin + this.addToMin;
			this.addToMin += 2;
		}
		
		return size;
	}
	
	public void setMouseListener(MouseListener listener) {
		this.tagComponentMouseListener = listener;
	}
	
	/**
	 * Resets the fontsizes to the defaults so that the next update
	 * will get correct sizes.
	 */
	private void reset(){
		this.baseFont = new Font("Serif", Font.PLAIN, 11);
		this.fontMax = 20f;
		this.fontMin = this.baseFont.getSize();
		this.addToMin = 0;
		this.removeFromMax = 0;
	}
		
	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && arg instanceof TagCloudModel){
			TagCloudModel model = (TagCloudModel)arg;
			this.map = model.getTagsOccurrence();
			
			removeAll();
			
			for(Map.Entry<String, Integer> entry : this.map.entrySet()){
				TagComponent tag = new TagComponent(entry.getKey());
				tag.addMouseListener(this.tagComponentMouseListener);
				tag.setFont(this.baseFont.deriveFont(getFontSize(entry.getValue())));
				add(tag);
			}
			
			revalidate();
			
			//Reset everything for the next update.
			this.reset();
		}
	}
}