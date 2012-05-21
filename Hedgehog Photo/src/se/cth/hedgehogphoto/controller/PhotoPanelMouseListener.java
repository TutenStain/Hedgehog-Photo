package se.cth.hedgehogphoto.controller;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.view.ImageUtils;
import se.cth.hedgehogphoto.view.PhotoPanel;


public class PhotoPanelMouseListener extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof PhotoPanel) {
			PhotoPanel photoPanel = (PhotoPanel) e.getSource();
			CardLayout cl = (CardLayout)(photoPanel.getCardPanel().getLayout());
			Image image = photoPanel.getIcon().getImage();
			BufferedImage bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
			ImageIcon icon2 = new ImageIcon(bi);
			photoPanel.setIcon(icon2);
		}
	}
}
