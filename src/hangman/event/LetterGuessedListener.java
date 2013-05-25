package hangman.event;

import java.util.EventListener;

/**
 * Signals ability to react to a "letter guessed" event.
 * @author Vera Coberley
 * May 24, 2013
 */

public interface LetterGuessedListener extends EventListener {

	/** Reacts to a LetterGuessedEvent.*/
	public void onLetterGuessed(LetterGuessedEvent eve);
}
