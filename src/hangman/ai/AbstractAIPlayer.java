package hangman.ai;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


import hangman.LetterSelector;
import hangman.event.LetterGuessedEvent;
import hangman.event.LetterGuessedListener;

/**
 * The parent class of all AI players.
 * @author Vera Coberley
 * May 25, 2013
 */

public abstract class AbstractAIPlayer implements LetterGuessedListener
{

	protected LetterSelector selector;
	
	/** Creates a new instance with the LetterSelector indicated.
	 * @param select callback to the UI to select a letter
	 * */
	public AbstractAIPlayer(LetterSelector select ){
		this.selector = select;
	}
	
	/**
	 * @return the selector (callback to the UI to select a letter)
	 */
	public LetterSelector getSelector()
	{
		return selector;
	}

	/**
	 * @param selector the selector to set (callback to the UI to select a letter)
	 */
	public void setSelector(LetterSelector selector)
	{
		this.selector = selector;
	}
	
	/** Causes the AI player to select the first letter.*/
	public void start() 
	{
		selector.selectLetter(nextLetter());
	}

	/** Returns the next letter for guessing.*/
	public abstract char nextLetter();
	
	 
	/** Reacts to a LetterGuessedEvent.*/
	public void onLetterGuessed(final LetterGuessedEvent eve) {
		// as long as the game isn't over, a "letter guessed" event will trigger this instance to select the next letter
		// and "select" it in the target selector (in our concrete instance, it will click the letter button in the UI
		if(!eve.getLogic().isGameOver()) 
		{
			// We're in an event loop here.  To allow the other listeners to catch up,
			// the next letter selection is happening on a diffrent thread after a time delay.
			ActionListener taskPerformer = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// double-check game state
					if (!eve.getLogic().isGameOver())
					{
						selector.selectLetter(nextLetter());
					}
				}
			  };
			  new Timer(2000, taskPerformer).start();
		} 
	
	}
}
