import java.io.*;

/**
 * This class will be used to write the text from the conversation to textfile.
 * Code adapted from the IORW class from A2
 * 
 * 
 * @author Mike Nowicki
 *
 */
public class SaveText {
    private static PrintStream outputStream = System.out;
	public static void saveConvo(String name, String text) throws FileNotFoundException{
		String path = Utils.getExecutingPath() + name;
		outputStream = new PrintStream(path);
        outputStream.print(text + "\r\n");
	}
}
