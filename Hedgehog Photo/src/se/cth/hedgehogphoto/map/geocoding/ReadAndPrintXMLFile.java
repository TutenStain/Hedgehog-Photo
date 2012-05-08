package se.cth.hedgehogphoto.map.geocoding;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import se.cth.hedgehogphoto.objects.LocationObject;

public class ReadAndPrintXMLFile{
	public static URI xmlFile;
	
	public static void initXMLFile() {
		if (xmlFile == null) 
			try {
				xmlFile = new URI("http://nominatim.openstreetmap.org/search?format=xml&addressdetails=1&q=london");
			}	catch (Exception e) { }
		
	}

    public static List<LocationObject> processSearch(){
    try {
    		initXMLFile();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (xmlFile.toString());

            // normalize text representation
            doc.getDocumentElement ().normalize ();
//            System.out.println ("Root element of the doc is " + 
//                 doc.getDocumentElement().getNodeName());


            NodeList listOfPlaces = doc.getElementsByTagName("place");
            int nbrOfPlaces = listOfPlaces.getLength();
            System.out.println("Total no of places : " + nbrOfPlaces);

            List<LocationObject> locations = new LinkedList<LocationObject>();
            for(int s=0; s < nbrOfPlaces ; s++){


                Node placeID = listOfPlaces.item(s);
                if(placeID.getNodeType() == Node.ELEMENT_NODE){

                    Element placeElement = (Element)placeID;
                    String lat = placeElement.getAttribute("lat");
                    String lon = placeElement.getAttribute("lon");
                    String place = placeElement.getAttribute("display_name");
                    LocationObject location = new LocationObject(place);
                    try {
                    	location.setLongitude(Double.parseDouble(lon));
                        location.setLatitude(Double.parseDouble(lat));
                       	locations.add(location);
                    } catch (NumberFormatException nf) { }
                    
                }//end of if clause


            }//end of for loop with s var

            return locations;

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }

    	return null;

    }//end of main


}
