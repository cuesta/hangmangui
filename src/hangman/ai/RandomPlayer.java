package hangman.ai;

import java.util.*;

public class RandomPlayer extends AbstractAIPlayer

	private String word;
	private char[] guessed;
	private char[] guessable;
	int count;
	
	public void readLetters() 
	{
		Scanner sc = new Scanner("letters.txt");
		int count = 0;
		while (sc.hasNextLine()) 
		{
			String s = sc.nextLine();
			guessable[count] = s.charAt(0);
			count++;
		}	
	}

	public char randomPlayer()
	{
		while (true)
		{
			Random rand = new Random();
			int r = rand.nextInt(word.length());
			
			boolean alreadyGuessed = false;
			for (int i = 0; i < guessed.length; i++) 
			{
				if (guessable[i] == guessed[i]) 
				{
					alreadyGuessed = true;
					break;
				}
			
			
				if (!alreadyGuessed) 
				{
					guessed[count] = guessable[i];
					count++;
					return guessable[i];
				}
			}
		}

	}
	
	@Override
	public char nextLetter()
	{
		// returns the next letter in the alphabet and advances the index
		return chars[charIndex++];
	}
}
