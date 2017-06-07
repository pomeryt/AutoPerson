package utility.observed;

import java.io.Serializable;

import utility.event.BooleanEvent;

/**
 * This class observes its boolean value. <br />
 * It must inherit <b>ObservedType</b> in utility package. <br />
 * When event list is needed, it must use the event list from its parent class for deserialization. <br />
 * It requires <b>BooleanEvent</b> class in utility package.
 * @author Rin
 * @version 1.0.0
 */
public class ObservedBoolean extends ObservedType implements Serializable {
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
		handleEvents();
	}
	
	/**
	 * Adds an event. <br />
	 * The event will be executed when the value changes.
	 * @param event
	 * @since 1.0.0
	 */
	public void onValueChanged(BooleanEvent event){
		super.booleanEvents.add(event);
	}
	
	/**
	 * Switch boolean value.
	 * @since 1.0.0
	 */
	public void switchValue(){
		// Switch value
		if (value){
			value = false;
		} else {
			value = true;
		}
		
		// Handle all events
		handleEvents();
	}
	
	/**
	 * Execute all events. <br />
	 * This method should be called when the boolean value is changed.
	 * @since 1.0.0
	 */
	private void handleEvents(){
		for (BooleanEvent event : super.booleanEvents){
			event.handle(value);
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private boolean value;
}
