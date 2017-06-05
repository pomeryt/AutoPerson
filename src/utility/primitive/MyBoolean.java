package utility.primitive;

import java.io.Serializable;

public class MyBoolean implements Serializable {
	
	public MyBoolean(boolean value){
		this.value = value;
	}
	
	public boolean value(){
		return this.value;
	}
	
	public void switchValue(){
		if (this.value){
			this.value = false;
		} else {
			this.value = true;
		}
	}
	
	private static final long serialVersionUID = 1L;

	private boolean value;
}
