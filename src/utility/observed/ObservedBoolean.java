package utility.observed;

import java.util.ArrayList;
import java.util.List;

import utility.event.BooleanEvent;

/**
 * This class observes its boolean value. <br />
 * It requires <b>BooleanEvent</b> class in utility package.
 * @author Rin
 * @version 1.0.0
 */
public class ObservedBoolean {
	/**
	 * Store initial boolean value which can be changed later.
	 * @param value boolean
	 * @since 1.0.0
	 */
	public ObservedBoolean(boolean value){
		this.value = value;
	}
	
	/**
	 * Returns the boolean value.
	 * @return a boolean value
	 * @since 1.0.0
	 */
	public boolean value(){
		return value;
	}
	
	/**
	 * Change the boolean value. <br />
	 * All events will be executed.
	 * @param value new boolean
	 * @since 1.0.0
	 */
	public void changeValue(boolean value){
		// Set new value
		this.value = value;
		
		// Handle all events
		for (BooleanEvent event : events){
			event.handle(value);
		}
	}
	
	/**
	 * Adds an event. <br />
	 * The event will be executed when the value changes.
	 * @param event
	 * @since 1.0.0
	 */
	public void onValueChanged(BooleanEvent event){
		events.add(event);
	}
	
	private boolean value;
	
	private final List<BooleanEvent> events = new ArrayList<BooleanEvent>();
}
