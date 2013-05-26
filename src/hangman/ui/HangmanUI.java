package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import hangman.HangmanLogic;
import hangman.LetterSelector;
import hangman.ai.AbstractAIPlayer;
import hangman.ai.CleverPlayer;
import hangman.ai.RandomPlayer;
import hangman.ai.SimplisticPlayer;
import hangman.event.GameOverEvent;
import hangman.event.GameOverEvent.Result;
import hangman.event.GameOverListener;
import hangman.event.LetterGuessedEvent;
import hangman.event.LetterGuessedListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * REPLACE/UPDATE WITH YOUR JFRAME LOGIC BUT KEEP Frame for HangmanGame
 * 
 * @author May 24, 2013
 */
@SuppressWarnings("serial")
public class HangmanUI extends JFrame implements GameOverListener,
		LetterGuessedListener, LetterSelector
{


	private JPanel wordDisplay;
	private JLabel word;
	private HangmanLogic logic;
	// just for testing/demo purposes at this point - we'll need to add the AI to the constructor
	private AbstractAIPlayer dumbAi;
	private JButton aiButton;
	private JButton restartButton;

	String[] text =
	{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "w", "u", "v", "x", "y", "z"};
	JButton[] button = new JButton[text.length];

	public HangmanUI(HangmanLogic logic, GameStateRenderer stateRenderer) throws IOException
	{
		super();
		
		this.logic = logic;
		constructUI(stateRenderer);
		logic.addGameOverListener(this);
		logic.addLetterGuessedListener(this);
		logic.addLetterGuessedListener(stateRenderer);
		dumbAi = new CleverPlayer(this);//new RandomPlayer(this, new File("letters.txt"));
		logic.addLetterGuessedListener(dumbAi);
		
		
	}

	private void constructUI(GameStateRenderer gsr)
	{
		setSize(800, 500);
	

		// places a title on the frame
		setTitle("Hangman");

		// sets the location of the frame to centered on the screen
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel gamePanel = new JPanel();
		JPanel alphabet = new JPanel(new GridLayout(7, 4));
	

		word = new JLabel();
		word.setText(logic.getKnownKeyPhrase());
		Font font = new Font("Verdana", Font.BOLD, 72);
		
		word.setFont(font);
		wordDisplay = new JPanel();
		wordDisplay.add(word);

		// add sub-panels to main panel
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(alphabet, BorderLayout.EAST);
		mainPanel.add(wordDisplay, BorderLayout.SOUTH);

		// add main panel to frame
		add(mainPanel);

		// create a panel for game output
		gamePanel.add(gsr.getDisplay());
		

		for (int i = 0; i < button.length; i++)
		{
			button[i] = new JButton(text[i]);
			button[i].addActionListener(new KeyActionListener());
			button[i].setEnabled(true);
			button[i].setFocusPainted(false);
			alphabet.add(button[i]);
		}
		
		
		aiButton = new JButton("AI Test");
		wordDisplay.add(aiButton);
		aiButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				dumbAi.start(logic.getKnownKeyPhrase().length());
				aiButton.setEnabled(false);
				// re-enable once game is over
			}
		});
		
		
		restartButton = new JButton("Start Over");
		wordDisplay.add(restartButton);
		restartButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// placeholder.
				
			}
		});
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
		// If player lost game, reveal the word
		for (int i = 0; i < button.length; i++)
		{
			button[i].setEnabled(false);
			
		}
		if (eve.getResult() == Result.PLAYER_LOST)
		{
			JOptionPane.showMessageDialog(this, "Sorry, the word was '"+logic.getKeyPhrase()+"'. Game Over!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Congratulations, you guessed right!");
		}

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

	/* (non-Javadoc)
	 * @see hangman.LetterSelector#selectLetter(char)
	 */
	@Override
	public void selectLetter(char c)
	{
		for (int i = 0; i < button.length; i++)
		{
			if(button[i].getText().charAt(0) == c)
			{
				// adding a bit of 'pop' to the AI version:
				Color savedF = button[i].getForeground();
				button[i].setForeground(Color.BLUE);
				try 
				{ // slowing down the UI a bit
					Thread.sleep(200L);
				} catch (Exception e) {
					// ignore
				}
				button[i].doClick();
				try 
				{ // slowing down the UI a bit
					Thread.sleep(200L);
				} catch (Exception e) {
					// ignore
				}
				button[i].setForeground(savedF);
			}
			
		}
	}
}
