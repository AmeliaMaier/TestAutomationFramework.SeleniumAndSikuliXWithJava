package changeWithCare;

import org.sikuli.script.*;
import doNotChange.Log;

public class SikuliXUtility
{
	public static boolean clickButton(String imageName, Log log) {
	    double maxTimeExists = 60;
	    Integer maxTimeClick = 60;
		// Window is already opened by WebDriver
	    Screen s = new Screen();
	    try {
	      s.exists(imageName, maxTimeExists);
	      s.click(imageName, maxTimeClick);
	    } catch (FindFailed ff) {
	    	log.write("Unable to click on the button found in image file: " + imageName);
	    	log.write(ff.getMessage());
	    	ff.printStackTrace(System.out);
	      return false;
	    } catch (Exception ex) {
	    	log.write("Unable to click on the button found in image file: " + imageName);
	    	log.write(ex.getMessage());
	    	ex.printStackTrace(System.out);
	      return false;	      
	    }
	    return true;
	  }

	  public static boolean verifyImage(String imageName, Log log) {
		double maxTimeExists = 60;
		// Window is already opened by WebDriver
	    Screen s = new Screen();
	    try {
	      return (s.exists(imageName, maxTimeExists)!= null);
	    } catch (Exception ex) {
	    	log.write("Unable to search for element shown in image file: " +  imageName);
	    	log.write(ex.getMessage());
	    	ex.printStackTrace(System.out);
	      return false;	      
	    }
	  }
	  
	  public static boolean enterText(String imageName, String input, Log log) {
			double maxTimeExists = 60;

		    Integer maxTimeClick = 60;
			// Window is already opened by WebDriver
		    Screen s = new Screen();
		    try {
		      s.exists(imageName, maxTimeExists);
		      s.click(imageName, maxTimeClick);
		      s.type(input);
		    } catch (FindFailed ff) {
		    	log.write("Unable to click on or type into element found in image file: " + imageName);
		    	log.write(ff.getMessage());
		    	ff.printStackTrace(System.out);
		      return false;
		    } catch (Exception ex) {
		    	log.write("Unable to click on or type into element found in image file: "+ imageName);
		    	log.write(ex.getMessage());
		    	ex.printStackTrace(System.out);
		      return false;	      
		    }
		    return true;
		  }
}
