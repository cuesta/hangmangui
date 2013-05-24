/**  @Author Joseph Jess
 *   @version 0.1, 01/20/2013
 *   This is an exception intended for being thrown when invalid input
 **/
public class InvalidInputException extends Exception 
{	
    public InvalidInputException(String message)
    {
		super(message);
    } 
}
