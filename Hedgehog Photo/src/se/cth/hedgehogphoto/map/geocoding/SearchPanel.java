package se.cth.hedgehogphoto.map.geocoding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import se.cth.hedgehogphoto.objects.LocationObject;

public final class SearchPanel extends JPanel {

	private ConstrainedGrid resultPanel = new ConstrainedGrid();
	private JComboBox<Object> searchBox = new JComboBox<Object>();

	private String oldSearch = "";
	private boolean searching;
	
	private static final String NAMEFINDER_URL = "http://nominatim.openstreetmap.org/search?";
	
	public static void main(String [] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new SearchPanel());
		frame.pack();
		frame.getContentPane().setVisible(true);
		frame.setVisible(true);
	}

	public SearchPanel() {
		super(new BorderLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setBackground(Color.white);
		JPanel topPanel = new JPanel(new BorderLayout(4, 4));
		topPanel.setOpaque(false);
		topPanel.add(new JLabel("Find:"), BorderLayout.WEST);
		topPanel.add(searchBox, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
//		JScrollPane scrollPane = new JScrollPane(new JPanel());
//		scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
//		scrollPane.setBorder(BorderFactory.createEmptyBorder());
//		scrollPane.getViewport().setBackground(Color.WHITE);
		add(resultPanel, BorderLayout.CENTER);
		searchBox.setEditable(true);
		Component editorComponent = searchBox.getEditor().getEditorComponent();
		searchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				doSearch(searchBox.getSelectedItem());
			}

		});
		if (editorComponent instanceof JTextField) {
			final JTextField textField = (JTextField) editorComponent;
			textField.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					textField.selectAll();
				}
			});
		}
	}

	public void doSearch(Object selectedItem) {
		if (searching)
			return;
		final String newSearch = selectedItem == null ? "" : selectedItem
				.toString();
		if (oldSearch.equals(newSearch))
			return;
		oldSearch = newSearch;
		Runnable r = new Runnable() {
			public void run() {
				doSearchInternal(newSearch);
			}
		};
		searching = true;
		searchBox.setEnabled(false);
//		editorPane.setText("<html><body><i>searching...</i></body></html>");
		searchBox.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		editorPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Thread t = new Thread(r, "searcher " + newSearch);
		t.start();
	}

	private void doSearchInternal(final String newSearch) {
		try {
			// Create a URL for the desired page
			String args = URLEncoder.encode(newSearch, "UTF-8");
			String path = NAMEFINDER_URL + "format=xml&addressdetails=1&q=" + args;
			System.out.println(path);
			ReadAndPrintXMLFile.xmlFile = new URI(path);
			List<LocationObject> locations = ReadAndPrintXMLFile.processSearch();
			resultPanel.addLocations(locations);
			this.setPreferredWidth();
		} catch (Exception e) {
//			log.log(Level.SEVERE, "failed to search for \"" + newSearch + "\"",
//					e);
			System.out.println("ERROR HAHAHAHAHAHAHAHAHA \n" + e);
		}
		

		Runnable r = new Runnable() {
			public void run() {
				try {
//					editorPane.setText("hej");
//					editorPane.setCaretPosition(0);
					DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) searchBox
							.getModel();
					comboBoxModel.removeElement(newSearch);
					comboBoxModel.addElement(newSearch);
				} finally {
					searchBox.setCursor(Cursor.getDefaultCursor());
//					editorPane.setCursor(Cursor.getDefaultCursor());
					
					searching = false;
					searchBox.setEnabled(true);
				}
			}
		};
		SwingUtilities.invokeLater(r);
	}
	
	private void setPreferredWidth() {
		Dimension d = this.getPreferredSize();
		d.width = resultPanel.getPreferredWidth() + 30;
	}
}
