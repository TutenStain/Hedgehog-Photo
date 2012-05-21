package se.cth.hedgehogphoto.metadata;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.LocationObjectOther;

public class Converter {
	private static Converter converter;
	private Converter(){
		
	}
	
	public static Converter getInstance(){
		if(converter == null)
			converter = new Converter();
			
		return converter;
	}
	public LocationObjectOther findLocationPlace(LocationObjectOther lo){
		Point.Double p= new Point.Double();
		p.setLocation(lo.getLongitude(),lo.getLatitude());
		URL url =URLCreator.getInstance().queryReverseGeocodingURL(p);
		LocationObjectOther newlocationObject = XMLParser.getInstance().processReverseGeocodingSearch(url);
		lo.setLocation(newlocationObject.getLocation());
		return lo;
	}
	
	public String convertComment(String comment) {
		try{
	
		if(!(comment.equals(""))){
		try {
			comment = convertDecimalNumbersToString(comment);
			System.out.print("comment" + comment);
		}
		catch (NumberFormatException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" +comment + "\" to a comment.", e);
		}
		}
		}
		catch (Exception e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" +comment + "\" to a comment.", e);
		}
		return comment;
	}
	
	public List<String> convertTags(String tag) {
		List<String> tags= new ArrayList<String>();
		try{
		if(!(tag.equals(""))){
			
		try {
			String string = convertDecimalNumbersToString(tag);
			tag =string;
			System.out.print(tag);

			String[] args = string.split(";");

			try{
			
			for(int k = 0; k<args.length ; k++){
				
			tags.add(args[k]);
			}
			}catch(NullPointerException e){
				System.out.print("NULL");
			}
		
			

			
		}
		catch (NumberFormatException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + tag + "\" to a tag.", e);
		}
		}
		}catch (Exception e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" +tag + "\" to a tag.", e);
		}
		return tags;
	}
	
	/**
	 * Converts a string that is on decimal format into a
	 * "normal" string. The string may look something like 
	 * "67, 101, 33, 33"
	 * @param decimalString
	 * @return
	 * @throws NumberFormatException
	 */
	public String convertDecimalNumbersToString(String decimalString) throws NumberFormatException {
		StringBuilder builder = new StringBuilder("");
		List<Integer> i = new ArrayList<Integer>();
		
		String string = decimalString.substring(1);
		for (String s: string.split(", ")) {
			i.add(Integer.parseInt(s));
		}

		for (Integer ii: i) {
			String aChar = Character.valueOf((char)(int)ii).toString();
			aChar = aChar.trim();
			builder.append(aChar);
		}
		
		return builder.toString();
	}

}
