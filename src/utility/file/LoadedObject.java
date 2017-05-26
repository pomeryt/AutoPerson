package utility.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

/**
 * This class is used to instantiate object from a binary file.
 * @author Rin
 * @version 1.0.0
 */
public class LoadedObject {
	/**
	 * It is better to check FileNotFoundException.
	 * @param path of binary file
	 * @return object
	 * @throws Exception
	 * @since 1.0.0
	 */
	public Object load(String path) throws Exception{
		// Stream
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		// Load an object
		Object obj = ois.readObject();
		
		// Close stream
		ois.close();
		
		// Return the object
		return obj;
	}
	
	/**
	 * It will try to load an object. <br />
	 * When the save file is missing, it will return an default object.
	 * @param path of binary file
	 * @param defaultValue in case where the save file is missing
	 * @return object
	 * @throws Exception
	 * @since 1.0.0
	 */
	public Object load(String path, Object defaultValue) throws Exception{
		// Declare a variable for return statement
		Object obj;
		
		// Stream
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
					
			// Load an object
			obj = ois.readObject();
					
			// Close stream
			ois.close();
		} catch (FileNotFoundException fileNotFoundException){
			obj = defaultValue;
		}
		
		// Return the object
		return obj;
	}
}
