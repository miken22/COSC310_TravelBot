import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * This class uses the OpenNLP NER and POS Taggers
 * to help identify other potential keywords or phrases
 * said by the user. Each word also get tagged as it's part of speech (POS) in the sentence. 
 * Words that appear to be formal nouns are capitalized and checked 
 * against the NER and Parser Dictionary to add a further level of spell-checking and to
 * allow the agent to handle more cases then it is explicitly programmed to do.
 * 
 * 
 * @author Mike Nowicki
 *
 */

public class Parser {
	
	// Sentence tokenizer
	private Tokenizer t;
 	
 	// Three different NER's to be used on text
 	private NameFinderME nf;
 	private NameFinderME of;
 	private NameFinderME lf;
 	private POSTaggerME tagger;
	private String[] tokens;
	
	public Parser() throws InvalidFormatException, IOException{
		trainTokenizer();
		trainTagger();
		// Initialize NER's
		trainNF();
		trainOF();
		trainLF();
	}
	
	public void tagSentence(String userMessage){
		
		// OpenNLP tokenization of sentence. To catch pos and named entities that have not been explicitly coded
        // in the parser dictionary.
        tokens = t.tokenize(userMessage);
        
        String[] taggedString = tagger.tag(tokens);
		
        // This checks all parts of speech. If something appears like it could be a person, place, organization
        // name then it forces the formatting to be first letter upper cased so the NE finders can try to match
        // the object.
        for(int i = 0; i < taggedString.length; i++){
//        	System.out.print(tokens[i]+ ", "+ taggedString[i] + " ");
        	if(taggedString[i].equals("NNP") || taggedString[i].equals("NN") || taggedString[i].equals("POS")){
        		tokens[i] = StringUtils.toTitleCase(tokens[i]);
//        		System.out.println(tokens[i]); // to see what is happening
        	}
        }
        
	}

	public String getUserMessage(){
    	StringBuilder sb = new StringBuilder();
    	for(String s:tokens){
    		sb.append(s + " ");
    	}
    	return sb.toString();
    }
	
	// All have been added for Assignment 3
    public String findDest(){
    	Span d[] = lf.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i] + " ");
    	}
    	return fs.toString();
    }
    
    public String findNames(){
    	Span d[] = nf.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i]);
    	}
    	return fs.toString();
    }
    
    public String findOrgs(){
    	Span d[] = of.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i] + " ");
    	}
    	return fs.toString();
    }   
    // These methods all train the appropriate NER part of the parser, and the tokenizer
	private void trainNF() throws InvalidFormatException, IOException {
		InputStream is = this.getClass().getResourceAsStream("en-ner-person.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        nf = new NameFinderME(tnf);
    }
    
    private void trainOF() throws InvalidFormatException, IOException{
    	InputStream is = this.getClass().getResourceAsStream("/en-ner-organization.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        of = new NameFinderME(tnf);
    }
    
    private void trainLF() throws InvalidFormatException, IOException{
    	InputStream is = this.getClass().getResourceAsStream("/en-ner-location.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        lf = new NameFinderME(tnf);
    }
    
    private void trainTokenizer() throws InvalidFormatException, IOException{
    	InputStream is = this.getClass().getResourceAsStream("/en-token.bin");
        TokenizerModel tm = new TokenizerModel(is);
        t = new TokenizerME(tm);
    }
    
    private void trainTagger(){
    	POSModel model = new POSModelLoader().load(new File(this.getClass().getResource("/en-pos-maxent.bin").getFile()));
    	tagger = new POSTaggerME(model);
    }
}