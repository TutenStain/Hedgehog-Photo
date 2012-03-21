package se.cth.hedgehogphoto.tagcloud;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TagCloudView extends JPanel{
	private TagCloudModel tgm;
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Font baseFont = new Font("Serif", Font.PLAIN, 20);
	
	public TagCloudView(TagCloudModel _tgm){
		tgm = _tgm;
		setLayout(new FlowLayout());
	}
		
	public void update(){
		map = tgm.getTagsOccurrence();
			
		removeAll();
		
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			JLabel label = new JLabel(entry.getKey());
			label.setFont(baseFont.deriveFont(15.0f * entry.getValue()));
			add(label);
		}
		
		revalidate();
	}
}
