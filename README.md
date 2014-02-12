========================================================================================================
COSC310project

Team Members:

	Allan Tsai
	Brett Dupree
	Manny Haller
	Mike Nowicki
	
Project Description: 
        Develop an interactive conversational travel agent that responds to user questions using Java. The user can ask the chatbot any question that is related to the trip such as weather of the destination, cost of the vacation, and transportation for the trip, etc. 
	 
========================================================================================================

FILES/FOLDERS DESCRIPTION

The Program class controls the ChatBot program. The class handles user input, which can be provided by Text File or in the Console. 

The input is then passed to the Parser class to determine what type of "input" the user provided. The parseUserMessage method cleans up the input and tokenizes the string. It then runs through all the possible input cases to determine what the user said. It does this by comparing words in the string to keywords that are defined in the ParsedInputDictionary class. If the input matches one of the cases the appropriate method sets the type of input, according the the lists defined in the ParsedInputType class.

The input is matched to keywords using a fuzzy substring distance measure based on levenshtein distance. The most closely matched keyword is the one we assume the user meant.

The input type is then passed to the TravelAgent class, which again uses cases to know what input was provided by the user. When a matching case is found the ResponseMaker class is called and the appropriate method starts to build a response. The responses are built in a few different ways. For trivial statements, such as things that the agent cannot handle, the ResponseMaker class will return the message coded into the method. The Responses class holds lists of more complex responses. The class uses a RNG to pick one of the responses from the correct list, and if necessary replaces keywords (such as <Dest>) with stored user input. The final way that responses are built is using the Location class. When the user provided a destination, the Location class uses the Google API's to query information that might be relevant to a tourist. This class finds distances between places, temperatures, estimates costs, and find locations based on user keywords (such as "food" to find restaurants"). Once the ResponseMaker has built the string it returns the information to the TravelAgent class to display for the user.

The org.json package has classes built by Douglas Crockford (https://github.com/douglascrockford/JSON-js) to allow the prorgam to interact with the Google API's.

The best way to run the application is by executing the JAR file. The JAR file is located in the ~/sim_imput/ChatBot folder.
After it has been downloaded do the following:

1. Open Command Prompt and navigate to the folder location when the JAR was downloaded.

2. Type: java -jar ChatBot.jar

========================================================================================================
Assignment 3 Additions

Continuing with the work started by the group in assignment 2 (above), the program will begin to be improved by including a simple GUI and increasing the parsers ability by using the OpenNLP parsers (http://opennlp.apache.org/) . This will also allow for better handling of misspelled words, especially proper nouns that are not explicitly defined in the ParserDictionary class. This will allow the agent to handle conversations more dynamically, creating responses based on user input even when the agent has no knowledge of what the user has said.

========================================================================================================
To Do:

- Improve GUI
- Incorporate OpenNLP for more dynamic conversations
- Level 0 DFD of system
- Level 1 DFD of system
- Explanations of features added
- 30 turn sample dialogue
- Video explaining project work so far.
