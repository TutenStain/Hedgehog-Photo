package se.cth.hedgehogphoto.tagcloud;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.view.WrapLayout;

/**
 * The view representation of the tagcloud.
 * !setPreferredSize should always be set!
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
		float size = map.size() * tagOccurrence;
		
		if(size > fontMax){
			size = fontMax - removeFromMax;
			removeFromMax += 2;
		}
		
		if(size < fontMin){
			size = fontMin + addToMin;
			addToMin += 2;
		}
		
		return size;
	}
	
	/**
	 * Resets the fontsizes to the defaults so that the next update
	 * will get correct sizes.
	 */
	private void reset(){
		baseFont = new Font("Serif", Font.PLAIN, 11);
		fontMax = 20f;
		fontMin = baseFont.getSize();
		addToMin = 0;
		removeFromMax = 0;
	}
		
	@Override
	public void update(Observable o, Object arg) {
		if(arg != null){
			TagCloudModel model = (TagCloudModel)arg;
			map = model.getTagsOccurrence();
			
			removeAll();
			
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				TagComponent tag = new TagComponent(entry.getKey());
				tag.addMouseListener(new TagComponentController(model, this));
				//TODO move this our to initiator.
				tag.setFont(baseFont.deriveFont(getFontSize(entry.getValue())));
				add(tag);
			}
			
			revalidate();
			
			//Reset everything for the next update.
			this.reset();
		}
	}
}