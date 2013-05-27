package hangman;

import hangman.ai.AbstractAIPlayer;
import hangman.ai.CleverPlayer;
import hangman.ai.RandomPlayer;
import hangman.ai.SystematicPlayer;
import hangman.ui.GameStateRenderer;
import hangman.ui.HangmanUI;
import hangman.ui.WiltingFlowerRenderer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Driver for UI Hangman Game.
 * 
 */
public class HangmanGame 
{  
	/** COMMAND LINE ARGUEMENTS
	 * Type this into program arguements box (w/o brackets) [words.txt letters.txt r|s|c|h]
	 * The three command line arguments
	 * args[0]: "words.txt" - Path to the words File for computer word selection
	 * args[1]: "letters.txt" - Path to the letters File 
	 * args[2]: either "r" - Random AI, "s" - Systematic AI, "c" - Clever AI, "h" - Human play
	 * args[3]:  path to the "resources" folder of this project.  If left off, JOption pane will prompt user.
	 */
	//uses the command line arguement to get a file name 
	public static void main(String[] args) throws IOException 
	{
		if(args.length!=3)
		{
			System.out.println("You must provide 3 arguments: path to word file, path to letter file, and code for AI player.");
			System.out.println("r = random, s = systematic, c = clever, h = human");
		}
		try 
		{
			File words = validateFile(args[0]);
			
			File letters = validateFile(args[1]);
			
			File resourcesDir = null;
			
			if(args.length == 4)
			{
				resourcesDir = validateFile(args[3]);
			}
			else 
			{
				while (resourcesDir == null)
				{ 	// ideally, this would be a conspicuous label on the file chooser, but I'm running out of time ...
					JOptionPane.showMessageDialog(null, "You will be asked to locate the 'resources' directory in this project's root folder.");
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setDialogTitle("Select 'resources' directory");
					fileChooser.showOpenDialog(null);
					resourcesDir = fileChooser.getSelectedFile();
				}
			}
			
			WordSupplier wordSupplier = new WordSupplier(words);
			
			int initialGuesses = 10;
			HangmanLogic hangman = new HangmanLogic(wordSupplier.secretWord(), initialGuesses);
			//GameStateRenderer gsr = new BasicGameStateRenderer(hangman);
			GameStateRenderer gsr = new WiltingFlowerRenderer(resourcesDir);
			AbstractAIPlayer player = null;
			List<Character> guessables = LetterParser.parseCharacterFile(letters);
			
			char modeCode = args[2].charAt(0);
			// determine which AI player to load:
			switch(modeCode)
			{
			case 'r':
				player = new RandomPlayer(guessables);
				break;
			case 's':
				player = new SystematicPlayer(guessables);
				break;
			case 'c':
				player = new CleverPlayer(resourcesDir);
				break;
			case 'h':
				break;
				default :
					System.out.println("You've provided an invalid mode: "+args[2]);
			}
			HangmanUI ui = new HangmanUI(hangman, gsr, player);
			ui.setVisible(true);
		} catch (Exception e) 
		{
			System.out.println("An exception occurred: ");
			e.printStackTrace();
		}
		
	}
	
	private static File validateFile(String file)
	{
		File f = new File(file);
		if(!f.exists() )
		{
			throw new IllegalArgumentException("Path to file is incorrect: "+file);
		}
		return f;
	}
	
}

