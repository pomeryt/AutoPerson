package utility.observed;

import java.io.Serializable;

import utility.event.IntegerEvent;

/**
 * This class observes its integer value. <br />
 * It must inherit <b>ObservedType</b> in utility package. <br />
 * When event list is needed, it must use the event list from its parent class for deserialization. <br />
 * It requires <b>IntegerEvent</b> class in utility package.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class ObservedInteger extends ObservedType implements Serializable {
	/**
	 * Store initial integer which can be changed later.
	 * @param value an integer
	 * @since 1.0.0
	 */
	public ObservedInteger(int value){
		this.value = value;
	}
	
	/**
	 * Returns the value of integer.
	 * @return an integer
	 * @since 1.0.0
	 */
	public int value(){
		return value;
	}
	
	/**
	 * Change the value of integer. <br />
	 * All events will be executed.
	 * @param value new integer
	 * @since 1.0.0
	 */
	public void changeValue(int value){
		// Set new value
		this.value = value;
		
		// Handle all events
		for (IntegerEvent event : super.integerEvents){
			event.handle(value);
		}
	}
	
	/**
	 * Adds an event. <br />
	 * The event will be executed when the value of integer changes.
	 * @param event
	 * @since 1.0.0
	 */
	public void addChangeEvent(IntegerEvent event){
		super.integerEvents.add(event);
	}
	
	private static final long serialVersionUID = 1L;
	
	private int value;
}
