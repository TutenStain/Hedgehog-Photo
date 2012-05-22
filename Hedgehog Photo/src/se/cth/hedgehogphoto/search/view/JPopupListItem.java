package se.cth.hedgehogphoto.search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.view.ImageUtils;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JPopupListItem extends JPanel implements JPopupItemI {
	private JLabel image;
	private JTextArea comment;
	private JTextArea tags;
	private PictureObject picture;
	
	private final Dimension IMAGE_SIZE = new Dimension(65, 50);
	private final Dimension COMMENT_AREA_SIZE = new Dimension(70, 50);
	private final Dimension TAG_AREA_SIZE = new Dimension(60, 50);
	
	public JPopupListItem() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		setLayout(flowLayout);
		
		this.image = new JLabel();
		this.image.setMinimumSize(this.IMAGE_SIZE);
		this.image.setMaximumSize(this.IMAGE_SIZE);
		this.image.setPreferredSize(this.IMAGE_SIZE);
		add(this.image);
		
		this.tags = new JTextArea();
		initJTextArea(this.tags);
		this.tags.setFont(new Font("Serif", Font.BOLD, 13));
		this.tags.setMinimumSize(this.TAG_AREA_SIZE);
		this.tags.setMaximumSize(this.TAG_AREA_SIZE);
		this.tags.setPreferredSize(this.TAG_AREA_SIZE);
		add(this.tags);
		
		this.comment = new JTextArea();
	    initJTextArea(this.comment);
		this.comment.setFont(new Font("Serif", Font.PLAIN, 13));
		this.comment.setMinimumSize(this.COMMENT_AREA_SIZE);
		this.comment.setMaximumSize(this.COMMENT_AREA_SIZE);
		this.comment.setPreferredSize(this.COMMENT_AREA_SIZE);
		add(this.comment);
	}
	
	public JPopupListItem(PictureObject pic) {
		this();
		
		this.picture = pic;
		updateGUI();
	}
	
	/** Mutates the component that gets passed in to this method. */
	private void initJTextArea(JTextArea textArea) {
		textArea.setEditable(false);  
		textArea.setCursor(null);  
		textArea.setOpaque(false);  
		textArea.setFocusable(false); 
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setAutoscrolls(false);
	}
	
	public void setPicture(PictureObject pic) {
		this.picture = pic;
		updateGUI();
	}
	
	public PictureObject getPicture() {
		return this.picture;
	}
	
	public List<PictureObject> getPictures() {
		List<PictureObject> pictures = new ArrayList<PictureObject>();
		pictures.add(this.picture);
		return pictures;
	}
	
	public boolean hasPicture() {
		return (this.getPicture() != null);
	}
	
	public void updateGUI() {
		if (this.picture == null){
			return;	
		}

		BufferedImage bi = ImageUtils.resize(new ImageIcon(getPicture().getPath()).getImage(), this.IMAGE_SIZE);
		this.image.setIcon(new ImageIcon(bi));
		this.comment.setText(getPicture().getComment().getComment());
		
		StringBuilder sb = new StringBuilder("");
		List<? extends TagObject> tagList = getPicture().getTags();
		for(TagObject tag : tagList){
			sb.append(tag.getTag());
			sb.append(' ');
		}
		
		this.tags.setText(sb.toString());
	}
	
	@Override
	public void addMouseListener(MouseAdapter listener){
		super.addMouseListener(listener);
		this.tags.addMouseListener(listener);
		this.comment.addMouseListener(listener);
	}
}
