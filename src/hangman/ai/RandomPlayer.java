package hangman.ai;

import hangman.LetterSelector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RandomPlayer extends AbstractAIPlayer
{


	private List<Character> guessable;

	
	
	/**
	 * @param select
	 */
	public RandomPlayer(LetterSelector select, File letters) throws IOException
	{
		super(select);
		guessable = new ArrayList<Character>();
		readLetters(letters);
	}

	
	
	private void readLetters(File letters) throws IOException 
	{
		Scanner sc = new Scanner(letters);
	
		while (sc.hasNextLine()) 
		{
			String s = sc.nextLine();
			guessable.add(s.charAt(0));
			
		}	
	}

	
	
	@Override
	public char nextLetter()
	{
		if(guessable.isEmpty())
		{
			throw new IllegalStateException("We've consumed all guessable letters!");
		}
		Random rand = new Random();
		int characterIndex = rand.nextInt(guessable.size());
		char candidate = guessable.get(characterIndex);
		return candidate;
		
	}
}
