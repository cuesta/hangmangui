package hangman;

import hangman.event.GameOverEvent;
import hangman.event.GameOverEvent.Result;
import hangman.event.GameOverListener;
import hangman.event.LetterGuessedEvent;
import hangman.event.LetterGuessedListener;

/**  
 * @Author Joseph Jess
 * @Author Vera Coberley
 *   @version 0.1, 01/20/2013
 *   This contains the logic for a Hangman game.
 *   I have chosen to disallow all input except alphabetic characters (disallowing number to make some things easier to manage)
 */
import java.util.ArrayList;

public class HangmanLogic 
{
	private String keyPhrase;
	private int numberOfGuesses;
	private int numberOfGuessesLeft;
	private ArrayList<Character> guessedCharacters;
	private ArrayList<GameOverListener> gameOverListeners;
	private ArrayList<LetterGuessedListener> letterGuessListeners;
	
	/**  The place holder character for letter that haven't been guessed yet in the known key phrase.*/
	public static final char WILDCARD = '-';
	public static final String REGEX_VALID = "([a-z])*";
	
	/**
	 * Constructor creates this HangmanLogic initial state
	 * @param newPhrase  the phrase to be used for the key phrase of the hangman game
	 * @param numGuesses  the number of guesses allowed in the hangman game
	 */
	public HangmanLogic(String keyPhrase, int numberOfGuesses)
	{
		initialize(keyPhrase, numberOfGuesses);
	}	
	
	private void initialize(String keyPhrase, int numberOfGuesses)
	{
		this.keyPhrase = keyPhrase;		
		this.numberOfGuesses = numberOfGuesses;
		this.numberOfGuessesLeft = numberOfGuesses;
		guessedCharacters = new ArrayList<Character>();
	}
	
	/** Resets this  instance for a new round.
	 * @param newPhrase  the phrase to be used for the key phrase of the hangman game
	 * @param numGuesses  the number of guesses allowed in the hangman game
	 * */
	public void reset(String keyPhrase, int numberOfGuesses)
	{
		initialize(keyPhrase, numberOfGuesses);
	}

	/**
	 * Accessor method for the keyPhrase field
	 * @return  returns the keyPhrase field of this object
	 */
	public String getKeyPhrase()
	{
		return keyPhrase;
	}
	
	/**
	 * method returns the keyPhrase field in an encoded manner with dashes representing the still unknown letters and letters for the known letters
	 * @return  returns the encoded keyPhrase of this object (letters known and dashes)
	 */
	public String getKnownKeyPhrase()
	{
		char[] encodedKeyPhrase = new char[keyPhrase.length()];
		String lowerCaseKeyPhrase = keyPhrase.toLowerCase();
		
		for(int i = 0; i < keyPhrase.length(); i++)
		{
			char cur = lowerCaseKeyPhrase.charAt(i);
			if(guessedCharacters.contains(cur))
				encodedKeyPhrase[i] = cur;
			else
				encodedKeyPhrase[i] = '-';
		}
		
		return (new String(encodedKeyPhrase) );
	}
	
	/**
	 * Accessor method for the numberOfGuesses field
	 * @return  returns the numberOfGuesses field of this object
	 */
	public int getNumberOfGuesses()
	{
		return numberOfGuesses;
	}
	
	/**
	 * Accessor method for the numberOfGuessesLeft field
	 * @return  returns the numberOfGuessesLeft field of this object
	 */
	public int getNumGuessesLeft()
	{
		return numberOfGuessesLeft;
	}
	
	/**
	 * Evaluates "letter guessed", and fires "LetterGuessedEvent" on registered
	 * listeners. Event evaluates as "success" if the specified character exists
	 * in the keyPhrase field. Will also fire the "game over" event, if game is
	 * over as result of the guess.
	 * 
	 * @param guess
	 *            a character being checked for in the keyPhrase field
	 * @throws InvalidInputException
	 *             if a non-valid character is passed as input to this method
	 * @throws AlreadyGuessedException
	 *             if a valid character which has already been guessed is passed
	 *             as input to this method
	 */
	public void guessCharacter(char guess) throws InvalidInputException,
			AlreadyGuessedException, GameAlreadyOverException
	{
		if (!isValidCharacter(guess))
		{
			throw new InvalidInputException("" + guess);
		}
		if (isGameOver())
		{
			throw new GameAlreadyOverException();
		}
		boolean isCorrectGuess = false;

		// force into one case
		guess = Character.toLowerCase(guess);

		if (guessedCharacters.contains(guess))
		{
			throw new AlreadyGuessedException("" + guess);
		}

		guessedCharacters.add(guess);
		if (keyPhrase.contains("" + guess))
			isCorrectGuess = true;
		else
		{
			numberOfGuessesLeft--;
			isCorrectGuess = false;
		}
		notifyLetterGuessed(guess, isCorrectGuess);
		if (isGameOver())
		{
			notifyGameOver(getKnownKeyPhrase().equals(keyPhrase.toLowerCase()));
		}
	}
	
	/** Fires the "letter guessed" event to registered listeners. */
	private void notifyLetterGuessed(char c, boolean correct)
	{
		LetterGuessedEvent eve = new LetterGuessedEvent(this, c, correct);
		for (LetterGuessedListener listen : getLetterGuessListeners())
		{
			try
			{
				listen.onLetterGuessed(eve);
			} catch (Exception e)
			{
				e.getStackTrace();
			}
		}
	}

	/** Fires the "game over" even to registered listeners. */
	private void notifyGameOver(boolean phraseGuessed)
	{
		GameOverEvent eve = new GameOverEvent(this,
				phraseGuessed ? Result.PLAYER_WON : Result.PLAYER_LOST);
		for (GameOverListener listen : getGameOverListeners())
		{
			try
			{
				listen.onGameOver(eve);
			} catch (Exception e)
			{
				// ideally, this would be a call back to the UI to display message
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns whether the guessing user is out of guesses in this game of hangman
	 * @return  Returns whether the guessing user is out of guesses in this game of hangman
	 */
	public boolean isGameOver()
	{
		return ( numberOfGuessesLeft == 0 || getKnownKeyPhrase().equals(keyPhrase.toLowerCase()));		
	}
	
	/**
	 * Returns whether the character parameter is considered valid in this game
	 * @param guess  the character being checked for validity
	 * @return  returns whether the character parameter is considered valid in this game
	 */
	private boolean isValidCharacter(char guess)
	{
		return Character.isLetter(guess);
	}
	
	/** Adds a LetterGuessedListener. */
	public void addLetterGuessedListener(LetterGuessedListener listen)
	{
		getLetterGuessListeners().add(listen);
	}

	/** Adds a GameOverListener. */
	public void addGameOverListener(GameOverListener listen)
	{
		getGameOverListeners().add(listen);
	}

	/**
	 * @return the gameOverListeners
	 */
	private ArrayList<GameOverListener> getGameOverListeners()
	{
		if (gameOverListeners == null)
		{
			gameOverListeners = new ArrayList<GameOverListener>();
		}
		return gameOverListeners;
	}

	/**
	 * @return the letterGuessListeners
	 */
	private ArrayList<LetterGuessedListener> getLetterGuessListeners()
	{
		if (letterGuessListeners == null)
		{
			letterGuessListeners = new ArrayList<LetterGuessedListener>();
		}
		return letterGuessListeners;
	}
}
