package hangman.ai;

import java.util.*;

/** Represents an AI players that simply selects random letters that are "approved" 
 * and haven't been guessed yet.
 * May 26, 2013
 */
public class RandomPlayer extends AbstractAIPlayer
{
	/**
	 * @param select
	 */
	public RandomPlayer(List<Character> guessables) 
	{
		super("Random Player");
		guessableOrig = guessables;
		initialize();
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
		// retrieve the random character, and remove it from the list so we don't use it again
		char candidate = guessable.remove(characterIndex);
		return candidate;
	}

	
}

