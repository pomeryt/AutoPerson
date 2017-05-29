package page.main;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jnativehook.GlobalScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import page.Page;
import page.common.KeyPrompt;
import page.edit.EditPage;
import page.setting.SettingKey;
import page.setting.SettingPage;
import utility.ErrorMessage;
import utility.SearchableList;
import utility.event.PlainEvent;

public class MainPage extends Page {

	public MainPage(String scriptFolder, Stage stage) throws IOException {
		// Initialize variables with arguments
		this.scriptFolder = scriptFolder;
		this.stage = stage;

		// Initialize Macro
		macro = new Macro(scriptFolder, stage, lReady);

		// Initialize container
		GridPane gridButton = new GridPane();

		// Label for running status
		lReady.setAlignment(Pos.CENTER);
		lReady.setPrefWidth(Integer.MAX_VALUE);
		lReady.setStyle("-fx-border-color: silver;");

		// Label for showing key
		lKey.setAlignment(Pos.CENTER);
		lKey.setPrefWidth(Integer.MAX_VALUE);
		lKey.setStyle("-fx-border-color: silver;");

		// SearchableList
		searchableList = new SearchableList(pane);
		searchableList.onSelection(() -> {
			showKeyText();
		});

		// Key Button
		bKey.setOnAction(actionEvent -> {
			// Making sure an item is selected
			if (searchableList.selectedItem() != null) {
				// Remove macro for a bit
				GlobalScreen.removeNativeKeyListener(macro);

				// Container for currently selected key
				Integer[] selectedKey = new Integer[1];

				// Event when save button of KeyPrompt is pressed
				PlainEvent saveEvent = new PlainEvent() {
					@Override
					public void handle() {
						if (selectedKey[0] != null) {
							try {
								// Currently selected path
								Path currentPath = Paths.get(scriptFolder + "/" + searchableList.selectedItem() + ".txt");
								
								// Copy everything in the file.
								List<String> copy = new ArrayList<String>();
								BufferedReader reader = Files.newBufferedReader(currentPath);
								String line = reader.readLine();
								while (line != null) {
									copy.add(line);
									line = reader.readLine();
								}
								reader.close();
	
								// Write selected key and the rest of copy
								BufferedWriter writer = Files.newBufferedWriter(currentPath);
								writer.write(Integer.toString(selectedKey[0]) + "\n");
								for (int x = 1; x < copy.size(); x++) {
									writer.write(copy.get(x) + "\n");
								}
								writer.close();
								
								// Refresh key data
								macro.refreshKeyData();
								
								// Refresh key text
								showKeyText();
							} catch (Exception exception){
								ErrorMessage errorMessage = new ErrorMessage(exception, stage);
								errorMessage.showThenClose();
							}
						}

						// Add macro back
						GlobalScreen.addNativeKeyListener(macro);
					}
				};

				// Initialize Key Prompt
				KeyPrompt keyPrompt = new KeyPrompt(pane, saveEvent);
				
				// Show initial key
				try {
					BufferedReader reader = Files.newBufferedReader(Paths.get(scriptFolder+"/"+searchableList.selectedItem()+".txt"));
					String firstLine = reader.readLine();
					try {
						keyPrompt.showText(KeyEvent.getKeyText(Integer.parseInt(firstLine)));
					} catch (NumberFormatException numberFormatException){
						
					}
				} catch (IOException ioException) {
					ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
					errorMessage.showThenClose();
				}
				
				keyPrompt.onKeyPressed(keyCode -> {
					selectedKey[0] = keyCode;
					keyPrompt.showText(KeyEvent.getKeyText(keyCode));
				});
			}
		});

		// Remove Button
		bRemove.setStyle("-fx-text-fill: red;");
		bRemove.setOnAction(actionEvent -> {
			// Selected Item
			String selectedItem = searchableList.selectedItem();

			// Making sure an item has been selected
			if (selectedItem != null) {
				// Add txt extension
				String removingFile = selectedItem + ".txt";

				// Try to delete the file
				try {
					Files.delete(Paths.get(scriptFolder + "/" + removingFile));
				} catch (IOException ioException) {
					ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
					errorMessage.showThenClose();
				}

				// refresh list
				refreshList();
			}
		});

		// Refresh Button
		bRefresh.setOnAction(actionEvent -> {
			refreshList();
		});

		// Button array
		Button[] buttons = { bNew, bEdit, bKey, bSetting, bRefresh, bRemove };
		for (Button button : buttons) {
			button.setMinWidth(75);
		}

		// StackPane for aligning the remove button on bottom-left
		StackPane paneRemoveButton = new StackPane();
		paneRemoveButton.getChildren().add(bRemove);
		paneRemoveButton.setAlignment(Pos.BOTTOM_CENTER);
		paneRemoveButton.prefHeightProperty().bind(gridButton.heightProperty());

		// GridPane for the buttons
		gridButton.addColumn(0, bNew, bEdit, bKey, bSetting, bRefresh, paneRemoveButton);

		// GridPane for joining buttons and SearchableList.
		GridPane gridButtonAndList = new GridPane();
		gridButtonAndList.addRow(0, gridButton, searchableList.body());

		// GridPane for joining gridButtonAndList and status label.
		GridPane gridStat = new GridPane();
		gridStat.addColumn(0, lReady, lKey, gridButtonAndList);
		gridStat.setPadding(new Insets(10, 10, 10, 10));

		// StackPane
		pane.getChildren().add(gridStat);

		// Scene
		scene = new Scene(pane);

		// Calling private methods to complete building this object
		refreshList();

		// Turn on and off macro respect to scene changes
		stage.sceneProperty().addListener(e -> {
			if (stage.getScene().equals(scene)) {
				GlobalScreen.addNativeKeyListener(macro);
			} else {
				GlobalScreen.removeNativeKeyListener(macro);
			}
		});
	}

