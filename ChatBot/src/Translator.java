import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Translate user text to the language in the area. User
 * must provide the text in double quotation marks so the
 * agent can identify what to translate.
 * 
 * @author Mike Nowicki
 * 
 */

public class Translator {

	public static String translate(String input, boolean tropicDest){

	    Translate.setClientId("COSC310_ChatBot");
        Translate.setClientSecret("4AfHA6BdU2T459w7+PEFyN4XdLbf5eGf22R02tkwnis=");
        
        String translatedText = "";
        if(tropicDest){
        	try {
        		translatedText = "The Spanish translation is: ";
				translatedText += Translate.execute(input, Language.ENGLISH, Language.SPANISH);
			} catch (Exception e) {}
        } else {
			try {
				translatedText = "The French translation is: ";
				translatedText += Translate.execute(input, Language.ENGLISH, Language.FRENCH);
			} catch (Exception e) {}
        }
		return translatedText;
	}
	
}