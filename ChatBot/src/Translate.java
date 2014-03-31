import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Translate user text to the language in the area. User
 * must provide the text in double quotation marks so the
 * agent can identify what to translate.
 * 
 * @author Mike Nowicki
 * 
 */

public class Translate {

	public static String translation(String input){
		
		String url = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate?appId=3GGTHYZ1au13mskBtrTDIS2fb/odz562zzQJAK22e3E=&from=en&to=es&text=" + input;
		Scanner scan;
		try {
			scan = new Scanner(new URL(url).openStream());
			String str = new String();
	        while (scan.hasNext()) {
	            str += scan.nextLine() + "\n";

	        }
	        scan.close();
	        System.out.println(str);
//	        JSONObject json = new JSONObject(str);
//	        return json.toString();
	        return null;
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}
}