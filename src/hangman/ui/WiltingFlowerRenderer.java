package hangman.ui;

import hangman.event.LetterGuessedEvent;

import javax.swing.JComponent;

/**
 * WHEN handleNewInvalidGuess() IS CALLED,
 * THE CLASS DISPLAYS THE NEXT IMAGE IN THE SEQUENCE.
 * The getDisplay() method needs to provide the actual widget.
 * 
 * @author 
 * May 24, 2013
 */

public class WiltingFlowerRenderer implements GameStateRenderer {

	/* (non-Javadoc)
	 * @see hangman.ui.GameStateRenderer#getMaxNbrGuessesSupported()
	 */
	@Override
	public int getMaxNbrGuessesSupported() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	/* (non-Javadoc)
	 * @see hangman.ui.GameStateRenderer#getDisplay()
	 */
	@Override
	public JComponent getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see hangman.event.LetterGuessedListener#onLetterGuessed(hangman.event.LetterGuessedEvent)
	 */
	@Override
	public void onLetterGuessed(LetterGuessedEvent eve)
	{
		// TODO 
		if(!eve.isSuccess()) {
			// show next image
		}
		
	}
	
	

}
