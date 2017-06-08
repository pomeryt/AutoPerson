package utility.observed;

import java.util.ArrayList;
import java.util.List;

import utility.event.BooleanEvent;
import utility.event.IntegerEvent;

/**
 * All Observed Types should inherit this class and use event lists from here. <br />
 * This class would call a constructor in behalf of its child classes during deserialization.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public abstract class ObservedType {
	/**
	 * Initialize event lists for its child classes.
	 * @since 1.0.0
	 */
	public ObservedType(){
		booleanEvents = new ArrayList<BooleanEvent>();
		integerEvents = new ArrayList<IntegerEvent>();
	}
	
	protected final List<BooleanEvent> booleanEvents;
	protected final List<IntegerEvent> integerEvents;
}
