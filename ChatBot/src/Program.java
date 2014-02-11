import java.util.*;

public final class Program {
    private static TravelAgent agent;
    private static String botName = "Travel Bot";
    private static int turnCount = 0;

    public static void main(String[] args) {
        readArguments(args);
        startBot();
    }

    // Reads the program arguments for automated conversation input/output files
    private static void readArguments(String[] args) {
        String executingPath = Utils.getExecutingPath();
        String inputFilePath = "";
        String outputFilePath = "";

        for (String arg : args) {
            if (arg.contains("=")) {
                int indexOfEquals = arg.indexOf('=');

                String option = arg.substring(0, indexOfEquals).trim();
                String value = arg.substring(indexOfEquals + 1).trim();

                if (value.isEmpty()) {
                    displayInvalidArgument(arg);
                    continue;
                }

                switch (option) {
                    case "in":
                        inputFilePath = executingPath + value;
                        break;
                    case "out":
                        outputFilePath = executingPath + value;
                        break;
                    default:
                        displayInvalidArgument(arg);
                }
            } else {
                displayInvalidArgument(arg);
            }
        }

        setupStreams(inputFilePath, outputFilePath);
    }

    private static void displayInvalidArgument(String arg) {
        IORW.writeLine("Invalid argument: '" + arg + "'");
    }

    // Redirect IO if input/output files are given
    private static void setupStreams(String inputFilePath, String outputFilePath) {
        try {
            if (!StringUtils.isNullOrEmpty(inputFilePath)) {
                IORW.setInputFile(inputFilePath, true);
            }

            if (!StringUtils.isNullOrEmpty(outputFilePath)) {
                IORW.setOutputFile(outputFilePath, true);
            }
        } catch (Exception e) {
            IORW.writeLine("Error opening input file. Message: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void startBot() {
        try {
            agent = new TravelAgent();
            IORW.writeLine("TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google");
            writeFromBot(agent.getResponseMaker().getGreeting(null));
            readParsePrintLoop();
        } catch (Exception e) {
            IORW.writeLine("\r\nAn unhandled exception occurred.\r\n" + e.getMessage() + "\r\n" + Utils.getStackTrace(e));
            System.exit(1);
        }
    }

    private static void readParsePrintLoop() {
        String userInput = "";
        String response = "";

        while (true) {
            // Read from console
            userInput = readLine();

            // Parse input
            ParsedInput parsedInput = Parser.parseUserMessage(userInput);

            // Send parsed messaged to agent
            response = agent.getResponse(parsedInput);

            // Display response to user
            writeFromBot(response);

            turnCount++;
        }
    }

    private static String readLine() {
        String line = "";

        try {
            line = IORW.readLine();
        } catch (NoSuchElementException e) {
            IORW.writeLine("{End of input stream}");
            System.exit(0);
        }

        return line;
    }

    private static void writeFromBot(String message) {
        // We want to display the username if it's available
        String user = "User";
        if (!StringUtils.isNullOrEmpty(agent.savedInputs.get("username"))) user = agent.savedInputs.get("username");

        if (IORW.debugOn) {
            IORW.writeLine("\r\n" + turnCount + " - " + botName + ":\r\n" + message + "\r\n\r\n" + user + ":");
        } else {
            // Write out our response with header & footer
            IORW.writeLine("\r\n" + botName + ":\r\n" + message + "\r\n\r\n" + user + ":");
        }
    }
}