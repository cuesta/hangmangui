package hangman.ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import hangman.HangmanLogic;
import hangman.LetterSelector;
import hangman.event.LetterGuessedEvent;

/**
 * A strategic player that attempts to find the correct word using the following approach:
 * 1.  For the first five letters, "guess" the most frequently used letters in the English language.
 * 2.  Based on the overall length of the phrase, locate a pre-parsed list of words that has that same word length.
 * 3.  Based on the frequency of the five letters in the guessable phrase, further narrow down the words from the word file, which is
 *     marked up with the same frequency pattern. 
 * 4.  From there on, choose as-of-yet not guessed letters from the candidate words to further narrow down the word list.
 * @author Vera Coberley
 * May 25, 2013
 */

public class CleverPlayer extends AbstractAIPlayer
{

	protected char[] frequentLetters = {'e','t','a','o','i'};
	protected char[] sortedFrequentLetters;
	protected int frequentLetterIndex;
	protected List<String> wordCandidates;
	protected List<Character> guessedCharacters;
	private static final String FILE_SUFFIX = "_words_sorted.txt";
	private StringBuilder etaoiCount;
	private String currentRegex;
	
	/**
	 * @param select
	 */
	public CleverPlayer(LetterSelector select)
	{
		super(select);
		guessedCharacters = new ArrayList<Character>();
		sortedFrequentLetters = Arrays.copyOf(frequentLetters, frequentLetters.length);
		Arrays.sort(sortedFrequentLetters);
		etaoiCount = new StringBuilder(5);
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
	
	private char nextStrategicLetter()
	{
		char next = ' ';
		while (next == ' ')
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
		}
		throw new IllegalStateException("We appear to have run out of letters to guess");
	}
	
	/** Loading the marked-up, word-length file, and retrieving candidate words. */
	private void loadWordCandidates(String regexPattern) 
	{
		File dir = null;
		while (dir == null)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Please locate the 'resources' directory in the project folder:");
			fileChooser.showOpenDialog(null);
			dir = fileChooser.getSelectedFile();
		}
		String frequencyPattern = etaoiCount.toString();
		wordCandidates = new ArrayList<String>();
		
		File wordFile = new File(dir.getAbsolutePath()+File.separator + this.getWordLength()+FILE_SUFFIX);
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
