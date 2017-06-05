package page.setting;

import java.awt.event.KeyEvent;

import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.file.LoadedObject;
import utility.file.SavedObject;
import utility.primitive.MyBoolean;
import utility.primitive.MyInteger;

public class Settings {
	public Settings(String settingFolder) throws Exception {
		// Initialize required variables
		this.settingFolder = settingFolder;
		
		// Load settings or create default settings
		LoadedObject loadedObject = new LoadedObject();
		this.alwaysOnTop = (MyBoolean) loadedObject.load(settingFolder+"/alwaysOnTop.bin", new MyBoolean(false));
		this.recordKey = (MyInteger) loadedObject.load(settingFolder+"/recordKey.bin", new MyInteger(KeyEvent.VK_BACK_QUOTE));
	}
	
	public MyBoolean alwaysOnTop(){
		return this.alwaysOnTop;
	}
	
	public MyInteger recordKey(){
		return this.recordKey;
	}
	
	public void save(Stage stage){
		final SavedObject savedObject = new SavedObject();
		try {
			savedObject.save(this.settingFolder+"/alwaysOnTop.bin", alwaysOnTop);
			savedObject.save(this.settingFolder+"/recordKey.bin", recordKey);
		} catch (Exception exception) {
			ErrorMessage errorMessage = new ErrorMessage(exception, stage);
			errorMessage.showThenClose();
		}
	}
	
	private final String settingFolder;
	
	private final MyBoolean alwaysOnTop;
	private final MyInteger recordKey;
}
