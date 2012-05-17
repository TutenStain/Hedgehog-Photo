package se.cth.hedgehogphoto.log;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hedgehog Photos logging-mechanism based around Javas Logger
 * @author Barnabas Sapan
 */
public class Log {
	private static Logger logger;
	
	public synchronized static Logger getLogger(){
		if(logger == null){
			logger = Logger.getLogger("Hedgehog-logger");
			
			try {
				FileHandler fh = new FileHandler("HedgehogPhoto.log", 10000, 1, false);
				fh.setFormatter(new EasyFormatter());
				logger.addHandler(fh);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.setLevel(Level.ALL);
			logger.setUseParentHandlers(false);
			
			return logger;
		}
		
		else{
			return logger;
		}
	}	
}
