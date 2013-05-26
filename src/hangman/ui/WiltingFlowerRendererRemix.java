package hangman.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import hangman.HangmanLogic;
import hangman.event.LetterGuessedEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WiltingFlowerRendererRemix implements GameStateRenderer
{
  private static int MAX_GUESSES = 9;
	private JPanel panel;
	private JLabel imageLabel;
	private ImageIcon[]images;
	int imageIndex = 0;
	
	/** Creates a new instance.*/
	public WiltingFlowerRendererRemix(HangmanLogic logic) 
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		imageLabel = new JLabel();
		panel.add(imageLabel, BorderLayout.CENTER);
		
		int numberOfImages = 10;
		
		images = new ImageIcon[numberOfImages];
		for (int i = 0; i < numberOfImages; i++)
		{
			images[i] = new ImageIcon(getClass().getResource("Flower"+Integer.toString(i) + ".png"));
		
		}
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
				setImage(images[imageIndex]);
			}
			if (imageIndex == images.length)
			{
				//show game over pop up
			}
		}
		
	}

	/**
	 * @param imageIcon
	 */
	private void setImage(ImageIcon image)
	{
		// TODO Auto-generated method stub
		
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
