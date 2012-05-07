package se.cth.hedgehogphoto.map.geolocation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class SearchPanel extends JPanel {

	private EditorPane editorPane = new EditorPane();
	private JComboBox searchBox = new JComboBox();

	private String oldSearch = "";
	private ArrayList<SearchResult> results = new ArrayList<SearchResult>();
	private boolean searching;
	
	private static final String NAMEFINDER_URL = "http://gazetteer.openstreetmap.org/namefinder/search.xml";
	
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
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane, BorderLayout.CENTER);
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
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					String s = e.getDescription();
					int index = Integer.valueOf(s);
					SearchResult result = results.get(index);
//					MapPanel.zoom = result.getZoom() < 1 || result.getZoom() > 18 ? 8
//							: result.getZoom();
//					Point position = MapPanel.computePosition(new Point2D.Double(
//									result.getLon(), result.getLat()));
//					MapPanel.setCenterPosition(position);
//					
//					MapPanel.repaint();
				}
			}
		});
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
		editorPane.setText("<html><body><i>searching...</i></body></html>");
		searchBox.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		editorPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Thread t = new Thread(r, "searcher " + newSearch);
		t.start();
	}

	private void doSearchInternal(final String newSearch) {
		results.clear();
		try {
			// Create a URL for the desired page
			String args = URLEncoder.encode(newSearch, "UTF-8");
			String path = NAMEFINDER_URL + "?find= " + args;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.newSAXParser().parse(path, new DefaultHandler() {
				private final ArrayList<String> pathStack = new ArrayList<String>();
				private final ArrayList<SearchResult> namedStack = new ArrayList<SearchResult>();
				private StringBuilder chars;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes) {
					pathStack.add(qName);
					if ("named".equals(qName)) {
						SearchResult result = new SearchResult();
						result.setType(attributes.getValue("type"));
						result.setLat(tryDouble(attributes.getValue("lat")));
						result.setLon(tryDouble(attributes.getValue("lon")));
						result.setName(attributes.getValue("name"));
						result.setCategory(attributes.getValue("category"));
						result.setInfo(attributes.getValue("info"));
						result.setZoom(tryInteger(attributes.getValue("zoom")));
						namedStack.add(result);
						if (pathStack.size() == 2)
							results.add(result);
					} else if ("description".equals(qName)) {
						chars = new StringBuilder();
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if ("named".equals(qName)) {
						namedStack.remove(namedStack.size() - 1);
					} else if ("description".equals(qName)) {
						namedStack.get(namedStack.size() - 1).setDescription(
								chars.toString());
					}
					pathStack.remove(pathStack.size() - 1);
				}

				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (chars != null)
						chars.append(ch, start, length);
				}

				private double tryDouble(String s) {
					try {
						return Double.valueOf(s);
					} catch (Exception e) {
						return 0d;
					}
				}

				private int tryInteger(String s) {
					try {
						return Integer.valueOf(s);
					} catch (Exception e) {
						return 0;
					}
				}
			});
		} catch (Exception e) {
//			log.log(Level.SEVERE, "failed to search for \"" + newSearch + "\"",
//					e);
			System.out.println("ERROR HAHAHAHAHAHAHAHAHA \n" + e);
		}

		StringBuilder html = new StringBuilder();
		html.append("<html><body>\r\n");
		for (int i = 0; i < results.size(); ++i) {
			SearchResult result = results.get(i);
			String description = result.getDescription();
			description = description.replaceAll("\\[.*?\\]", "");
			String shortName = result.getName();
			shortName = shortName.replaceAll("\\s(.*)$", "");
			String linkBody = shortName + " [" + result.getCategory() + "]";
			html.append("<a href='").append(i).append("'>").append(linkBody)
					.append("</a><br>\r\n");
			html.append("<i>").append(description).append("<br><br>\r\n");
			// String description = result.getDescription() == null ||
			// result.getDescription().length() == 0 ? "-" :
			// result.getDescription();
			// html.append(description).append("<br><br>\r\n");
		}
		html.append("</body></html>\r\n");
		final String html_ = html.toString();

		Runnable r = new Runnable() {
			public void run() {
				try {
					editorPane.setText(html_);
					editorPane.setCaretPosition(0);
					DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) searchBox
							.getModel();
					comboBoxModel.removeElement(newSearch);
					comboBoxModel.addElement(newSearch);
				} finally {
					searchBox.setCursor(Cursor.getDefaultCursor());
					editorPane.setCursor(Cursor.getDefaultCursor());
					searching = false;
					searchBox.setEnabled(true);
				}
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
