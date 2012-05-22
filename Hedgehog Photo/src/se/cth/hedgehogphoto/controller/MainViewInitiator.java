package se.cth.hedgehogphoto.controller;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		this.view.setPrevNextBtnListeners(new PrevNextBtnListeners());
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
				BufferedImage bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
				ImageIcon icon2 = new ImageIcon(bi);
				photoPanel.setIcon(icon2);
				JPanel cardPanel = view.getCardPanel();
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "One");
				JPanel singlePhotoPanel = view.getSinglePhotoPanel();
				singlePhotoPanel.removeAll();
				singlePhotoPanel.add(photoPanel);
				view.resetSlider();
				view.setPrevNextBtnVisibility(true);
				JScrollPane pane = view.getPhotoView();
				pane.getVerticalScrollBar().setValue(0);
			}
		}
	}
	public class PrevNextBtnListeners implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() instanceof JButton){
				JButton btn = (JButton) arg0.getSource();
				JPanel panel = view.getSinglePhotoPanel();
				PhotoPanel pp = view.getCurrentPhotoPanel();
				List<PhotoPanel> ppList = view.getPhotoPanels();
				if(ppList.contains(pp)){
					panel.removeAll();
					if(btn.getText().equals("Previous") && (ppList.indexOf(pp) - 1) >= 0){
						pp = ppList.get(ppList.indexOf(pp) - 1);
					}
					if(btn.getText().equals("Next") && (ppList.indexOf(pp) + 1) < ppList.size()){
						pp = ppList.get(ppList.indexOf(pp) + 1);
					}
					panel.add(pp);
					panel.repaint();
					panel.revalidate();
					view.resetSlider();
				}
			}
		}
		
	}

}
