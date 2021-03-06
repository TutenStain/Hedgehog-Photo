package se.cth.hedgehogphoto.controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import se.cth.hedgehogphoto.global.Constants;
import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.view.ImageUtils;
import se.cth.hedgehogphoto.view.MainView;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class MainViewInitiator {
	private MainView view;
	private MainModel model;

	public MainViewInitiator(JFrame frame) {
		this.model = new MainModel();
		this.view = new MainView(frame);
		model.addObserver(this.view);
		new DefaultController(this.view);
		this.view.addPhotoPanelActionListeners(new PhotoPanelActionListener());
		this.view.addPhotoPanelFocusListener(new PhotoPanelFocusListener());
		this.view.addPhotoPanelMouseListener(new PhotoPanelMouseListener());
	}

	public MainView getMainView() {
		return this.view;
	}

	public MainModel getMainModel() {
		return this.model;
	}

	public class PhotoPanelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof PhotoPanel) {
				PhotoPanel photoPanel = (PhotoPanel) e.getSource();
				Image image = photoPanel.getIcon().getImage();
				float scale;
				BufferedImage bi;
				if(image.getWidth(null) > Constants.MAX_PICTURE_WIDTH){
					scale = Constants.MAX_PICTURE_WIDTH/image.getWidth(null);
					bi = ImageUtils.resize(image, Math.round(Constants.MAX_PICTURE_WIDTH),
							Math.round(image.getHeight(null)*scale));
					if(bi.getHeight() > Constants.MAX_PICTURE_HEIGHT){
						scale = Constants.MAX_PICTURE_HEIGHT/bi.getHeight();
						bi = ImageUtils.resize(image, Math.round(bi.getWidth()*scale), Math.round(Constants.MAX_PICTURE_HEIGHT));
					}
				}else if(image.getHeight(null) > Constants.MAX_PICTURE_HEIGHT){
					scale = Constants.MAX_PICTURE_HEIGHT/image.getHeight(null);
					bi = ImageUtils.resize(image, Math.round(image.getWidth(null)*scale),
							Math.round(Constants.MAX_PICTURE_HEIGHT));
					if(bi.getWidth() > Constants.MAX_PICTURE_WIDTH){
						scale = Constants.MAX_PICTURE_WIDTH/bi.getWidth();
						bi = ImageUtils.resize(image, Math.round(Constants.MAX_PICTURE_WIDTH), Math.round(bi.getHeight()*scale));
					}
				}else{
					bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
				}
				photoPanel.setScaleDimension(new Dimension(bi.getWidth(), bi.getHeight()));
				ImageIcon icon2 = new ImageIcon(bi);
				photoPanel.setIcon(icon2);
				JPanel cardPanel = view.getCardPanel();
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "One");
				JPanel singlePhotoPanel = view.getSinglePhotoPanel();
				singlePhotoPanel.removeAll();
				singlePhotoPanel.add(photoPanel);
				view.resetSlider();
				view.setTopButtonsVisibility(true);
				JScrollPane pane = view.getPhotoView();
				pane.getVerticalScrollBar().setValue(0);
			}
		}
	}
}
