package se.cth.hedgehogphoto.view;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PhotoPanelMouseController {
	private PhotoPanel photoPanel;
	
	public PhotoPanelMouseController(PhotoPanel pp){
		photoPanel = pp;
		photoPanel.setMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getSource() instanceof JLabel){
					JLabel label = (JLabel) e.getSource();
					Image image = photoPanel.getIcon().getImage();
					BufferedImage bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
					ImageIcon icon2 = new ImageIcon(bi);
					photoPanel.setIcon(icon2);
				}
			}
		});
	}
}
