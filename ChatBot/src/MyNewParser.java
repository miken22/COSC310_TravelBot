import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.process.*;
import edu.stanford.nlp.tagger.maxent.*;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.*;


public class MyNewParser {

	
	public MyNewParser(String userInput){
		StringReader sr = new StringReader(userInput);
		
		MaxentTagger tagger = new MaxentTagger(userInput);
	    TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
										   "untokenizable=noneKeep");
	   	DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(sr);
	   	documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);
	   	
	   	List<HasWord> sent = Sentence.toWordList("The", "slimy", "slug", "crawled", "over", "the", "long", ",", "green", "grass", ".");
	    
	   
	   	List<TaggedWord> taggedSent = tagger.tagSentence(sent);
	   	for (TaggedWord tw : taggedSent) {
	   		if (tw.tag().startsWith("JJ")) {
	   			System.out.println(tw.word());
	   		}
	   	}
	}
}