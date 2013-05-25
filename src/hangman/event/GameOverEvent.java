package hangman.event;

import java.util.EventObject;

/**
 * Event that signals "game over".
 * 
 * @author Vera Coberley
 * May 24, 2013
 */

@SuppressWarnings("serial")
public class GameOverEvent extends EventObject {

	public enum Result {
		PLAYER_WON,
		PLAYER_LOST;
	}
	
	private Result result;
	
	/**
	 * @param source
	 */
	public GameOverEvent(Object source, Result result) {
		super(source);
		setResult(result);
	}
	
	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}
	
	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

}
