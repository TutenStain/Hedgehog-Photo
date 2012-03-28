package se.cth.hedgehogphoto.tagcloud;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TagCloudView extends JPanel{
	private TagCloudModel tgm;
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Font baseFont = new Font("Serif", Font.PLAIN, 10);
	private float fontMax = 20f;
	private float fontMin = baseFont.getSize();
	
	public TagCloudView(TagCloudModel _tgm){
		tgm = _tgm;
		setLayout(new FlowLayout());
	}
	
	//TODO Maybe a better implementation fontSize based on max/min size.
	private float getFontSize(int tagOccurrence){
		float size = 6.0f * tagOccurrence;
		
		if(size > fontMax){
			size = fontMax;
		}
		
		if(size < fontMin){
			size = fontMin;
		}
		
		return size;
	}
		
	public void update(){
		map = tgm.getTagsOccurrence();
		
		removeAll();
		
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			JLabel label = new JLabel(entry.getKey());
			label.setFont(baseFont.deriveFont(getFontSize(entry.getValue())));
			add(label);
		}
		
		revalidate();
	}
}
