package utility.primitive;

import java.io.Serializable;

public class MyInteger implements Serializable {
	
	public MyInteger(int value){
		this.value = value;
	}
	
	public void change(int value){
		this.value = value;
	}
	
	public int value(){
		return this.value;
	}
	
	private static final long serialVersionUID = 1L;
	
	private int value;
}
