import java.util.Scanner;

/**
 * Driver for command line based "hangman" game
 * @author Josepi
 * @version 0.1, 01/20/2013
 */
public class HangmanGame
{  
	public static void main(String[] args) 
	{
		String keyPhrase = "Aardvark";
		int initialGuesses = 10;
		HangmanLogic hangmanGame = new HangmanLogic(keyPhrase, initialGuesses);
		
		playGame(hangmanGame);
	}
	
	private static void playGame(HangmanLogic game)
	{
		String input;
		char guess;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to hangman, your up next!\n");
		System.out.printf("The key phrase you are trying to guess is %d characters long.", game.getKeyPhrase().length(), game.getNumberOfGuesses());
		
		while(!game.isGameOver())
		{
			System.out.printf("You have %d guesses left to save your neck!\n", game.getNumGuessesLeft());
			System.out.printf("So far you know the following positions about the keyphrase (dashes represent unknown characters):\n\t%s\n\n", game.getKnownKeyPhrase());
			System.out.println("Please enter a single character that you believe is in the key phrase");
			
			
			//get input and confirm it is only one character
			input = in.nextLine();
			
			//while input is longer than one character, ask for new input with only a single character as input
			while(input.length() > 1)
			{
				System.out.println("That is more than one character, please input only a single character.");
				input = in.nextLine();
			}
			
			//guess has now been confirmed at only one character
			guess = input.charAt(0);
			
			
			//now for the meaningful part of checking the input character
			try
			{
				game.guessCharacter(guess);
			}
			catch (InvalidInputException e)
			{
				System.out.printf("\n\nThe guess %s is invalid, you are only allowed to guess letters in my version of this game, try again\n\n\n", e.getMessage());
			}
			catch (AlreadyGuessedException e)
			{
				System.out.printf("\n\nYou have already guessed %s, try again\n\n\n", e.getMessage());
			}
		}
		if(game.getNumGuessesLeft() <= 0)
			System.out.println("I am sorry, that was your last guess, and you have lost the game.");
		else
			System.out.println("Congratulations! You have won the game!");
	}

}
