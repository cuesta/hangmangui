package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import hangman.HangmanLogic;
import hangman.event.LetterGuessedEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A no-frills representation of how many invalid guesses the user has remaining.
 * @author Vera Coberley
 * May 25, 2013
 */

public class BasicGameStateRenderer implements GameStateRenderer
{
	private static int MAX_GUESSES = 26;
	private JPanel panel;
	private JLabel instructions;
	private JLabel nbrPanel;
	
	/** Creates a new instance.*/
	public BasicGameStateRenderer(HangmanLogic logic) 
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		instructions  = new JLabel();
		instructions.setText("Nbr of guesses remaining: ");
		instructions.setFont(new Font("Verdana", Font.BOLD, 24));
		panel.add(instructions, BorderLayout.NORTH);
		nbrPanel = new JLabel();
		nbrPanel.setText(Integer.toString(logic.getNumGuessesLeft()));
		nbrPanel.setFont(new Font("Verdana", Font.BOLD, 96));
		panel.add(nbrPanel, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * @see hangman.event.LetterGuessedListener#onLetterGuessed(hangman.event.LetterGuessedEvent)
	 */
	@Override
	public void onLetterGuessed(LetterGuessedEvent eve)
	{
		if (!eve.isSuccess())
		{
			nbrPanel.setText(Integer.toString(eve.getLogic().getNumGuessesLeft()));
		}
		
	}

	/* (non-Javadoc)
	 * @see hangman.ui.GameStateRenderer#getMaxNbrGuessesSupported()
	 */
	@Override
	public int getMaxNbrGuessesSupported()
	{
		return MAX_GUESSES;
	}

	/* (non-Javadoc)
	 * @see hangman.ui.GameStateRenderer#getDisplay()
	 */
	@Override
	public JComponent getDisplay()
	{
		return panel;
	}

}
