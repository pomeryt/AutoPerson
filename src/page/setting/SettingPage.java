package page.setting;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import page.Page;
import page.common.KeyPrompt;
import page.main.MainPage;
import utility.event.PlainEvent;
import utility.file.LoadedObject;
import utility.file.SavedObject;

/**
 * The setting page allows an user to configure <b>Always On Top</b> property of
 * the program.
 * 
 * @author Rin
 * @version 1.0.0
 */
public class SettingPage extends Page {
	/**
	 * It will load setting files.
	 * 
	 * @param settingPaths
	 *            The path of binary files. It should contain "alwaysOnTop" and
	 *            "recording" keys.
	 * @param stage
	 *            JavaFX stage
	 * @throws Exception
	 * @since 1.0.0
	 */
	public SettingPage(Map<SettingKey, String> settingPaths, Stage stage) throws Exception {
		// Initialize variables using arguments
		this.stage = stage;
		this.settingPaths = settingPaths;

		// Load settings or generate default settings
		final LoadedObject objectLoader = new LoadedObject();
		boolean alwaysOnTop = (boolean) objectLoader.load(settingPaths.get(SettingKey.ALWAYS_ON_TOP_PATH), false);
		int recordKey = (int) objectLoader.load(settingPaths.get(SettingKey.RECORD_KEY_PATH), KeyEvent.VK_BACK_QUOTE);
		
		// Store settings in the local hash map
		settings.put(SettingKey.ALWAYS_ON_TOP, alwaysOnTop);
		settings.put(SettingKey.RECORD_KEY, recordKey);

		// CheckBox for always on top feature
		cbAlwaysOnTop.setOnAction(actionEvent -> {
			if ((boolean) settings.get(SettingKey.ALWAYS_ON_TOP) == true) {
				settings.put(SettingKey.ALWAYS_ON_TOP, false);
			} else {
				settings.put(SettingKey.ALWAYS_ON_TOP, true);
			}
			updateAlwaysOnTop();
		});

		// StackPane for storing record button
		StackPane paneRecordButton = new StackPane();
		paneRecordButton.getChildren().add(bRecord);
		paneRecordButton.setAlignment(Pos.CENTER);

		// GridPane for listing elements
		GridPane grid = new GridPane();
		grid.addColumn(0, cbAlwaysOnTop, paneRecordButton);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);

		// StackPane
		pane.getChildren().addAll(grid, bMain);
		StackPane.setAlignment(bMain, Pos.TOP_CENTER);
		StackPane.setMargin(bMain, new Insets(10, 0, 0, 0));

		// Scene
		scene = new Scene(pane);

		// Calling private methods to complete building for this object
		activateRecordButton();
		updateAlwaysOnTop();
		updateRecordKey();
	}

	/**
	 * The program will go to main page when <b>To Main</b> button is clicked.
	 * 
	 * @param mainPage
	 * @since 1.0.0
	 */
	public void linkToMainPage(MainPage mainPage) {
		bMain.setOnAction(actionEvent -> {
			stage.setScene(mainPage.body());
		});
	}

	/**
	 * The current <b>Always On Top</b> property will be saved to a binary file.
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	public void save() throws Exception {
		SavedObject objectSaver = new SavedObject();
		objectSaver.save(settingPaths.get(SettingKey.ALWAYS_ON_TOP_PATH),
				(Serializable) settings.get(SettingKey.ALWAYS_ON_TOP));
		objectSaver.save(settingPaths.get(SettingKey.RECORD_KEY_PATH),
				(Serializable) settings.get(SettingKey.RECORD_KEY));
	}

	public Map<SettingKey, Object> settings() {
		return settings;
	}

	/**
	 * This method must be called in order to use this object. <br />
	 * Simply, place this body in a container.
	 * 
	 * @return The body of this object
	 * @since 1.0.0
	 */
	public Scene body() {
		return scene;
	}

	/**
	 * This method will synchronize the <b>Always On Top</b> property and
	 * corresponding user interface.
	 * 
	 * @since 1.0.0
	 */
	private void updateAlwaysOnTop() {
		if ((boolean) settings.get(SettingKey.ALWAYS_ON_TOP) == true) {
			cbAlwaysOnTop.setSelected(true);
			stage.setAlwaysOnTop(true);
		} else {
			cbAlwaysOnTop.setSelected(false);
			stage.setAlwaysOnTop(false);
		}
	}

	private void updateRecordKey() {
		String keyText = KeyEvent.getKeyText((int) settings.get(SettingKey.RECORD_KEY));
		bRecord.setText("Record: " + keyText);
	}

	private void activateRecordButton() {
		bRecord.setOnAction(actionEvent -> {
			// Event when save button of KeyPrompt is pressed
			PlainEvent saveEvent = new PlainEvent() {
				@Override
				public void handle() {
					updateRecordKey();
				}
			};

			// Initialize KeyPrompt
			KeyPrompt keyPrompt = new KeyPrompt(pane, saveEvent);

			// Show initial record key
			String initialText = "Record: " + KeyEvent.getKeyText((int) settings.get(SettingKey.RECORD_KEY));
			keyPrompt.showText(initialText);

			// Save new record key
			keyPrompt.onKeyPressed(keyCode -> {
				settings.put(SettingKey.RECORD_KEY, keyCode);
				keyPrompt.showText("Record: " + KeyEvent.getKeyText(keyCode));
			});
		});
	}

	private final Map<SettingKey, Object> settings = new HashMap<SettingKey, Object>();
	private final Map<SettingKey, String> settingPaths;

	private final Button bRecord = new Button();
	private final CheckBox cbAlwaysOnTop = new CheckBox("Always On Top");
	private final Button bMain = new Button("To Main");
	private final StackPane pane = new StackPane();
	private final Scene scene;
	private final Stage stage;
}
