/**
 * This class will act as the main program, passing
 * user input to be parsed and returning responses.
 * All inputs/outputs are then returned to GUI for display.
 * 
 * @author Mike-Laptop
 *
 */
public class ChatAgent {
	
	private TravelAgent agent;
    private String botName = "Travel Bot";
    private String user = "User";
    
	public ChatAgent(){
		agent = new TravelAgent();
	}
	
	public String buildResponse(String input) {
        String message = "";

        ParsedInput parsedInput = Parser.parseUserMessage(input);
        
        message = agent.getResponse(parsedInput);

        // Write out our response with header & footer
        String response = "\r\n\r\n" + botName + ":\r\n" + message + "\r\n\r\n";
    
        return response;

    }
}