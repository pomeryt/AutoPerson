package utility;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * This class is used to instantiate object from a binary file.
 * @author Rin
 * @version 1.0.0
 */
public class Deserializer {
	/**
	 * It is better to check FileNotFoundException.
	 * @param path of binary file
	 * @return Deserialized object
	 * @throws Exception
	 */
	public Object deserialize(String path) throws Exception{
		// Stream
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		// Deserialize an object
		Object obj = ois.readObject();
		
		// Close stream
		ois.close();
		
		// Return the object
		return obj;
	}
}
