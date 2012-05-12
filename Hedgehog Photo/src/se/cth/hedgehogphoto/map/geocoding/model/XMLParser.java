package se.cth.hedgehogphoto.map.geocoding.model;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import se.cth.hedgehogphoto.objects.LocationObject;

public class XMLParser {
	private static XMLParser xmlParser;
	private DocumentBuilder docBuilder;
	
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
    	Document doc = docBuilder.parse(xmlFileUrl.toString()); //TODO: USE URI INSTEAD OF URL?

    	// normalize text representation
    	doc.getDocumentElement().normalize();

    	NodeList listOfPlaces = doc.getElementsByTagName("place");
    	int nbrOfPlaces = listOfPlaces.getLength();
    	List<LocationObject> locations = new LinkedList<LocationObject>();
    	
    	for (int s = 0; s < nbrOfPlaces; s++) {
    		
    		Node placeID = listOfPlaces.item(s);
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

    } catch (SAXParseException err) {
    	System.out.println("** Parsing error" + ", line "
    			+ err.getLineNumber() + ", uri " + err.getSystemId());
    	System.out.println(" " + err.getMessage());

    } catch (SAXException e) {
    	Exception x = e.getException();
    	((x == null) ? e : x).printStackTrace();

    } catch (Throwable t) {
    	t.printStackTrace();
    }

    return null;

    }// end of main


}
