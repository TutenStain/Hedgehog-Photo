package se.cth.hedgehogphoto.geocoding.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import se.cth.hedgehogphoto.geocoding.model.URLCreator;
import se.cth.hedgehogphoto.geocoding.model.XMLParser;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.LocationGPSObject;

/**
 * The MainView in the geocoding-subsystem.
 * @author Florian Minges
 */
@SuppressWarnings("serial")
public final class GeoSearchPanel extends JPanel {

	private static GeoSearchPanel instance;
	private static JFrame frame;
	private ConstrainedGrid resultPanel = new ConstrainedGrid();
	private JComboBox<Object> searchBox = new JComboBox<Object>();

	private JButton okButton;
	private JButton cancelButton;

	private URLCreator urlCreator = URLCreator.getInstance();
	private XMLParser xmlParser = XMLParser.getInstance();

	private String oldSearch = "";
	private boolean searching;

	public static GeoSearchPanel getInstance() {
		if (instance == null) {
			instance = new GeoSearchPanel();
		}

		instance.setVisible(true);

		return instance;
	}

	private GeoSearchPanel() {
		super(new BorderLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setBackground(Color.white);
		JPanel topPanel = new JPanel(new BorderLayout(4, 4));
		topPanel.setOpaque(false);
		topPanel.add(new JLabel("Find:"), BorderLayout.WEST);
		topPanel.add(this.searchBox, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(this.resultPanel, BorderLayout.CENTER);
		this.searchBox.setEditable(true);
		Component editorComponent = this.searchBox.getEditor().getEditorComponent();
		this.searchBox.addActionListener(new ActionListener() {
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

		this.okButton = new JButton("Ok");
		this.cancelButton = new JButton("Cancel");
		this.okButton.setEnabled(false);

		JPanel buttonWrapper = new JPanel();
		buttonWrapper.add(this.okButton);
		buttonWrapper.add(this.cancelButton);

		add(buttonWrapper, BorderLayout.SOUTH);
		createFrame();
	}

	public void createFrame() {
		GeoSearchPanel.frame = new JFrame();
		GeoSearchPanel.frame.setPreferredSize(new Dimension(400,600));
		GeoSearchPanel.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GeoSearchPanel.frame.getContentPane().add(this);
		GeoSearchPanel.frame.pack();
		GeoSearchPanel.frame.getContentPane().setVisible(true);
		GeoSearchPanel.frame.setVisible(true);
	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		if (frame != null) {
			frame.setVisible(state);
		}
	}

	public void addOkButtonListener(ActionListener listener) {
		this.okButton.addActionListener(listener); 
	}

	public void addCancelButtonListener(ActionListener listener) {
		this.cancelButton.addActionListener(listener);
	}

	public void enableOkButton(boolean state) {
		this.okButton.setEnabled(state);
	}

	public void doSearch(Object selectedItem) {
		if (this.searching) {
			return;
		}

		final String newSearch = selectedItem == null ? "" : selectedItem
				.toString();

		if (this.oldSearch.equals(newSearch)) {
			return;
		}

		this.oldSearch = newSearch;

		Runnable runnable = new Runnable() {
			public void run() {
				doSearchInternal(newSearch);
			}
		};

		this.searching = true;
		this.searchBox.setEnabled(false);
		this.searchBox.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Thread thread = new Thread(runnable, "searcher " + newSearch);
		thread.start();
	}

	private void doSearchInternal(final String newSearch) {
		try {
			// Create a URL for the desired page, and create objects for the searchResults
			URL url = this.urlCreator.queryGeocodingURL(newSearch);
			List<LocationGPSObject> locations = xmlParser.processGeocodingSearch(url);
			this.resultPanel.addLocations(locations);
			this.setPreferredWidth();
		} catch (Exception e) {
			Log.getLogger().log(Level.SEVERE, "failed to search for \"" + newSearch + "\"", e);
		}


		Runnable runnable = new Runnable() {
			public void run() {
				try {
					DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel)searchBox.getModel();
					comboBoxModel.removeElement(newSearch);
					comboBoxModel.addElement(newSearch);
				} finally {
					searchBox.setCursor(Cursor.getDefaultCursor());
					searching = false;
					searchBox.setEnabled(true);
				}
			}
		};

		SwingUtilities.invokeLater(runnable);
	}

	public void setInitialSearchBoxText(final String text) {
		if (text == null) {
			return;
		}

		Object object = new Object() {
			public String toString() { 
				return text; 
			}
		};
		this.searchBox.addItem(object);
		this.searchBox.setSelectedItem(object);

		doSearch(this.searchBox.getSelectedItem());
	}

	private void setPreferredWidth() {
		Dimension dimension = this.getPreferredSize();
		dimension.width = this.resultPanel.getPreferredWidth() + 30;
	}

	public void addResultPanelMouseListener(MouseAdapter adapter) {
		this.resultPanel.setMouseAdapter(adapter);
	}
}
