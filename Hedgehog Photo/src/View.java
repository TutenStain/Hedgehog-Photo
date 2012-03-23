package hedgehogView;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {

	private JFrame frame;
	private JTextField searchField;
	PhotoPanel[] photoPanels = new PhotoPanel[2];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame =  new JFrame();
		frame.setBounds(200, 200, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		JButton btnShowhideTags = new JButton("Show/Hide tags");


		JButton btnShowhideLocation = new JButton("Show/Hide location");
		btnShowhideLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				photoPanels[0].displayLocation(!photoPanels[0].isVisibleLocation());
				photoPanels[1].displayLocation(!photoPanels[1].isVisibleLocation());

			}
		});

		JSlider slider = new JSlider();

		JButton showHideComments = new JButton("Show/Hide comments");


		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(100)
						.addComponent(btnShowhideTags)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnShowhideLocation)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(showHideComments)
						.addGap(202)
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(59, Short.MAX_VALUE))
				);
		gl_bottomPanel.setVerticalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnShowhideLocation)
										.addComponent(btnShowhideTags)
										.addComponent(showHideComments)))
										.addContainerGap())
				);
		bottomPanel.setLayout(gl_bottomPanel);

		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		searchField = new JTextField();
		searchField.setText("Search..");
		searchField.setColumns(10);

		JLabel lblAlbum = new JLabel("Album");

		JLabel lblicon = new JLabel("Igelkottsbild");
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
				gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblicon)
						.addGap(38)
						.addComponent(lblAlbum)
						.addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(44))
				);
		gl_topPanel.setVerticalGroup(
				gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAlbum)
								.addComponent(lblicon)))
				);
		topPanel.setLayout(gl_topPanel);

		JPanel leftPanelView = new JPanel();
		frame.getContentPane().add(leftPanelView, BorderLayout.WEST);
		leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));

		JButton btnMaps = new JButton("Maps");
		leftPanelView.add(btnMaps);

		JButton btnTags = new JButton("Tags");
		leftPanelView.add(btnTags);

		JButton btnCalender = new JButton("Calender");
		leftPanelView.add(btnCalender);

		ScrollPane photoView = new ScrollPane();
		frame.getContentPane().add(photoView, BorderLayout.CENTER);

		JPanel photoViewPanel = new JPanel();
		photoView.add(photoViewPanel);


		photoPanels[0] = new PhotoPanel("C:\\Users\\starpie\\Desktop\\Hedgehog\\\u00F6vriga bilder\\kottis.jpg");
		photoPanels[1] = new PhotoPanel("C:\\Users\\starpie\\Desktop\\Hedgehog\\\u00F6vriga bilder\\bananid�.png");
		photoViewPanel.add(photoPanels[0]);
		photoViewPanel.add(photoPanels[1]);

		showHideComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				photoPanels[0].displayComments(!photoPanels[0].isVisibleComments());
				photoPanels[1].displayComments(!photoPanels[1].isVisibleComments());
			}
		});
		btnShowhideTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				photoPanels[0].displayTags(!photoPanels[0].isVisibleTags());
				photoPanels[1].displayTags(!photoPanels[1].isVisibleTags());
			}
		});
	}
}
