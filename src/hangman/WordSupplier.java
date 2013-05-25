package hangman;

/**
 * Indicates ability to supply a "guessable" word for the hangman game.
 * 
 * @author Vera Coberley
 * May 24, 2013
 */
public interface WordSupplier {
	
	public String nextWord();

}
