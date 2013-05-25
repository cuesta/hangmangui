package hangman.ai;

import hangman.LetterSelector;

/**
 * "Dumb" AI player that simply "guesses" letters from a-z based on alphabetical order.
 * @author Vera Coberley
 * May 25, 2013
 */

public class SimplisticPlayer extends AbstractAIPlayer
{
	private char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private int charIndex=0;

	/**
	 * @param select
	 */
	public SimplisticPlayer(LetterSelector select)
	{
		super(select);
	}

	/* (non-Javadoc)
	 * @see hangman.ai.AbstractAIPlayer#nextLetter()
	 */
	@Override
	public char nextLetter()
	{
		// returns the next letter in the alphabet and advances the index
		return chars[charIndex++];
	}

}
