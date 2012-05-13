package se.cth.hedgehogphoto.map.geocoding.model;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
import se.cth.hedgehogphoto.objects.LocationObject;

public class XMLParser implements Runnable {
	private static XMLParser xmlParser;
	private DocumentBuilder docBuilder;
	private Lock lock = new ReentrantLock();

	public static synchronized XMLParser getInstance() {
		if (xmlParser == null) 
			xmlParser = new XMLParser();
		return xmlParser;
	}

	private XMLParser() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { 
			//this better not happen.
		}
	}

	public List<LocationObject> processSearch(URL xmlFileUrl){
		try {
			lock.lock();
			Document doc = docBuilder.parse(xmlFileUrl.toString()); 

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listOfPlaces = doc.getElementsByTagName("place");
			int nbrOfPlaces = listOfPlaces.getLength();
			List<LocationObject> locations = new LinkedList<LocationObject>();

			for (int index = 0; index < nbrOfPlaces; index++) {

				Node placeID = listOfPlaces.item(index);
				if (placeID.getNodeType() == Node.ELEMENT_NODE) {

					Element placeElement = (Element) placeID;
					String lat = placeElement.getAttribute("lat");
					String lon = placeElement.getAttribute("lon");
					String place = placeElement.getAttribute("display_name");
					LocationObject location = new LocationObject(place);
					try {
						location.setLongitude(Double.parseDouble(lon));
						location.setLatitude(Double.parseDouble(lat));
						locations.add(location);
					} catch (NumberFormatException nf) { //don't add location
					}
				}
			}

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
			lock.unlock();
		}

		return null; //Return empty list instead?

	}

	@Override
	public void run() {
		   try {
			  lock.lock();
		      Thread.sleep(1000);
		   } catch (InterruptedException ie) {
			   //should not happen :(
		   }
		   finally {
		      lock.unlock();
		   }
//		}

		
	}


}
