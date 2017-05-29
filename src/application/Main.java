package application;
	
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

import javafx.application.Application;
import javafx.stage.Stage;
import page.edit.EditPage;
import page.main.MainPage;
import page.setting.SettingKey;
import page.setting.SettingPage;
import utility.ErrorMessage;
import utility.file.NewFolder;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			
			// Set GlobalScreen to only show warnings and errors.
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			logger.setUseParentHandlers(false);
			
			// GlobalScreen
			GlobalScreen.registerNativeHook();
			
			// Required folder names
			String scriptFolder = "script";
			String settingFolder = "setting";
			
			// Create a folder for scripts if it does not exists.
			NewFolder folderCreator = new NewFolder();
			folderCreator.create(scriptFolder, settingFolder);
			
			// Setting Paths
			Map<SettingKey, String> pathMap = new HashMap<SettingKey, String>();
			pathMap.put(SettingKey.ALWAYS_ON_TOP_PATH, settingFolder+"/alwaysOnTop.bin");
			pathMap.put(SettingKey.RECORD_KEY_PATH, settingFolder+"/recording.bin");
			
			// Initialize pages
			mainPage = new MainPage(scriptFolder, stage);
			settingPage = new SettingPage(pathMap, stage);
			editPage = new EditPage(stage);
			
			// MainPage
			mainPage.activateNewButton(editPage, settingPage.settings());
			mainPage.linkToEditPage(editPage, settingPage.settings());
			mainPage.linkToSettingPage(settingPage);
			
			// SettingPage
			settingPage.linkToMainPage(mainPage);
			
			// Stage
			stage.setScene(mainPage.body());
			stage.setTitle("AutoPerson");
			stage.setWidth(325);
			stage.setHeight(350);
			stage.show();
			stage.setOnCloseRequest(windowEvent->{
				// Save settings
				try {
					settingPage.save();
				} catch (Exception exception) {
					ErrorMessage errorMessage = new ErrorMessage(exception, stage);
					errorMessage.showThenClose();
				}
				
				// Terminate Java Virtual Machine
				System.exit(0);
			});
			
		} catch(Exception exception) {
			exception.printStackTrace();
			// Show error message when an exception occurs and then close the program.
			//ErrorMessage errorMessage = new ErrorMessage(exception);
			//errorMessage.showThenClose();
		}
	}
	
	private MainPage mainPage;
	private SettingPage settingPage;
	private EditPage editPage;
	
	public static void main(String[] args) {
		launch(args);
	}
}
