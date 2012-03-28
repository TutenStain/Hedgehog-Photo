package se.cth.hedgehogphoto.map;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Complete class taken from the link below.
 * @author 'Geek'
 * @source http://geekexplains.blogspot.se/2009/04/implementation-of-sax-parser-in-java.html
 * @date 2012-03-27
 * @modifiedby Florian Minges
 */
public class XMLParser extends DefaultHandler{

	//Path of the XML File to be parsed - private as we don't want it outside
	//and 'final' as once it's assigned to a value (path), it doesn't require any change
	private static final String XML_FILE_TO_BE_PARSED = "berlin.xml";
	private static final List<String> STRINGS_TO_LOOK_FOR_IN_DOCUMENT = new ArrayList<String>();
	private static String lastProcessedString = "test";
	//TODO the list above should be initialized from input when this class "starts"
	
	//Reference to the output stream
	private static Writer out;    
 
	public XMLParser() {
		STRINGS_TO_LOOK_FOR_IN_DOCUMENT.add("formatted_address");
		STRINGS_TO_LOOK_FOR_IN_DOCUMENT.add("lat");
		STRINGS_TO_LOOK_FOR_IN_DOCUMENT.add("lng");
	}

    public static void main (String [] args) {
        //Getting a new instance of the SAX Parser Factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try {
            //Setting up the output stream - in this case System.out with UTF8 encoding
            out = new OutputStreamWriter(System.out, "UTF8");

            //Getting a parser from the factory
            SAXParser saxParser = factory.newSAXParser();
            
            //Parsing the XML document using the parser
            saxParser.parse( new File(XML_FILE_TO_BE_PARSED), new XMLParser() );
//            saxParser.parse( new File("berlinlatlong.xml"), new XMLParser() );
            
        } catch (Throwable throwable) { //Throwable as it can be either Error or Exception
            throwable.printStackTrace ();
        }
    }

    //Implementation of the required methods of the ContentHandler interface
    
//    public void startDocument() throws SAXException {
//        
//    }
//
//    public void endDocument() throws SAXException {
////        try {
//////             out.flush ();
////        } catch(IOException ioe) {
////            throw new SAXException ("ERROR: I/O Eexception thrown while parsing XML", ioe);
////        }
//    }

//    public void startElement(String namespaceURI, String localName, String node, Attributes attributes) throws SAXException {   
//        if (attributes != null) {
//            for (int i = 0; i < attributes.getLength(); i++) {
//                
//                if(namespaceURI.equalsIgnoreCase("lat")) {
////                	printData (attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"");
////                	printData (attributes.getValue(i));
//                }
//            }
//        }
//    }
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
    	printData (qName);
    }

//    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
//    	
//    }

    public void characters(char buffer [], int offset, int length) throws SAXException {
        String string = new String(buffer, offset, length);
        printData(string);
    }

    //Definition of helper methods
    
    /**
     * printData: accepts a String and prints it on the assigned output stream
     * if the last processed string is found in "the list".
     * @param string
     * @throws SAXException
     */
    private void printData(String string)throws SAXException {
        try {
        	for(int i = 0; i<STRINGS_TO_LOOK_FOR_IN_DOCUMENT.size(); i++) {
        		if( lastProcessedString.equalsIgnoreCase(STRINGS_TO_LOOK_FOR_IN_DOCUMENT.get(i)) ) {
        			out.write(lastProcessedString + ": " + string);
        			printNewLine();
        			out.flush();
        		}
        	}
        } catch (IOException ioe) {
            throw new SAXException ("ERROR: I/O Exception thrown while printing the data", ioe);
        } finally {
        	lastProcessedString = string;
        }
    }

    //printNewLine: prints a new line on the underlying platform
    //end of line character may vary from one platform to another
    private void printNewLine() throws SAXException {
        //Getting the line separator of the underlying platform
    	String endOfLine =  System.getProperty("line.separator");
        
        try {
            out.write (endOfLine);
        } catch (IOException ioe) {
            throw new SAXException ("ERROR: I/O Exception thrown while printing a new line", ioe);
        }
    }

}