	public void linkToSettingPage(SettingPage settingPage) {
		bSetting.setOnAction(actionEvent -> {
			stage.setScene(settingPage.body());
		});
	}

	public void linkToEditPage(EditPage editPage, Map<SettingKey, Object> settingData) {
		bEdit.setOnAction(actionEvent -> {
			String fileName = searchableList.selectedItem();

			if (searchableList.selectedItem() != null) {
				try {
					editPage.activatePage(fileName, scriptFolder, settingData, this);
				} catch (Exception exception) {
					ErrorMessage errorMessage = new ErrorMessage(exception, stage);
					errorMessage.showThenClose();
				}
				stage.setScene(editPage.body());
			}
		});
	}

	public void activateNewButton(EditPage editPage, Map<SettingKey, Object> settingData) {
		bNew.setOnAction(actionEvent -> {
			// Remove macro for a bit
			GlobalScreen.removeNativeKeyListener(macro);

			// Initialize containers
			StackPane paneNew = new StackPane();
			GridPane gridTop = new GridPane();

			// Label for an error message
			Label lError = new Label();
			lError.prefWidthProperty().bind(gridTop.widthProperty());
			lError.setAlignment(Pos.CENTER);
			lError.setStyle("-fx-text-fill: red;");

			// TextField for prompt
			TextField tfNew = new TextField();
			tfNew.setPromptText("Enter a script name");

			// Create Button
			Button bCreate = new Button("Create");
			bCreate.setOnAction(create -> {
				// New script name
				String fileName = tfNew.getText();

				// Flag for determining whether the creation was successful or
				// not
				boolean success = true;

				if (fileName.equals("")) {
					// When the user provides nothing for script name
					lError.setText("Please enter a script name");
					success = false;
				} else {
					// Try to create a new file
					try {
						Files.createFile(Paths.get(scriptFolder + "/" + fileName + ".txt"));
					} catch (FileAlreadyExistsException fileAlreadyExistsException) {
						// When the file already exists
						lError.setText("File already exists");
						success = false;
					} catch (IOException ioException) {
						// Any other unexpected error
						ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
						errorMessage.showThenClose();
					}
				}

				// If the file creation was successful, populate default values
				// and ten go to edit page.
				if (success) {
					// Write default values
					try {
						BufferedWriter writer = Files
								.newBufferedWriter(Paths.get(scriptFolder + "/" + fileName + ".txt"));
						writer.write("\n");
						writer.write("1\n");
						writer.write("100\n");
						writer.write("1000");
						writer.close();
					} catch (Exception exception) {
						ErrorMessage errorMessage = new ErrorMessage(exception, stage);
						errorMessage.showThenClose();
					}

					// Refresh list
					refreshList();

					// Remove the prompt
					pane.getChildren().remove(paneNew);

					// Go to edit page
					try {
						editPage.activatePage(fileName, scriptFolder, settingData, this);
					} catch (Exception exception) {
						ErrorMessage errorMessage = new ErrorMessage(exception, stage);
						errorMessage.showThenClose();
					}
					stage.setScene(editPage.body());
				}
			});

			// Cancel Button
			Button bCancel = new Button("Cancel");
			bCancel.setOnAction(cancel -> {
				pane.getChildren().remove(paneNew);
				GlobalScreen.addNativeKeyListener(macro);
			});

			// GridPane for top
			gridTop.addRow(0, tfNew, bCreate, bCancel);

			// GridPane for everything
			GridPane grid = new GridPane();
			grid.addColumn(0, gridTop, lError);
			grid.setAlignment(Pos.CENTER);

			// StackPane for entire prompt
			paneNew.getChildren().add(grid);
			paneNew.setStyle("-fx-background-color: rgba(192, 192, 192, 0.7);");

			// Insert the prompt to root container
			pane.getChildren().add(paneNew);
		});
	}

