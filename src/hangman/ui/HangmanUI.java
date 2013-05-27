package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import hangman.HangmanLogic;
import hangman.LetterSelector;
import hangman.WordSupplier;
import hangman.ai.AbstractAIPlayer;
import hangman.event.GameOverEvent;
import hangman.event.GameOverEvent.Result;
import hangman.event.GameOverListener;
import hangman.event.LetterGuessedEvent;
import hangman.event.LetterGuessedListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Frame and controller for HangmanGame
 * 
 * @author May 24, 2013
 */
@SuppressWarnings("serial")
public class HangmanUI extends JFrame implements GameOverListener,
		LetterGuessedListener, LetterSelector
{
	private static final String TITLE = "Hangman's Flower";
	private JPanel wordDisplay;
	private JLabel word;
	private HangmanLogic logic;
	private JButton aiButton;
	private JButton restartButton;
	private JComboBox playerComboBox;
	private JPanel aiPanel;
	private AbstractAIPlayer[] aiPlayers;
	private GameStateRenderer stateRenderer;
	private WordSupplier wordSupplier;

	String[] text =
	{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "w", "u", "v", "x", "y", "z"};
	JButton[] button = new JButton[text.length];

	public HangmanUI(final HangmanLogic logic, GameStateRenderer stateRenderer, AbstractAIPlayer[] _aiPlayers, int selectedAi, WordSupplier wordSupplier) throws IOException
	{
		super();
		aiPlayers = _aiPlayers;
		this.stateRenderer = stateRenderer;
		this.wordSupplier = wordSupplier;
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.logic = logic;
		constructUI(stateRenderer);
		logic.addGameOverListener(this);
		logic.addLetterGuessedListener(this);
		logic.addLetterGuessedListener(stateRenderer);
		this.playerComboBox = new JComboBox(_aiPlayers);
		
		this.playerComboBox.setSelectedIndex(selectedAi);
		
		for (AbstractAIPlayer aiPlayer : _aiPlayers)
		{
			// provide a callback to the AI player to this instance to select the next letter:
			aiPlayer.setSelector(this);
			logic.addLetterGuessedListener(aiPlayer);
		}
		
		aiPanel.add(new JLabel("AI mode: "));
		aiPanel.add(playerComboBox);
		aiButton = new JButton("Run AI");
		aiPanel.add(aiButton);
		
		aiButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				aiButton.setEnabled(false);
				final AbstractAIPlayer ai = (AbstractAIPlayer) playerComboBox.getSelectedItem();
				ai.start(logic.getKnownKeyPhrase().length());
			}
		});
		
		
		
	}

	private void constructUI(GameStateRenderer gsr)
	{
		setSize(800, 500);

		// places a title on the frame
		setTitle(TITLE);

		// sets the location of the frame to centered on the screen
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
		JPanel gamePanel = new JPanel();
		JPanel alphabet = new JPanel(new GridLayout(7, 4));
		JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
		aiPanel = new JPanel();
		aiPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

		word = new JLabel();
		word.setText(logic.getKnownKeyPhrase());
		Font font = new Font("Verdana", Font.BOLD, 48);
		
		word.setFont(font);
		wordDisplay = new JPanel();
		wordDisplay.add(word);
		bottomPanel.add(wordDisplay);
		bottomPanel.add(aiPanel);

		// add sub-panels to main panel
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(alphabet, BorderLayout.EAST);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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
		
		restartButton = new JButton("Start Over");
		wordDisplay.add(restartButton);
		restartButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try 
				{
					logic.reset(wordSupplier.secretWord(), logic.getNumberOfGuesses());
					word.setText(logic.getKnownKeyPhrase());
					aiButton.setEnabled(true);
					for (AbstractAIPlayer player : aiPlayers)
					{
						player.reset();
					}
					stateRenderer.reset(logic);
					for (int i = 0; i < button.length; i++)
					{
						button[i].setEnabled(true);
					}
					restartButton.setEnabled(false);
				} catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "An exception occurred: "+e.getMessage());
				}
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
		
		if (eve.getResult() == Result.PLAYER_LOST)
		{
			JOptionPane.showMessageDialog(this, "Sorry, the word was '"+logic.getKeyPhrase()+"'. Game Over!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Congratulations, you guessed correctly!");
		}
		
		for (int i = 0; i < button.length; i++)
		{
			button[i].setEnabled(false);
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
					restartButton.setEnabled(true);
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
