package utility.file;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class is used to save an object as a binary file.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class SavedObject {
	/**
	 * The path should include a file name with extension.
	 * @param path to be saved
	 * @param object that is able to be serialized
	 * @throws Exception
	 * @since 1.0.0
	 */
	public void save(String path, Serializable object) throws Exception{
		// Stream
		final FileOutputStream fos = new FileOutputStream(path);
		final ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		// Save an object
		oos.writeObject(object);
		oos.flush();
		
		// Close stream
		oos.close();
	}
}
