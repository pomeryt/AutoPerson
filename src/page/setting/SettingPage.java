package page.setting;

import java.awt.event.KeyEvent;

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
import utility.primitive.MyBoolean;
import utility.primitive.MyInteger;

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
	public SettingPage() {
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
	}

	public void activateAlwaysOnTop(MyBoolean alwaysOnTop, Stage stage) {
		class Local {
			public void update(){
				if (alwaysOnTop.value()) {
					cbAlwaysOnTop.setSelected(true);
					stage.setAlwaysOnTop(true);
				} else {
					cbAlwaysOnTop.setSelected(false);
					stage.setAlwaysOnTop(false);
				}
			}
		}
		
		// Local
		final Local local = new Local();
		
		// Always On Top feature
		this.cbAlwaysOnTop.setOnAction(actionEvent -> {
			alwaysOnTop.switchValue();
			local.update();
		});
		
		// Initial update
		local.update();
	}
	
	public void activateRecordKey(MyInteger recordKey){
		class Local {
			void update(){
				String keyText = KeyEvent.getKeyText(recordKey.value());
				bRecord.setText("Record: " + keyText);
			}
		}
		
		// Local
		final Local local = new Local();
		
		// Activate button
		this.bRecord.setOnAction(actionEvent -> {
			// Event when save button of KeyPrompt is pressed
			PlainEvent saveEvent = new PlainEvent() {
				@Override
				public void handle() {
					local.update();
				}
			};

			// Initialize KeyPrompt
			KeyPrompt keyPrompt = new KeyPrompt(pane, saveEvent);

			// Show initial record key
			String initialText = "Record: " + KeyEvent.getKeyText(recordKey.value());
			keyPrompt.showText(initialText);

			// Save new record key
			keyPrompt.onKeyPressed(keyCode -> {
				recordKey.change(keyCode);
				keyPrompt.showText("Record: " + KeyEvent.getKeyText(keyCode));
			});
		});
		
		// Initial update
		local.update();
	}
	
	/**
	 * The program will go to main page when <b>To Main</b> button is clicked.
	 * 
	 * @param mainPage
	 * @since 1.0.0
	 */
	public void linkToMainPage(Stage stage, MainPage mainPage) {
		bMain.setOnAction(actionEvent -> {
			stage.setScene(mainPage.body());
		});
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
	
	private final Button bRecord = new Button();
	private final CheckBox cbAlwaysOnTop = new CheckBox("Always On Top");
	private final Button bMain = new Button("To Main");
	private final StackPane pane = new StackPane();
	private final Scene scene;
}
