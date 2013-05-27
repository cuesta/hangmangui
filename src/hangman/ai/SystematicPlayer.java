package hangman.ai;

import java.util.List;

/**
 * AI player that iterates over a list of most frequently used letters in the English language.
 * @author Vera Coberley
 * May 25, 2013
 */

public class SystematicPlayer extends AbstractAIPlayer
{
	private char[] chars = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z' };
	private int charIndex=0;
	private List<Character> guessables;

	/**
	 * @param select
	 */
	public SystematicPlayer(List<Character> guessables)
	{
		super("Systematic");
		this.guessables = guessables;
	}

	/* (non-Javadoc)
	 * @see hangman.ai.AbstractAIPlayer#nextLetter()
	 */
	@Override
	public char nextLetter()
	{
		Character next = null;
		while(next == null)
		{
			if(charIndex >= chars.length)
			{
				throw new IllegalStateException("We've iterated through all letters!");
			}
			char candidate = chars[charIndex++];
			if(guessables.contains(candidate))
			{
				next = candidate;
			}
		}
		return next;
	}

}
