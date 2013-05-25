package hangman;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class WordSupplier {
	
	public static String secretWord(String filename) throws IOException {
		String randomWord = null;
		int count = 0;
		int i = 0;
		String [] wordList = new String[50000];
        
        try {
        	FileInputStream fstream = new FileInputStream(filename);
        	DataInputStream in = new DataInputStream(fstream);
        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
        	String strLine;

        while ((strLine = br.readLine()) != null)  
        {
        	wordList[i] = strLine;
        	i++;
        	count++;
        }
        
        in.close();
    	Random rand = new Random();
    	randomWord = wordList[rand.nextInt(count)];
    
        }
        catch (FileNotFoundException e)
        {
        	System.out.println("File not found!");
        }
        return randomWord;
	}

}
