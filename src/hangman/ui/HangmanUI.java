package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hangman.HangmanLogic;
import hangman.event.GameOverEvent;
import hangman.event.GameOverListener;
import hangman.event.LetterGuessedEvent;
import hangman.event.LetterGuessedListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * REPLACE/UPDATE WITH YOUR JFRAME LOGIC BUT KEEP Frame for HangmanGame
 * 
 * @author May 24, 2013
 */
@SuppressWarnings("serial")
public class HangmanUI extends JFrame implements GameOverListener,
		LetterGuessedListener
{

	private JLabel line1;
	private JLabel line2;
	
	private JPanel wordDisplay;
	private JLabel word;
	private HangmanLogic logic;

	String[] text =
	{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "w", "u", "v", "x", "y", "z",
			"Restart", "Start" };
	JButton[] button = new JButton[28];

	public HangmanUI(HangmanLogic logic, GameStateRenderer stateRenderer)
	{
		super();
		this.logic = logic;
		constructUI();
		logic.addGameOverListener(this);
		logic.addLetterGuessedListener(this);
		logic.addLetterGuessedListener(stateRenderer);

		// SOMEWHERE DOWN THE LINE
		// xyzComponent.add(stateRenderer.getDisplay());

		// SOMEWHERE DOWN THE LINE
		// display current phrase from "logic"

		// SOMEWHERE DOWN THE LINE
		// letterButtonActionListener - send message to logic:
		// logic.guessCharacter(guess)
	}

	private void constructUI()
	{
		setSize(800, 500);
		line1 = new JLabel("0");
		line2 = new JLabel("0");

		// places a title on the frame
		setTitle("Hangman");

		// sets the location of the frame to centered on the screen
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel gamePanel = new JPanel();
		JPanel alphabet = new JPanel(new GridLayout(7, 4));
		line1.setText("Keyboard Guess: ");
		line2.setText(" ");

		word = new JLabel();
		word.setText(logic.getKnownKeyPhrase());
		Font font = new Font("Verdana", Font.BOLD, 72);
		
		word.setFont(font);
		wordDisplay = new JPanel();
		wordDisplay.add(word);

		// add sub-panels to main panel
		mainPanel.add(gamePanel, BorderLayout.WEST);
		mainPanel.add(alphabet, BorderLayout.EAST);
		mainPanel.add(wordDisplay, BorderLayout.SOUTH);

		// add main panel to frame
		add(mainPanel);

		// create a panel for game output
		gamePanel.add(line1);
		gamePanel.add(line2);

		for (int i = 0; i < button.length; i++)
		{
			button[i] = new JButton(text[i]);
			button[i].addActionListener(new KeyActionListener());
			button[i].setEnabled(true);
			button[i].setFocusPainted(false);
			alphabet.add(button[i]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hangman.event.GameOverListener#onGameOver(hangman.event.GameOverEvent)
	 */
	@Override
	public void onGameOver(GameOverEvent eve)
	{
		// TODO Disable all further letter selection; notify user that game is
		// over
		// If player lost game, reveal the word

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hangman.event.LetterGuessedListener#onLetterGuessed(hangman.event.
	 * LetterGuessedEvent)
	 */
	@Override
	public void onLetterGuessed(LetterGuessedEvent eve)
	{
		if(eve.isSuccess()) 
		{
			word.setText(logic.getKnownKeyPhrase());
		}

	}

	public class KeyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{

			for (int i = 0; i < button.length; i++)
			{
				if (event.getSource() == button[i])
				{
					String letter = text[i];
					line2.setText(letter);
					button[i].setEnabled(false);
					try {
						logic.guessCharacter(letter.charAt(0));
					} catch (Exception e) {
						// the UI should disable duplicate guessing, so we won't worry about handling this exception
						// But just in case we have a logic error, let's print the stacktrace so we'll know
						e.printStackTrace();
					}
				}

			}

			

		}
	}
}
