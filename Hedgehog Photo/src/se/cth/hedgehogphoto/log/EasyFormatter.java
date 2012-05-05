package se.cth.hedgehogphoto.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A custom formatter designed around readability
 * @author Barnabas Sapan
 */
public class EasyFormatter extends Formatter {
	
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		sb.append(record.getLevel());
		sb.append(" | ");
		sb.append(record.getSourceClassName());
		if(record.getThrown() != null){
			sb.append(" | ");
			sb.append(record.getThrown().getStackTrace());
		}
		sb.append("]: ");
		sb.append(record.getMessage());
		sb.append("\n");

		return sb.toString();
	}
	
	@Override
	public String getHead(Handler h){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder()
			.append("\n")
			.append("HedgehogPhoto ")
			.append(dateFormat.format(cal.getTime()))
			.append(" -----------------------")
			.append("\n")
			.append("\n");
		
		return sb.toString();
	}
	
	@Override
	public String getTail(Handler h){
		return new StringBuilder()
					.append("\n")
					.append("----------------------------------------------------------")
					.toString();
	}
}
