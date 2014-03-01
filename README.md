COSC 310 Project
========================================================================================================

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
Assignment 3 
Individual Work:

Continuing with the work started by the group in assignment 2 (above), the program will begin to be improved by including a simple GUI and increasing the parsers ability by using the OpenNLP parsers (http://opennlp.apache.org/) . This will allow the agent to handle conversations more dynamically, creating responses based on user input even when the agent has no knowledge of what the user has said.

Added Features:

GUI Interface:
A simple graphical user interface has been added to the system to provide the user with a more realistic chat environment. The GUI stores all text from the conversation to allow the user to look back at previous responses, and WILL also have a feature added to export the chat history. This feature could be used by the user to save conversations to look back on later, or to be used by the agent as additional learning data.

Implement OpenNLP: 
OpenNLP NER and POS Tagger have been added to the system to try and generate more realistic conversations. The primary way that this is done is by parsing the sentence and flagging parts of the input that might represent nouns. The system converts the text to title case (from mike to Mike for example) to create a syntactically correct sentence. Then the OpenNLP Named Entity Recognition parser compares the input to its learned vocabulary. This way the agent can recognize words that are not explicitly coded in its dictionary and still generate responses. An example would be if the user asks about going to Paris, although the system does not know the word explicitly, the NER flags Paris as a location and stores the input as a "bad location" type. The agent can then use the input to build a sentence that explains to the user that it cannot help with going to Paris.

Improved Spell Checking: 
The original system had a Fuzzy Pattern Matching algorithm to try and identify user spelling mistakes. As mentioned above, the OpenNLP POS Tagger has added another level of spell checking allowing the agent to recognize extra words from its learning set for people, organizations and places.

Second Conversation Topic:
On top of the current conversation the agent now handles organizing vacations to BC's interior and parts of Alberta. On top of using the same framework to generate similar conversation styles to the Mexico conversations, further additions to the Location class allow for more detailed Google searches for activities around the destination. 
========================================================================================================
To Do:

- Improve GUI
- 30 turn sample dialogue
- Video explaining project work so far.