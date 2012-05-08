package se.cth.hedgehogphoto.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;

import se.cth.hedgehogphoto.objects.ImageObject;


/**
 * A wrapper-class for the metadata extractor
 * @author Florian
 */
public class Metadata {
	
	private static File file = new File("test3.jpg"); //default file
	private static final String[] metadataTypes = 
			{"Modify Date", "Artist", "XPComment", "XPAuthor", 
					"XPKeywords", "Date Time Original", "Interop Index", 
					"Interop Version", "Unknown Tag (0x3)", "Unknown Tag (0x4)"};
	
	public static void main(String [] args) {
		try {
			IImageMetadata metadata = extractMetadata();
			ImageObject io = getImageObject(metadata);
			setFileProperties(io);
			io.print();
		} catch (IOException e) {
			//IF POSSIBLE: Add exception handling
			e.printStackTrace();
		}
	}
	
	private static IImageMetadata extractMetadata() {
		return extractMetadata(file);
	}
	
	public static IImageMetadata extractMetadata(File file) {
		Metadata.file = file;
		IImageMetadata metadata = null;
		try {
			metadata = Sanselan.getMetadata(file);
//			System.out.println(metadata.toString());
		} catch (ImageReadException e) {
			//IF POSSIBLE: Add exception handling
			e.printStackTrace();
		} catch (IOException e) {
			//IF POSSIBLE: Add exception handling
			e.printStackTrace();
		}
		
		return metadata;
	}
	
	public static ImageObject getImageObject(IImageMetadata imageMetadata) throws IOException {
		String metadata = imageMetadata.toString();
		BufferedReader br = new BufferedReader( new StringReader(metadata) );
		ImageObject imageObject = new ImageObject();
		
		String line;
		while((line = br.readLine()) != null) {		
			if (containsTargetMetadata(line)) {
				setPropertyFromString(imageObject, line);
			}
		}
		
		return imageObject;
	}
	
	private static boolean containsTargetMetadata(String line) {
		int nbrOfTypes = metadataTypes.length;
		for(int i = 0; i < nbrOfTypes; i++) {
			if(line.contains(metadataTypes[i])) {
				return true;
			}
		}
		return false;
	}
	
	private static void setPropertyFromString(ImageObject imageObject, String line) {
		line = line.trim();
		String property = getProperty(line);
		String value = getPropertyValue(line);
		imageObject.setProperty(property, value);
	}
	
	private static String getProperty(String line) {
		int indexOfPropertyEnds = line.indexOf(":");
		return line.substring(0,indexOfPropertyEnds);
	}
	
	private static String getPropertyValue(String line) {
		int indexOfValueStarts = line.indexOf(":") + 1;
		return line.substring(indexOfValueStarts);
	}
	
	private static void setFileProperties(ImageObject io) {
		io.setProperty("fileName", file.getName());
		io.setProperty("filePath", file.getPath());
	}
	
}
