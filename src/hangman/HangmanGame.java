package hangman;

import hangman.ui.GameStateRenderer;
import hangman.ui.HangmanUI;
import hangman.ui.WiltingFlowerRenderer;

import java.util.Scanner;

/**
 * Driver for UI Hangman Game.
 * @author
 * 
 */
public class HangmanGame implements WordSupplier
{  
	/** CARLOS, CAN YOU DO THIS, PLEASE? 
	 * Per requirements:  Launches the HangmanGame
	 * Accepts three command line arguments
	 * Parameter 1: Path to the letters File so the computer knows what letters are allowed to be guessed (See letters.txt in the "Files You will need: " section).
	 * Parameter 2: Path to the words File for computer word selection (See words.txt in the "Files You will need: " section).
	 * Parameter 3: A character (either 'r', 's', ‘c’, or h) representing which type of computer player to use (random, systematic, or clever respectively) or if the guesser will be a human.
	 */
	public static void main(String[] args) 
	{
		HangmanGame game = new HangmanGame();
		int initialGuesses = 10;
		HangmanLogic hangman = new HangmanLogic(game.nextWord(), initialGuesses);
		GameStateRenderer gsr = new WiltingFlowerRenderer();
		HangmanUI ui = new HangmanUI(hangman, gsr);
		ui.setVisible(true);
		
	}

	/* (non-Javadoc)
	 * @see hangman.WordSupplier#nextWord()
	 */
	@Override
	public String nextWord() {
		// TODO IMPLEMENT TO SELECT RANDOM WORD FROM WORDLIST; IGNORING ANY WORDS THAT HAVE CHARACTERS OTHER THAN A-Z
		return "artificial";
	}
	
	
}
