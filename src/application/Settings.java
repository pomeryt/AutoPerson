package application;

import java.awt.event.KeyEvent;

import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.file.LoadedObject;
import utility.file.NewFolder;
import utility.file.SavedObject;
import utility.observed.ObservedBoolean;
import utility.observed.ObservedInteger;

/**
 * This class manages setting configurations.
 * @author Rin
 * @version 1.0.0
 */
public class Settings {
	/**
	 * Load settings from the folder. <br />
	 * It would assign default values when a file is missing.
	 * @param settingFolder
	 * @throws Exception
	 * @since 1.0.0
	 */
	public Settings(String settingFolder) throws Exception {
		// Remember parameters.
		this.settingFolder = settingFolder;
		
		// Create a setting folder if it does not exists.
		final NewFolder newFolder = new NewFolder();
		newFolder.create(this.settingFolder);
		
		// Load settings or create defaults if does not exist.
		final LoadedObject loadedObject = new LoadedObject();
		alwaysOnTop = (ObservedBoolean) loadedObject.load(this.settingFolder+"/alwaysOnTop.bin", new ObservedBoolean(false));
		recordKey = (ObservedInteger) loadedObject.load(this.settingFolder+"/recordKey.bin", new ObservedInteger(KeyEvent.VK_BACK_QUOTE));
	}
	
	/**
	 * Return always on top property.
	 * @return always on top property
	 * @since 1.0.0
	 */
	public ObservedBoolean alwaysOnTop(){
		return alwaysOnTop;
	}
	
	/**
	 * Return record key.
	 * @return record key
	 * @since 1.0.0
	 */
	public ObservedInteger recordKey(){
		return recordKey;
	}
	
	/**
	 * It saves settings as binary files. <br />
	 * It would show error message when something goes wrong.
	 * @param stage
	 * @since 1.0.0
	 */
	public void save(Stage stage){
		final SavedObject savedObject = new SavedObject();
		try {
			savedObject.save(settingFolder+"/alwaysOnTop.bin", alwaysOnTop);
			savedObject.save(settingFolder+"/recordKey.bin", recordKey);
		} catch (Exception exception) {
			final ErrorMessage errorMessage = new ErrorMessage(exception, stage);
			errorMessage.showThenClose();
		}
	}
	
	private final String settingFolder;
	private final ObservedBoolean alwaysOnTop;
	private final ObservedInteger recordKey;
}
