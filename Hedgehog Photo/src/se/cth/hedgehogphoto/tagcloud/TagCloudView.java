package se.cth.hedgehogphoto.tagcloud;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import se.cth.hedgehogphoto.plugin.*;
import se.cth.hedgehogphoto.view.WrapLayout;

/**
 * The view representation of the tagcloud.
 * !setPreferredSize should always be set!
 * @author Barnabas Sapan
 */
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
		//Default values
		setPreferredSize(new Dimension(150, 75));
	}
	
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
		
	@Override
	public void update(Observable o, Object arg) {
		if(arg != null){
			TagCloudModel model = (TagCloudModel)arg;
			map = model.getTagsOccurrence();
			removeAll();
			
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				TagComponent tag = new TagComponent(entry.getKey());
				tag.addMouseListener(new TagComponentController(this, tag, model.getDatabase()));
				tag.setFont(baseFont.deriveFont(getFontSize(entry.getValue())));
				add(tag);
			}
			
			revalidate();
		}
	}
}