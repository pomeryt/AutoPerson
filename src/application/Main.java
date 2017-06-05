package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

import javafx.application.Application;
import javafx.stage.Stage;
import page.edit.EditPage;
import page.main.MainPage;
import page.setting.SettingPage;
import page.setting.Settings;
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
			final String scriptFolder = "script";
			final String settingFolder = "setting";

			// Create a folder for scripts if it does not exists.
			final NewFolder newFolder = new NewFolder();
			newFolder.create(scriptFolder, settingFolder);
			
			// Settings
			final Settings settings = new Settings(settingFolder);
			
			// Initialize pages
			mainPage = new MainPage(scriptFolder, stage);
			settingPage = new SettingPage();
			editPage = new EditPage();

			// MainPage
			mainPage.activateNewButton(scriptFolder, stage, editPage, settings.recordKey());
			mainPage.activateRemoveButton(scriptFolder, stage);
			mainPage.activateKeyButton(scriptFolder, stage);
			mainPage.activateMacro(stage, scriptFolder);
			mainPage.linkToEditPage(stage, editPage, settings.recordKey(), scriptFolder);
			mainPage.linkToSettingPage(stage, settingPage);

			// SettingPage
			settingPage.activateAlwaysOnTop(settings.alwaysOnTop(), stage);
			settingPage.activateRecordKey(settings.recordKey());
			settingPage.linkToMainPage(stage, mainPage);

			// Stage
			stage.setScene(mainPage.body());
			stage.setTitle("AutoPerson");
			stage.setWidth(325);
			stage.setHeight(350);
			stage.show();
			stage.setOnCloseRequest(windowEvent -> {
				// Save settings
				settings.save(stage);

				// Terminate Java Virtual Machine
				System.exit(0);
			});

		} catch (Exception exception) {
			// Show error message when an exception occurs and then close the
			// program.
			ErrorMessage errorMessage = new ErrorMessage(exception, stage);
			errorMessage.showThenClose();
		}
	}

	private MainPage mainPage;
	private SettingPage settingPage;
	private EditPage editPage;

	public static void main(String[] args) {
		launch(args);
	}
}
