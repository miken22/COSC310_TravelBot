import static org.junit.Assert.*;

import org.junit.Test;


public class MyNewParserTest {

	@Test
	public void test() {
		String userInput = "A passenger plane has crashed shortly after take-off from Kyrgyzstan's capital, Bishkek, killing a large number of those on board. The head of Kyrgyzstan's civil aviation authority said that out of about 90 passengers and crew, only about 20 people have survived. The Itek Air Boeing 737 took off bound for Mashhad, in north-eastern Iran, but turned round some 10 minutes later.";
		
		MyNewParser p = new MyNewParser(userInput);
	}

}
