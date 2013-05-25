import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class HangmanRunner
{
	/**
	 * @Author Denise Pollock
	 * @param command line inputs
	 */
	public static void main(String[] args) 
	{	
		int numberOfGuesses = 0;
		String keyPhrase = null;

		for (int i = 0; i < 2; i++)
		{
			try
			{
				numberOfGuesses = Integer.parseInt(args [i]);
			}
			catch(Exception e)
			{
					
			}
			try
			{
				keyPhrase = (args[i]);
			}
			catch(Exception e)
			{
					
			}
		}	
		HangmanLogic HangmanGame = new HangmanLogic(keyPhrase, numberOfGuesses);
	}
}