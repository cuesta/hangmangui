package hangman.ui;

import hangman.HangmanLogic;
import hangman.event.LetterGuessedListener;

import javax.swing.JComponent;

/**
 * Represents the ability to display the number of false guesses remaining, for instance, via the 
 * traditional hangman outline.
 * @author Vera Coberley
 * May 24, 2013
 */

public interface GameStateRenderer extends LetterGuessedListener{

	/** Returns the number of guesses supported by the instance. */
	public int getMaxNbrGuessesSupported();
	
	
	/** Returns the component that provides the actual display.*/
	public JComponent getDisplay();
	
	/** Resets the state.*/
	public void reset(HangmanLogic logic);
}
