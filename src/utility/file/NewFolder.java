package utility.file;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is used to create some folders.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class NewFolder {
	/**
	 * This method will try to create some folders. <br />
	 * It will do nothing in the case where a folder already exists.
	 * @param folderNames A list of folder names
	 * @throws Exception
	 * @since 1.0.0
	 */
	public void create(String... folderNames) throws Exception{
		for (String folderName : folderNames){
			try {
				Files.createDirectories(Paths.get(folderName));
			} catch (FileAlreadyExistsException fileAlreadyExistsException) {
				// Do nothing when the folder already exists.
			}
		}
	}
}
