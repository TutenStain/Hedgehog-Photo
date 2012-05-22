package se.cth.hedgehogphoto.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.global.Constants;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.model.MainModel;

/**
 * 
 * @author David Grankvist
 */

public class MainView implements Observer {

	private JFrame frame;
	private JPanel cardPanel = new JPanel();
	private JPanel singlePhotoPanel = new JPanel();
	private JPanel photoViewPanel = new JPanel();
	private	JPanel leftPanelView = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JScrollPane photoView = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private LoadingLayer[] layers = new LoadingLayer[3];
	private JPanel topPanel = new JPanel();
	private JPanel topBtnArea = new JPanel();
	private JButton uploadPictures = new JButton("Import pictures");
	private JButton btnNextPP = new JButton("Next");
	private JButton btnPrevPP = new JButton("Previous");
	private JButton btnBack = new JButton("Back");
	final private JButton btnShowHideName = new JButton("Show/Hide name");
	final private JButton btnShowHideTags = new JButton("Show/Hide tags");
	final private JButton btnShowHideLocation = new JButton("Show/Hide location");
	final private JButton btnshowHideComments = new JButton("Show/Hide comments");
	final private JSlider slider = new JSlider(50, 200, 100);
	//TODO Can we keep this? Enough MVC?
	private List<PhotoPanel> photoPanels;

	private ActionListener actionListener;
	private FocusListener focusListener;
	private MouseAdapter mouseListener;

	public MainView(JFrame startView) {
		initialize(startView);
	}

