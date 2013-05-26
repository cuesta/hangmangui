package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Provides the next random word from the word list, assuming it matches our "valid word" criteria.
 * May 26, 2013
 */
public class WordSupplier 
{
	
	private int wordCount;
	private File wordFile;
	
	public WordSupplier(String filename) throws IOException
	{
		// determine how many lines there are in the file, so we can randomly pick one
		wordFile = new File(filename);
    	BufferedReader br = new BufferedReader(new FileReader(wordFile));
	    while (br.readLine()!=null)  
	    {
	    	wordCount++;
	    }
	    br.close();
	    System.out.println("Number of words: "+wordCount);
	}
	
	/** Returns the next random word.*/
	public String secretWord() throws IOException
	{
		String randomWord = null;
		// outer loop provided in case last few lines of the word input list provide invalid content
		while(randomWord == null)
		{
			BufferedReader br = new BufferedReader(new FileReader(wordFile));
	
			int nextLine = new Random().nextInt(wordCount) + 1;
	
			int lineCount = 0;
			String line = null;
			
			while ((line = br.readLine()) != null && randomWord == null)
			{
				lineCount++;
				if (lineCount >= nextLine)
				{
					if (line.length() > 1
							&& line.matches(HangmanLogic.REGEX_VALID))
					{
						randomWord = line;
					}
				}
			}
			br.close();
		}
		System.out.println("random word is: "+randomWord);
		return randomWord;
	}
	


}
