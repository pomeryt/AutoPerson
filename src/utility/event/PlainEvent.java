package utility.event;

/**
 * This functional interface will be used for an event listener without any parameters.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public interface PlainEvent {
	/**
	 * Execute something for an event where a parameter is not required.
	 * @since 1.0.0
	 */
	public void handle();
}
