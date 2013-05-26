package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class parses the letter file and returns a List of Characters that are allowed.
 */

public class LetterParser
{
	public static List<Character> parseCharacterFile(File letters) throws FileNotFoundException
	{
		ArrayList<Character> guessable = new ArrayList<Character>();
		Scanner sc = new Scanner(letters);
	
		while (sc.hasNextLine()) 
		{
			String s = sc.nextLine();
			guessable.add(s.charAt(0));
			
		}	
		sc.close();
		return guessable;
	}
}
