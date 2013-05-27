package hangman.ai;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import hangman.HangmanLogic;
import hangman.event.LetterGuessedEvent;

/**
 * A strategic player that attempts to find the correct word using the following approach:
 * 1.  For the first five letters, "guess" the most frequently used letters in the English language.
 * 2.  Based on the overall length of the phrase, locate a pre-parsed list of words that has that same word length.
 * 3.  Based on the frequency of the five letters in the guessable phrase, further narrow down the words from the word file, which is
 *     marked up with the same frequency pattern. (In hindsight, I'm not sure the pre-parsing and encoding was necessary - I should have run 
 *     some performance tests first going against the full file.  I just assumed it was going to be slow).
 * 4.  From there on, choose as-of-yet not guessed letters from the candidate words to further narrow down the word list.
 * 
 * Note that this strategic player does not read in the letter file - it relies on a regular expression instead.  In an ideal world,
 * we'd determine a regular expression based on the letters in the input file, or else, allow the file to contain the actual regular expression
 * instead of individual letters.
 * 
 * @author Vera Coberley
 * May 25, 2013
 */

public class CleverPlayer extends AbstractAIPlayer
{
	private static final String FILE_SUFFIX = "_words_sorted.txt";
	protected char[] frequentLetters = {'e','t','a','o','i'};
	protected char[] sortedFrequentLetters;
	protected int frequentLetterIndex;
	protected List<String> wordCandidates;
	protected List<Character> guessedCharacters;
	
	private StringBuilder etaoiCount;
	private String currentRegex;
	private File resourcesDir;
	
	/**
	 * @param select
	 */
	public CleverPlayer(File resourcesDir)
	{
		super("Clever Player");
		this.resourcesDir = resourcesDir;
		sortedFrequentLetters = Arrays.copyOf(frequentLetters, frequentLetters.length);
		Arrays.sort(sortedFrequentLetters);
		initialize();
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		guessedCharacters = new ArrayList<Character>();
		etaoiCount = new StringBuilder(5);
		wordCandidates = null;
	}
	
	/* (non-Javadoc)
	 * @see hangman.ai.AbstractAIPlayer#reset()
	 */
	@Override
	public void reset()
	{
		initialize();
	}

	/* (non-Javadoc)
	 * @see hangman.ai.AbstractAIPlayer#nextLetter()
	 */
	@Override
	public char nextLetter()
	{
		if(frequentLetterIndex<frequentLetters.length)
		{
			return frequentLetters[frequentLetterIndex++];
		} 
		else 
		{
			if (wordCandidates == null)
			{
				loadWordCandidates(currentRegex);
			}
			return nextStrategicLetter();
		}
	}
	
	/** Reacts to a LetterGuessedEvent.*/
	public void onLetterGuessed(final LetterGuessedEvent eve) {
		guessedCharacters.add(eve.getLetter());
		if(Arrays.binarySearch(sortedFrequentLetters, eve.getLetter()) >=0)
		{
			etaoiCount.append(getLetterCount(eve.getLetter(), eve.getLogic().getKnownKeyPhrase()));
		} 
		currentRegex = eve.getLogic().getKnownKeyPhrase().replace(HangmanLogic.WILDCARD, '.');
		if(!eve.isSuccess())
		{
			filterCandidateListAgainstUnmatchedChar(eve.getLetter());
		}
		else
		{
			filterCandidateListAgainstKnownKeyPhrase();
		}
		super.onLetterGuessed(eve);
	}
	
	/** If the letter we guessed was not contained in the target word, remove any candidate words that contain that 
	 * letter as they obvioulsy are not the target word.
	 * @param unmatched
	 */
	private void filterCandidateListAgainstUnmatchedChar(char unmatched)
	{
		if (wordCandidates == null)
		{
			return; // list hasn't been loaded yet - not needed
		}
		Iterator<String> it = wordCandidates.iterator();
		String chStr = Character.toString(unmatched);
		while(it.hasNext())
		{
			String word = it.next();
			if(word.contains(chStr))
			{
				it.remove();
			}
		}
	}
	
	/** If the letter we guessed was contained in the target word, use the knowledge of the position of this letter in the target word via the
	 * 'curren key phrase' (target key phrase with dash substitutes for as-of-yet unguessed characters.)
	 */
	private void filterCandidateListAgainstKnownKeyPhrase()
	{
		if(wordCandidates!=null)
		{
			Iterator<String> it = wordCandidates.iterator();
			while(it.hasNext())
			{
				String word = it.next();
				if(!word.matches(currentRegex))
				{
					it.remove();
				}
			}
		}
	}
	
	/** Determine how many times a given character is found in a given word.*/
	private int getLetterCount(char target, String word)
	{
		int count =0;
		for(char c : word.toCharArray())
		{
			if(c == target)
			{
				count++;
			}
		}
		return count;
	}
	
	/** Iterate over candidate words, looking for letters we haven't guessed yet.*/
	private char nextStrategicLetter()
	{
		for (String word : wordCandidates)
		{
			for (char c : word.toCharArray())
			{
				if(!guessedCharacters.contains(c))
				{
					return c;
				}
			}
		}
		throw new IllegalStateException("We appear to have run out of letters to guess");
	}
	
	/** Loading the marked-up, word-length file, and retrieving candidate words. */
	private void loadWordCandidates(String regexPattern) 
	{
		String frequencyPattern = etaoiCount.toString();
		wordCandidates = new ArrayList<String>();
		
		File wordFile = new File(resourcesDir.getAbsolutePath()+File.separator + this.getWordLength()+FILE_SUFFIX);
		try 
		{
			Scanner scan = new Scanner(wordFile);
			
			String line = null;
			boolean frequencyPatternFound = false;
			while(scan.hasNextLine()) 
			{
				line = scan.nextLine();
				if(Character.isDigit(line.charAt(0)))  // digit means frequency pattern in encoded file
				{
					if(frequencyPatternFound)
					{   // we just hit a new pattern - we're done
						break;
					}
					else if (line.equals(frequencyPattern))
					{
						frequencyPatternFound = true;// we've got a hit on our pattern - start collecting words
					}
				}
				else if (frequencyPatternFound && line.matches(regexPattern)) // only add words that match the known letters thus far
				{	
					wordCandidates.add(line);
				}
			}
			scan.close();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
			throw new RuntimeException(ioe);
		}
	}

	

}
