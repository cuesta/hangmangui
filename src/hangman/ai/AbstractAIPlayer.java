package hangman.ai;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
	protected boolean listeningEnabled;
	protected int wordLength;
	protected List<Character> guessableOrig;
	protected List<Character> guessable;
	protected String name;
	
	/** Creates a new instance with the LetterSelector indicated.
	 * Note that the "selector" must be set before the instance is usable.
	 * */
	public AbstractAIPlayer(String name)
	{
		setName(name);
	}
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/** Creates a new instance with the LetterSelector indicated.
	 * @param select callback to the UI to select a letter
	 * */
	public AbstractAIPlayer(LetterSelector select )
	{
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
	
	protected void setListeningEnabled(boolean tf) 
	{
		listeningEnabled = tf;
	}
	
	protected boolean isListeningEnabled()
	{
		return listeningEnabled;
	}
	
	/** Causes the AI player to select the first letter.*/
	public void start(int wordLength) 
	{
		setListeningEnabled(true);
		setWordLength(wordLength);
		selector.selectLetter(nextLetter());
	}

	/** Returns the next letter for guessing.*/
	public abstract char nextLetter();
	
	
	
	 
	/** Reacts to a LetterGuessedEvent.*/
	public void onLetterGuessed(final LetterGuessedEvent eve) {
		// if this instance is inactive, don't handle events
		if(!listeningEnabled)
		{
			return;
		}
		// as long as the game isn't over, a "letter guessed" event will trigger this instance to select the next letter
		// and "select" it in the target selector (in our concrete instance, it will click the letter button in the UI
		if (!eve.getLogic().isGameOver())
		{
			// We're in an event loop here. To allow the other listeners to
			// catch up,
			// the next letter selection is happening on a different thread
			// after a time delay.
			ActionListener taskPerformer = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					selector.selectLetter(nextLetter());
				}
			};
			Timer t = new Timer(1000, taskPerformer);
			t.setRepeats(false);
			t.start();
		}
		else
		{
			// switch off listening after a round has been completed
			setListeningEnabled(false);
		}
	}

	/**
	 * @return the wordLength
	 */
	public int getWordLength()
	{
		return wordLength;
	}

	/**
	 * @param wordLength the wordLength to set
	 */
	public void setWordLength(int wordLength)
	{
		this.wordLength = wordLength;
	}

	protected void initialize()
	{
		if(guessableOrig!=null)
		{
			guessable = new ArrayList<Character>();
			guessable.addAll(guessableOrig);
		}
	}

	/** Reinitializes this instance for another round.*/ 
	public void reset()
	{
		initialize();
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}
