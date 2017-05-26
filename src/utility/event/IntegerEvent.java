package utility.event;

/**
 * This functional interface will be used for an event listener with an integer parameter.
 * @author Rin
 * @version 1.0.0
 */
public interface IntegerEvent {
	/**
	 * Execute something for an event where an integer parameter is required.
	 * @param integer
	 */
	public void handle(int integer);
}
