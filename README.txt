COSC 310 Project
=============================================================================================

Team Members:

	Allan Tsai
	Brett Dupree
	Manny Haller
	Mike Nowicki
	
Project Description: 
Develop an interactive conversational travel agent that responds to user questions using Java. The user can ask the chatbot any question that is related to the trip such as weather of the destination, cost of the vacation, and transportation for the trip, etc. 
	 
=============================================================================================

FILES/FOLDERS DESCRIPTION

The Program class controls the ChatBot program. The class handles user input, which can be provided by Text File or in the Console. 

The input is then passed to the Parser class to determine what type of "input" the user provided. The parseUserMessage method cleans up the input and tokenizes the string. It then runs through all the possible input cases to determine what the user said. It does this by comparing words in the string to keywords that are defined in the ParsedInputDictionary class. If the input matches one of the cases the appropriate method sets the type of input, according the the lists defined in the ParsedInputType class.

The input is matched to keywords using a fuzzy substring distance measure based on levenshtein distance. The most closely matched keyword is the one we assume the user meant.

The input type is then passed to the TravelAgent class, which again uses cases to know what input was provided by the user. When a matching case is found the ResponseMaker class is called and the appropriate method starts to build a response. The responses are built in a few different ways. For trivial statements, such as things that the agent cannot handle, the ResponseMaker class will return the message coded into the method. The Responses class holds lists of more complex responses. The class uses a RNG to pick one of the responses from the correct list, and if necessary replaces keywords (such as <Dest>) with stored user input. The final way that responses are built is using the Location class. When the user provided a destination, the Location class uses the Google API's to query information that might be relevant to a tourist. This class finds distances between places, temperatures, estimates costs, and find locations based on user keywords (such as "food" to find restaurants"). Once the ResponseMaker has built the string it returns the information to the TravelAgent class to display for the user.

The org.json package has classes built by Douglas Crockford (https://github.com/douglascrockford/JSON-js) to allow the prorgam to interact with the Google API's.

The way that this program is run is by running the Executable JAR File. This file depends on the en-pos-maxent.bin file as well. Follow these steps to run the program:

1. Download the ChatBot.jar file and en-pos-maxent.bin file from GitHub in the Executable Folder. 

2. Save the two files to the same location on your computer.

3. Double click the ChatBot.jar file. The tagger will need 1-2 seconds to load before the application fully launches.

=================================================================================================
Assignment 3 
Individual Work:

Continuing with the work started by the group in assignment 2 (above), the program will begin to be improved by including a simple GUI and increasing the parsers ability by using the OpenNLP parsers (http://opennlp.apache.org/) . This will allow the agent to handle conversations more dynamically, creating responses based on user input even when the agent has no knowledge of what the user has said.

Added Features:
Second Conversation Topic:
On top of the current conversation the agent now handles organizing vacations to BC's interior and parts of Alberta.

GUI Interface:
A simple graphical user interface has been added to the system to provide the user with a more realistic chat environment. The GUI stores all text from the conversation to allow the user to look back at previous responses, and WILL also have a feature added to export the chat history. This feature could be used by the user to save conversations to look back on later, or to use for the agent to build more responses.

Implement OpenNLP: 
OpenNLP NER and POS Tagger have been added to the system to try and generate more realistic conversations. The primary way that this is done is by parsing the sentence and flagging parts of the input that might represent nouns. The system converts the text to title case (from mike to Mike for example) to create a syntactically correct sentence. Then the OpenNLP Named Entity Recognition parser compares the input to its learned vocabulary. This way the agent can recognize words that are not explicitly coded in its dictionary and still generate responses. An example would be if the user asks about going to Paris, although the system does not know the word explicitly, the NER flags Paris as a location and stores the input as a "bad location" type. The agent can then use the input to build a sentence that explains to the user that it cannot help with going to Paris.
Examples:
User: I want to go to Toronto
Travel Bot: Our guide stopped going to Toronto a while ago, sorry.
User: Can I go to Paris?
Travel Bot: Sorry, I do not think that we arrange trips to Paris. 

Improved Spell Checking: 
The original system had a Fuzzy Pattern Matching algorithm to try and identify user spelling mistakes. This lets the agent attempt to understand the users sentence by comparing a possible misspelled word against words in its dictionary. Using the new features with OpenNLP incorrectly capitalized named entities also get flagged and allow the agent to respond as shown below:
Examples:
User: I want to go to revelstok
Travel Bot: Everybody loves Revelstoke!
User: Where can I go sking
Travel Bot: One of the best resorts in the area, Revelstoke mountain will provide you with an excellent challenge.
User: what's the weathr like
Travel Bot: It is currently -4.59 degrees C in Revelstoke with broken clouds.

More Dynamic Response Building:
Building upon the framework of the Location class developed in Assignment 2 the program has been modified to allow for more realistic answers by using the Google APIs to return true search results. This is most commonly used for search for restaurants and hotel locations. The agent will try and perform a Google search on the keyword and if there are any results it will pick a result to use in building a response for the user.
Examples:
User: I can afford $60 a night.
Travel Bot: Searching for the best accommodations that match you budget. 
In that price range there is the Sandman Hotel Revelstoke for only $55 a night.
User: are there good restaurants there?
Travel Bot: Determining what's best for you. 
A very popular place is The Village Idiot.
In the below case this is how the agent handles a search with no valid results:
User: are there any popular restaurants?
Travel Bot: Well let me find out... There may be some local places, but nothing outstanding.

Response Handling:
The agent now has a more refined method to handle inputs that it does not know the answer to. While the agent has the basics to generate a message that it does not know how to respond, other features have been added to try and let the agent use the users input to build a more realistic response as shown below:
Examples:
This example shows a response generated by the agent when the user asks a question before specifying a destination.
User: What's the weather there?
Travel Bot: Sorry, you have to decide where you want to go before we can talk about weather.
This example (from above) also shows how the agent uses the user's input to create a more realistic response.
User: Can I go to Paris?
Travel Bot: Sorry, I do not think that we arrange trips to Paris. 
This example demonstrates the agent handling a question that is not related to being in the interior.
User: can i go on a cruise?
Travel Bot: It's a little hard to go on a cruise when you're in Canada's Interior. I can redirect you to our Alaskan Cruise Line Partners if you'd like.


=============================================================================================
To Do:
- Video explaining project work so far.