package se.cth.hedgehogphoto.geocoding.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.LocationGPSObject;

/**
 * Parses XML-documents from the nominatim-server
 * and extracts the essential information about the
 * locations. Requests to the server are limited to 
 * 1 per second, ie a new request can start 1000 ms
 * after the last one was processed. This will result
 * in a slightly longer waiting-period, depending on
 * how fast the XML-documents are processed. This is
 * in line with the nominatim usage policy, which
 * should be provided with this software. 
 * @author Florian Minges
 */
public class XMLParser implements Runnable {
	private static XMLParser xmlParser;
	private DocumentBuilder docBuilder;
	private Map<String, List<LocationGPSObject>> cachedSearchResults = new HashMap<String, List<LocationGPSObject>>();

	public static synchronized XMLParser getInstance() {
		if (xmlParser == null) {
			xmlParser = new XMLParser();
		}

		return xmlParser;
	}

	private XMLParser() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			this.docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { 
			//processSearch can't run if this happens, since docBuilder is null
			Log.getLogger().log(Level.SEVERE, "ParserConfigurationException", e);
		}
	}

	public List<LocationGPSObject> processGeocodingSearch(URL xmlFileUrl) {
		List<LocationGPSObject> locations = processGeocoding(xmlFileUrl, RequestType.GEOCODING_REQUEST);
		return locations;
	}

	public LocationGPSObject processReverseGeocodingSearch(URL xmlFileUrl) {
		List<LocationGPSObject> list = processGeocoding(xmlFileUrl, RequestType.REVERSE_GEOCODING_REQUEST);
		return (list == null || list.size() == 0) ? null : list.get(0); 
	}

	/**
	 * Processes an XML-file requested from the nominatim-server,
	 * either from the geocoding, or the reverse-geocoding-service.
	 * Caches the searchresults to minimize the request to the 
	 * nominatim server. This is in line with the nominatim usage
	 * policy.
	 * @param xmlFileUrl the URL to the searchResult from the nominatim
	 * server.
	 * @param type the kind of request; Geocoding or Reverse-Geocoding.
	 * @return a List of LocationObjects containing the result of the
	 * search.
	 */
	public synchronized List<LocationGPSObject> processGeocoding(URL xmlFileUrl, RequestType type) {
		if (xmlFileUrl == null || type == null) {
			return new ArrayList<LocationGPSObject>();
		}

		/* Check for cached search results first. */
		if (this.cachedSearchResults.containsKey(xmlFileUrl.toString())) {
			return this.cachedSearchResults.get(xmlFileUrl.toString());
		}

		try {
			final String tagName = (type == RequestType.GEOCODING_REQUEST) ? "place" : "result";

			Document doc = this.docBuilder.parse(xmlFileUrl.toString()); 

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listOfPlaces = doc.getElementsByTagName(tagName);
			int nbrOfPlaces = listOfPlaces.getLength();
			List<LocationGPSObject> locations = new LinkedList<LocationGPSObject>();

			for (int index = 0; index < nbrOfPlaces; index++) {

				Node placeID = listOfPlaces.item(index);
				if (placeID.getNodeType() == Node.ELEMENT_NODE) {

					Element placeElement = (Element) placeID;
					String lat = placeElement.getAttribute("lat");
					String lon = placeElement.getAttribute("lon");
					String place = (type == RequestType.GEOCODING_REQUEST) ? 
							placeElement.getAttribute("display_name") : 
								placeElement.getTextContent();

							LocationGPSObject location = new LocationGPSObject(place);
							try {
								location.setLongitude(Double.parseDouble(lon));
								location.setLatitude(Double.parseDouble(lat));
								locations.add(location);
							} catch (NumberFormatException nf) { //don't add location
							}
				}
			}
			this.cachedSearchResults.put(xmlFileUrl.toString(), locations); //cache results
			return locations;

		} catch (SAXParseException error) {
			//create error-message
			StringBuilder sb = new StringBuilder("** Parsing error, line ");
			sb.append(error.getLineNumber());
			sb.append(", uri ");
			sb.append(error.getSystemId());
			sb.append(" ");
			sb.append(error.getMessage());
			Log.getLogger().log(Level.SEVERE, sb.toString(), error);

		} catch (SAXException e) {
			Exception x = e.getException();
			Log.getLogger().log(Level.SEVERE, e.getMessage(), (x == null) ? e : x);

		} catch (Throwable t) {
			Log.getLogger().log(Level.SEVERE, "Error" , t);
		} finally {
			Thread t = new Thread(this);
			t.start();
			try {
				t.join(); /*wait for sleep to finish before leaving the lock
				 *to this query-method*/
			} catch (InterruptedException ie) {
				Log.getLogger().log(Level.SEVERE, "Thread got interrupted.", ie);
			}
		}

		return null; 

	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Log.getLogger().log(Level.SEVERE, "InterruptedException", e);
		}
	}


}
