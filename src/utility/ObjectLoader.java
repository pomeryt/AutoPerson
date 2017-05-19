package utility;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * This class is used to instantiate object from a binary file.
 * @author Rin
 * @version 1.0.0
 */
public class ObjectLoader {
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
}
