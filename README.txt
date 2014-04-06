COSC 310 Project
=============================================================================================
Team Members for A1 and A2:
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

The way that this program is run is by running the Executable JAR File. The file depends on the en-pos-maxent.bin file as well. Follow these steps to run the program:

1. Download the ChatBot.jar file and en-pos-maxent.bin file from GitHub in the Executable Folder. 

2. Save the two files to the same location on your computer.

3. Double click the ChatBot.jar file. The tagger will need 1-2 seconds to load before the application fully launches.

=================================================================================================
Assignment 3 - Michael Nowicki
Individual Work:

Continuing with the work started by the group in assignment 2 (above), the program will begin to be improved by including a simple GUI and increasing the parsers ability by using the OpenNLP parsers (http://opennlp.apache.org/) . This will allow the agent to handle conversations more dynamically, creating responses based on user input even when the agent has no knowledge of what the user has said.

Added Features:
Second Conversation Topic:
On top of the current conversation the agent now handles organizing vacations to different towns in BC's interior and parts of Alberta. The agent is also able to handle topic changes, going from planning a vacation in BC to arranging a trip in Mexico.

GUI Interface:
A simple graphical user interface has been added to the system to provide the user with a more realistic chat environment with a non-generic font. The GUI stores all text from the conversation to allow the user to look back at previous responses, and WILL also have a feature added to export the chat history. This feature could be used by the user to save conversations to look back on later, or to use for the agent to build more responses.

Implement OpenNLP: 
OpenNLP NER and POS Tagger have been added to the system to try and generate more realistic conversations. The primary way that this is done is by parsing the sentence and flagging parts of the input that might represent nouns. The system converts the text to title case (from mike to Mike for example) to create a syntactically correct sentence. Then the OpenNLP Named Entity Recognition parser compares the input to its learned vocabulary. This way the agent can recognize words that are not explicitly coded in its dictionary and still generate responses. An example would be if the user asks about going to Paris, although the system does not know the word explicitly, the NER flags Paris as a location and stores the input as a "bad location" type. The agent can then use the input to build a sentence that explains to the user that it cannot help with going to Paris.
Examples:
User: I want to go to toronto
Travel Bot: Our guide stopped going to Toronto a while ago, sorry.
User: Can I go to Paris?
Travel Bot: Sorry, I do not think that we arrange trips to Paris. 

Improved Spell Checking: 
The original system had a Fuzzy Pattern Matching algorithm to try and identify user spelling mistakes. This lets the agent attempt to understand the users’ sentence by comparing a possible misspelled word against words in its dictionary. Using the new features with OpenNLP incorrectly capitalized named entities also get flagged and allow the agent to respond as shown below:
Examples:
User: I want to go to revelstok
Travel Bot: Everybody loves Revelstoke!
User: Where can I go sking
Travel Bot: One of the best resorts in the area, Revelstoke mountain will provide you with an excellent challenge.
User: what's the weathr like
Travel Bot: It is currently -4.59 degrees C in Revelstoke with broken clouds.

Dynamic Response Building:
Building upon the framework of the Location class developed in Assignment 2 the program has been modified to allow for more realistic answers by using the Google APIs to return true search results. This is used automatically for search for restaurants and hotel locations. The agent has also be provided with a list of the most relevant keywords for Google's Nearby Search feature which will look for locations that match the type within a given area. The agent will try and perform a Google search on the keyword and if there are any results it will pick a result to use in building a response for the user.
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
User: is there a library?
Travel Bot: Yes, I've found a place called 'Millarville Community Library' that might be just what you're looking for. 

Input Handling:
The agent now has a more refined method to handle inputs that it does not know the answer to. While the agent has the basics to generate a message that it does not know how to respond, the following features have been added to improve the agent by using the users input to build a more realistic response as shown below:
Examples:
This example shows a response generated by the agent when the user asks a question before specifying a destination.
User: What's the weather there?
Travel Bot: Sorry, you have to decide where you want to go before we can talk about weather.
This example demonstrates how the agent handles answeing a question that it cannot find the answer to.
User: is there a library?
TravelBot: Sorry, I could not find anything matching 'library', would you like to look for something else?
This example demonstrates the agent handling a question that is not related to being in the interior.
User: can i go on a cruise?
Travel Bot: It's a little hard to go on a cruise when you're in Canada's Interior. I can redirect you to our Alaskan Cruise Line Partners if you'd like.

=================================================================================================
Final Project - Michael Nowicki

Added APIs:
Google Geocoding:
The API is used to encode addresses and cities as latitude and longitude coordinates. The API is not directly used in the conversation, however it provides all the functionality for the Google Places and Google Directions APIs.

Google Directions:
This API is used to help guide the user to different places in the destination the conversation is about. After the user gets a location from the Google Nearby Search API the address and place are stored. The user may then ask about how to get to that place, and along with the Geocoding API the agent is able to provide a complete list of directions to that destination.
Example:
Travel Bot: Hi! Welcome to our travel center. We help arrange trips to Mexico and ski resorts in BC and Alberta. How can I help you today?
User: I want to go to Revelstoke.
...
User: is there a library?
TravelBot: You could try 'Okanagan Regional Library Revelstoke Branch', it looks perfect for you.
User: how can i get there?
TravelBot: Here are the directions to Okanagan Regional Library Revelstoke Branch:
Head east on University Way toward Alumni Ave for 0.5 km.
At the roundabout, continue straight to stay on University Way for 0.2 km.
Turn left onto BC-97 N for 45.8 km.
Continue straight to stay on BC-97 N for 3.6 km.
Continue onto Vernon-Sicamous Hwy/BC-97A N for 65.5 km.
Turn right onto BC-1 E/Trans-Canada Hwy for 70.5 km.
Turn right onto Victoria Rd (signs for City Centre) for 1.8 km.
Turn right onto Campbell Ave for 0.3 km.
Your destination is at 605 Campbell Ave, Revelstoke.
Total distance is 188 km and will take 2 hours 35 mins to drive there.

Bing Translate:
This API is used to help the user learn simple sentences before going to their destination. If the conversation is about a vacation to Mexico the agent can help the user learn key phrases in Spanish. If the destination is in Canada the agent can perform the same help but in French. 
Example:
Travel Bot: Hi! Welcome to our travel center. We help arrange trips to Mexico and ski resorts in BC and Alberta. How can I help you today?
User: I want to go to Revelstoke.
...
User: how do I say where is the restaurant?
TravelBot: The French translation is: où se trouve le restaurant ?

User: I want to go to tijuana
TravelBot: Tijuana is a wonderful place!
...
User: how do I say where is the bathroom?
TravelBot: The spanish translation is: ¿Dónde está el baño?

Wikipedia API:
This API is used to help provide the user with more information about there destination. The agent can query Wikipedia for some general information about the destination and returns a cleaned summary of their information.
Example:
User: I want to go to Juarez
TravelBot: Juarez is very nice!
User: what can you tell me about juarez?
TravelBot: Ciudad Juárez (Spanish pronunciation: [sjuˈðad ˈxwaɾes]), known in the past as Paso del Norte (Northern Pass), and commonly referred to by locals as simply Juárez, is a city and seat of the municipality of Juárez in the Mexican state of Chihuahua. Juárez's estimated population is 1.5 million people. The city lies on the Rio Grande (Río Bravo del Norte), south of El Paso, Texas. Ciudad Juárez and El Paso comprise the second largest bi-national metropolitan area on the Mexico-United States border (after San Diego–Tijuana), with a combined population of over 2.7 million people.
Source: Wikipedia

Shared APIs:
Location API - Performs Google Nearby Searches, Geocoding, and Directions. Parses the returned JSON object so you work with cleaned String object instead that are formatted for printing to the user.

Fuzzy Matching - Algorithm for performing spell checking. Compares each word against a stored dictionary to help catch user spelling mistakes.

Save Conversations - Save the text history to a text file on the users computer. Can be used to store conversations to use later, or if the user wants to keep a record of the conversations with the agent.

Regular Expressions - Provides you with the ability to parse the input sentences against regular expressions. Allows for simple tokenizing of the sentences, with the ability to look for specific patterns within the input.