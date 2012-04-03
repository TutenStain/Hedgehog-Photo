package se.cth.hedgehogphoto.tagcloud;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.search.SearchModel;

/**
 * @author Barnabas Sapan
 */

public class TagCloudView extends JPanel implements Observer{
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Font baseFont = new Font("Serif", Font.PLAIN, 11);
	private float fontMax = 20f;
	private float fontMin = baseFont.getSize();
	
	public TagCloudView(){
		setLayout(new FlowLayout());
	}
	
	//TODO Maybe a better implementation fontSize based on max/min size.
	private float getFontSize(int tagOccurrence){
		float size = map.size() * tagOccurrence;
		
		if(size > fontMax){
			size = fontMax;
		}
		
		if(size < fontMin){
			size = fontMin;
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
				tag.addMouseListener(new TagComponentController(this, tag));
				tag.setFont(baseFont.deriveFont(getFontSize(entry.getValue())));
				add(tag);
			}
			
			revalidate();
		}
	}
}
