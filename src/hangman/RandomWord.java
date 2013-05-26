package hangman;

import java.util.*;
import java.io.*;

public class RandomWord {

  public static String main(String[] args) throws IOException {
        String file_name = "words.txt";
        String randomWord = null;
		int count = 0;
		int i = 0;
		String [] wordList = new String[50000];
        
        try {
        	FileInputStream fstream = new FileInputStream("words.txt");
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