	public Scene body() {
		return scene;
	}

	private void refreshList() {
		// Clear SearchableList
		searchableList.clear();

		// Script Path
		Path scriptDir = Paths.get(scriptFolder);

		// Try iterate through the script folder and insert each file into the
		// list
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(scriptDir);
			for (Path script : stream) {
				String fileName = script.getFileName().toString().split(".txt")[0];
				searchableList.add(fileName);
			}
		} catch (IOException ioException) {
			ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
			errorMessage.showThenClose();
		}

		// Clear text for showing key
		lKey.setText("");
		
		// Refresh key data
		try {
			macro.refreshKeyData();
		} catch (IOException ioException) {
			ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
			errorMessage.showThenClose();
		}
	}
	
	private void showKeyText(){
		String fileName = searchableList.selectedItem();
		Path filePath = Paths.get(scriptFolder + "/" + fileName + ".txt");

		if (macro.duplicate().containsKey(filePath)) {
			lKey.setStyle("-fx-border-color: silver; -fx-text-fill: red;");
			Path duplicatePath = macro.duplicate().get(filePath);
			String duplicateFileName = duplicatePath.getFileName().toString().split(".txt")[0];
			lKey.setText("Duplicate with " + duplicateFileName);
		} else {
			try {
				BufferedReader reader = Files.newBufferedReader(filePath);
				try {
					int key = Integer.parseInt(reader.readLine());
					String keyText = KeyEvent.getKeyText(key);
					lKey.setStyle("-fx-border-color: silver; -fx-text-fill: black;");
					lKey.setText(keyText);
				} catch (NumberFormatException numberFormatException) {
					lKey.setText("");
				}
				reader.close();
			} catch (IOException ioException) {
				ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
				errorMessage.showThenClose();
			}
		}
	}

	private final String scriptFolder;

	private final Macro macro;

	private final SearchableList searchableList;
	
	private final Label lReady = new Label("Ready");
	private final Label lKey = new Label();
	private final Button bNew = new Button("New");
	private final Button bEdit = new Button("Edit");
	private final Button bKey = new Button("Key");
	private final Button bSetting = new Button("Setting");
	private final Button bRefresh = new Button("Refresh");
	private final Button bRemove = new Button("Remove");
	private final StackPane pane = new StackPane();
	private final Scene scene;
	private final Stage stage;
}
