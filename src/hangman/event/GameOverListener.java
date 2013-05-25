package hangman.event;

/**
 * Signals ability to react to a "game over" event.
 * 
 * @author Vera Coberley
 * May 24, 2013
 */

public interface GameOverListener {

	public void onGameOver(GameOverEvent eve);
}
