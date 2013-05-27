package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;

import hangman.event.LetterGuessedEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WiltingFlowerRendererRemix implements GameStateRenderer
{
    private static final int MAX_GUESSES = 10;
    private static final String MESSAGE = "Number of guesses remaining: ";
    private JLabel messageLabel;
	private JLabel imageLabel;
	private JPanel panel;
	private ImageIcon[]images;
	int imageIndex = 0;
	
	
	/** Creates a new instance.*/
	public WiltingFlowerRendererRemix(File resourceDir) 
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout(20,10));
		imageLabel = new JLabel();
		panel.add(imageLabel, BorderLayout.CENTER);
		JLabel blank = new JLabel();
		// alignment hack:
		blank.setText("                       ");
		panel.add(blank, BorderLayout.WEST);
		messageLabel = new JLabel();
		messageLabel.setText(MESSAGE+MAX_GUESSES);
		messageLabel.setFont(new Font("Verdana", Font.BOLD, 24));
		panel.add(messageLabel, BorderLayout.NORTH);
		int numberOfImages = 10;
		
		images = new ImageIcon[numberOfImages];
		for (int i = 0; i < numberOfImages; i++)
		{
			images[i] = new ImageIcon(resourceDir.toString()+File.separatorChar+"Flower"+i + ".png");
		}
		imageLabel.setIcon(images[imageIndex]);
	}

	/* (non-Javadoc)
	 * @see hangman.event.LetterGuessedListener#onLetterGuessed(hangman.event.LetterGuessedEvent)
	 */
	@Override
	public void onLetterGuessed(LetterGuessedEvent eve)
	{
		if (!eve.isSuccess())
		{
			if (imageIndex < images.length)
			{
				imageIndex++;
				imageLabel.setIcon(images[imageIndex]);
				messageLabel.setText(MESSAGE+eve.getLogic().getNumGuessesLeft());
			}
			
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