	private void initialize(JFrame startView) {
		this.frame = startView;
		this.photoPanels = new ArrayList<PhotoPanel>();
		//TODO Minimum size?
		this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(300)
						.addComponent(this.btnShowHideTags)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(this.btnShowHideLocation)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(this.btnshowHideComments)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(this.btnShowHideName)
						.addGap(150)
						.addComponent(this.slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(59, Short.MAX_VALUE))
				);
		gl_bottomPanel.setVerticalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(this.btnShowHideLocation)
										.addComponent(this.btnShowHideTags)
										.addComponent(this.btnshowHideComments)
										.addComponent(this.btnShowHideName)))
										.addContainerGap())
				);
		this.bottomPanel.setLayout(gl_bottomPanel);

		this.topPanel.setLayout(new BorderLayout());
		this.frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.topPanel.add(this.uploadPictures, BorderLayout.WEST);

		Dimension d = new Dimension(Constants.PREFERRED_MODULE_WIDTH, Constants.PREFERRED_MODULE_HEIGHT);
		this.leftPanelView.setPreferredSize(d);
		this.leftPanelView.setMinimumSize(d);
		this.leftPanelView.setSize(d);
		this.frame.getContentPane().add(this.leftPanelView, BorderLayout.WEST);
		this.leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));

		//Lays out the placeholder widgets and start the loading animation
		for(int i = 0; i < 3; i++) {
			JPanel p = new JPanel();
			p.add(new JLabel("Loading plugin..."));
			this.layers[i] = new LoadingLayer(p);
			this.leftPanelView.add(layers[i].getDecoratedPanel());	
			this.layers[i].start();
		}
		
		this.frame.getContentPane().add(this.photoView, BorderLayout.CENTER);
		this.photoView.setViewportView(this.cardPanel);
		this.cardPanel.setLayout(new CardLayout());
		this.cardPanel.add(this.photoViewPanel, "All");
		this.cardPanel.add(this.singlePhotoPanel, "One");
		this.photoViewPanel.setLayout(new WrapLayout(FlowLayout.LEFT));
		this.singlePhotoPanel.setLayout(new WrapLayout(FlowLayout.LEFT));
		this.topPanel.add(this.topBtnArea, BorderLayout.CENTER);
		this.topBtnArea.add(btnBack);
		this.topBtnArea.add(btnPrevPP);
		this.topBtnArea.add(btnNextPP);
		this.setTopButtonsVisibility(false);
		this.setOverallBackground(Constants.GUI_BACKGROUND);
	}
	public PhotoPanel getCurrentPhotoPanel(){
		Component[] comps = this.singlePhotoPanel.getComponents();
		for(int i=0;i<comps.length;i++){
			if(comps[i] instanceof PhotoPanel){
				return (PhotoPanel) comps[i];
			}
		}
		return null;
	}
	
	public void setCommentsButtonListener(ActionListener l){
		this.btnshowHideComments.addActionListener(l);
	}

	public void setUploadPictureButtonListener(ActionListener l){
		this.uploadPictures.addActionListener(l);
	}

	public void setTagsButtonListener(ActionListener l){
		this.btnShowHideTags.addActionListener(l);
	}

	public void setLocationButtonListener(ActionListener l){
		this.btnShowHideLocation.addActionListener(l);
	}

	public void setPhotoNameButtonListener(ActionListener l){
		this.btnShowHideName.addActionListener(l);
	}

	public void setSliderListener(ChangeListener l){
		this.slider.addChangeListener(l);
	}

	public List<PhotoPanel> getPhotoPanels(){
		return this.photoPanels;
	}
	
	public JPanel getCardPanel(){
		return this.cardPanel;
	}
	public JPanel getSinglePhotoPanel(){
		return this.singlePhotoPanel;
	}
	public void resetSlider(){
		this.slider.setValue(100);
	}
	public JScrollPane getPhotoView(){
		return this.photoView;
	}
	public void setOverallBackground(Color c){
		this.bottomPanel.setBackground(c);
		this.topPanel.setBackground(c);
		this.topBtnArea.setBackground(c);
		this.photoViewPanel.setBackground(c);
		this.singlePhotoPanel.setBackground(c);
		this.leftPanelView.setBackground(c);
		this.slider.setBackground(c);
	}
	public void setTopButtonsVisibility(Boolean b){
		this.btnBack.setVisible(b);
		this.btnPrevPP.setVisible(b);
		this.btnNextPP.setVisible(b);
	}
	public void addPlugin(JPanel panel, PluginArea placement){
		if(panel != null){
			panel.setBackground(Constants.GUI_BACKGROUND);
			if(placement == PluginArea.SEARCH){
				this.topPanel.add(panel, BorderLayout.EAST);
			} else {	
				if(placement == PluginArea.LEFT_TOP){
					this.layers[0].stopAndRemove();
					this.leftPanelView.remove(0);
					this.leftPanelView.add(panel, 0);
				}

				if(placement == PluginArea.LEFT_MIDDLE){
					this.layers[1].stopAndRemove();
					this.leftPanelView.remove(1);
					this.leftPanelView.add(panel, 1);
				}

				if(placement == PluginArea.LEFT_BOTTOM){
					this.layers[2].stopAndRemove();
					this.leftPanelView.remove(2);
					this.leftPanelView.add(panel, 2);
				}
			}
		} else {
			Log.getLogger().log(Level.SEVERE, "Could not add plugin panel to the view, panel is null");
		}
	}

	public void addPhotoPanelActionListeners(ActionListener listener) {
		this.actionListener = listener;
		for (PhotoPanel panel : this.photoPanels) {
			panel.setTextFieldActionListeners(listener);
		}
	}

	public void addPhotoPanelMouseListener(MouseAdapter listener) {
		this.mouseListener = listener;
		for (PhotoPanel panel : this.photoPanels) {
			panel.addMouseListener(listener);
		}
	}

	public void addPhotoPanelFocusListener(FocusListener listener) {
		this.focusListener = listener;
		for (PhotoPanel panel : this.photoPanels) {
			panel.setTextFieldFocusListeners(listener);
		}
	}
	public void setPrevNextBtnListeners(ActionListener l){
		this.btnPrevPP.addActionListener(l);
		this.btnNextPP.addActionListener(l);
	}
	public void setBackBtnListener(ActionListener l){
		this.btnBack.addActionListener(l);
	}
	public JPanel getPhotoViewPanel(){
		return this.photoViewPanel;
	}
	private void addListenersToAll() {
		addPhotoPanelActionListeners(this.actionListener);
		addPhotoPanelFocusListener(this.focusListener);
		addPhotoPanelMouseListener(this.mouseListener);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.setTopButtonsVisibility(false);
		this.resetSlider();
		this.photoPanels = new ArrayList<PhotoPanel>();
		List<? extends PictureObject> images;
		if(this.cardPanel.getLayout() instanceof CardLayout){
			CardLayout cl = (CardLayout) this.cardPanel.getLayout();
			cl.show(this.cardPanel, "All");
		}
		//TODO Maybe refresh in another way than removing the PhotoPanels?
		this.photoViewPanel.removeAll();
		this.photoViewPanel.repaint();

		if(arg1 instanceof MainModel) {
			MainModel model = (MainModel)arg1;
			images = model.getImages();

			for(int i = 0; i < images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				try{
					pp.setComment(images.get(i).getComment().getComment());
				}catch(Exception f){

				}
				try{
					pp.setTags(images.get(i).getTags());
				}catch(Exception f){

				}
				try{
					pp.setLocation(images.get(i).getLocation().getLocation());
				}catch(Exception f){

				}
				try{
					pp.setName(images.get(i).getName());
				}catch(Exception f){

				}
				System.out.println(images.get(i).getName());
				
				this.photoPanels.add(i, pp);
				this.photoViewPanel.add(pp);
			}
			addListenersToAll();
		}

		if(arg1 instanceof Files){
			images = Files.getInstance().getPictureList();
			for(int i = 0; i<images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				try{
					pp.setComment(images.get(i).getComment().getComment());
				}catch(Exception f){

				}
				try{
					pp.setTags(images.get(i).getTags());
				}catch(Exception f){

				}
				try{
					pp.setLocation(images.get(i).getLocation().getLocation());
				}catch(Exception f){

				}
				try{
					pp.setName(images.get(i).getName());
				}catch(Exception f){

				}
				System.out.println(images.get(i).getName());
				this.photoPanels.add(i, pp);
				this.photoViewPanel.add(pp);
			}
			addListenersToAll();
		}
		//TODO just a test
		if(!photoPanels.isEmpty()){
			for(int i = 0; i < this.photoPanels.size(); i++){
				Image image = this.photoPanels.get(i).getIcon().getImage();
				float scale = Constants.PREFERRED_PICTURE_HEIGHT/image.getHeight(null);
				BufferedImage bi = ImageUtils.resize(image, Math.round(image.getWidth(null)*scale),
						Math.round(Constants.PREFERRED_PICTURE_HEIGHT));
				ImageIcon icon2 = new ImageIcon(bi);
				this.photoPanels.get(i).setIcon(icon2);
				this.photoPanels.get(i).setScaleDimension(new Dimension(Math.round(image.getWidth(null)*scale), 
						Math.round(image.getHeight(null)*scale)));
			}
		}
		this.frame.revalidate();
	}
}
