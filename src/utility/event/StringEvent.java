package utility.event;

/**
 * This functional interface will be used for an event listener with a string parameter.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public interface StringEvent {
	/**
	 * Execute something for an event where a string parameter is required.
	 * @param string
	 * @since 1.0.0
	 */
	public void handle(String string);
}
