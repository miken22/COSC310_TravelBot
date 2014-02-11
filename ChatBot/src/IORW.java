import java.io.*;
import java.nio.file.Paths;
import java.util.*;

// IORW = InputOutputReaderWriter
// Default IO go through System.in/out, but also allows IO to/from files
// This enables us to run a conversation and record the output
public final class IORW {
    private static Scanner inputScanner = new Scanner(System.in);
    private static PrintStream outputStream = System.out;
    private static boolean writeToSystemOut = false;
    private static boolean forwardInputToOutput = false;
    static boolean debugOn = false;

    public static void setInputFile(String inputFilePath, boolean forwardInputToOutput) throws IOException {
        inputScanner = new Scanner(Paths.get(inputFilePath));
        IORW.forwardInputToOutput = forwardInputToOutput;
    }

    public static void setOutputFile(String outputFilePath, boolean writeToSystemOut) throws FileNotFoundException {
        outputStream = new PrintStream(outputFilePath);
        IORW.writeToSystemOut = writeToSystemOut;
    }

    public static String readLine() throws NoSuchElementException {
        String line = inputScanner.nextLine();
        if (forwardInputToOutput) writeLine(line);
        return line;
    }

    public static void writeLine(String x) {
        outputStream.print(x + "\r\n");
        if (writeToSystemOut) System.out.println(x);
    }

    public static void debug(String x) {
        if (debugOn) writeLine(x);
    }
}