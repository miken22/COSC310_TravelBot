import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;


public final class openNLPparser{
	// Sentence tokenizer
	Tokenizer t;
	
	// Three different NER's to be used on text
	NameFinderME nf;
	NameFinderME of;
	NameFinderME lf;

	// Will hold tokenized sentence
	public String tokens[];

	public openNLPparser() throws InvalidFormatException, IOException{
	
		trainTokenizer();
		
		// Initilize NER's
		trainNF();
		trainOF();
		trainLF();
	}
	
	private void trainNF() throws InvalidFormatException, IOException{
		InputStream is;
        TokenNameFinderModel tnf;
        try {
            is = new FileInputStream("OpenNLP/en-ner-person.bin");
            tnf = new TokenNameFinderModel(is);
            this.nf = new NameFinderME(tnf);
        } catch (FileNotFoundException e) {
			e.printStackTrace();
        }
    }
    
    private void trainOF() throws InvalidFormatException, IOException{
		InputStream is;
        TokenNameFinderModel tnf;
        try {
            is = new FileInputStream("OpenNLP/en-ner-organization.bin");
            tnf = new TokenNameFinderModel(is);
            this.of = new NameFinderME(tnf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void trainLF() throws InvalidFormatException, IOException{
		InputStream is;
        TokenNameFinderModel tnf;
        try {
            is = new FileInputStream("OpenNLP/en-ner-location.bin");
            tnf = new TokenNameFinderModel(is);
            this.lf = new NameFinderME(tnf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void trainTokenizer(){
		InputStream is;
        TokenizerModel tm;
        try {
            is = new FileInputStream("OpenNLP/en-token.bin");
            tm = new TokenizerModel(is);
            this.t = new TokenizerME(tm);
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    public String findKeyWords(String userMessage){
		tokens = t.tokenize(userMessage);	
		return null;
    }
}