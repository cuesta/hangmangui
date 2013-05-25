package hangman.event;

import hangman.HangmanLogic;

import java.util.EventObject;

/**
 * Abstract event for "letter selected"
 * @author Vera Coberley
 * May 24, 2013
 */

@SuppressWarnings("serial")
public class LetterGuessedEvent extends EventObject {

	private char letter;
	private boolean success;

	/**
	 * @param source
	 */
	public LetterGuessedEvent(Object source, char letter, boolean success) {
		super(source);
		setLetter(letter);
		setSuccess(success);
	}

	/**
	 * @return the letter
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * @param letter the letter to set
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}

	/**
	 * @return the success - true if letter was guessed successfully
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success - if true, letter was guessed successfully
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/** Provides access to the HangmanLogic instance that fired the event.*/
	public HangmanLogic getLogic() 
	{
		return (HangmanLogic) this.getSource();
	}

}