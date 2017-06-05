package utility.event;

/**
 * This functional interface will be used for an event listener with a boolean parameter.
 * @author Rin
 * @version 1.0.0
 */
public interface BooleanEvent {
	/**
	 * Execute something for an event where a boolean parameter is required.
	 * @param integer
	 * @since 1.0.0
	 */
	public void handle(boolean value);
}
