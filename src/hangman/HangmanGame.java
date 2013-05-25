package hangman;

import hangman.ui.BasicGameStateRenderer;
import hangman.ui.GameStateRenderer;
import hangman.ui.HangmanUI;
import hangman.ui.WiltingFlowerRenderer;
import hangman.RandomWord;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

/**
 * Driver for UI Hangman Game.
 * 
 */
public class HangmanGame 
{  
	/** COMMAND LINE ARGUEMENTS
	 * Type this into program arguements box (w/o brackets) [words.txt letters.txt r s c h]
	 * The three command line arguments
	 * args[0]: "words.txt" - Path to the words File for computer word selection
	 * args[1]: "letters.txt" - Path to the letters File 
	 * args[2]: "r" - Random AI
	 * args[3]: "s" - Systematic AI
	 * args[4]: "c" - Clever AI
	 * args[5]: "h" - Human play
	 */
	//uses the command line arguement to get a file name 
	public static void main(String[] args) throws IOException 
	{
		String nextWord = WordSupplier.secretWord(args[0]);
		int initialGuesses = 10;
		HangmanLogic hangman = new HangmanLogic(nextWord, initialGuesses);
		GameStateRenderer gsr = new BasicGameStateRenderer(hangman);
		HangmanUI ui = new HangmanUI(hangman, gsr);
		ui.setVisible(true);
		
	}
	
}

