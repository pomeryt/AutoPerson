package application;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

import application.page.EditPage;
import application.page.MainPage;
import application.page.SettingPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.file.NewFolder;

/**
 * It starts the program. <br />
 * It uses <b>JavaFX</b> as GUI framework. <br />
 * <a href="https://github.com/kwhat/jnativehook/releases/tag/2.1.0"><b>JNativeHook</b></a> is required for this program. 
 * Apart from this source code, the library should be obtained separately.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class Main extends Application {
	/**
	 * It starts program.
	 * @since 1.0.0
	 */
	@Override
	public void start(Stage stage) {
		try {
			// Only show logs of warnings and errors for GlobalScreen.
			final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			logger.setUseParentHandlers(false);

			// GlobalScreen
			GlobalScreen.registerNativeHook();

			// Required folder names
			final String scriptFolder = "script";
			final String settingFolder = "setting";

			// Create a folder for scripts if it does not exists.
			final NewFolder newFolder = new NewFolder();
			newFolder.create(scriptFolder);

			// Settings
			final Settings settings = new Settings(settingFolder);

			// MainPage
			final MainPage mainPage = new MainPage();

			// EditPage
			final EditPage editPage = new EditPage();

			// SettingPage
			final SettingPage settingPage = new SettingPage();

			// Macro
			final Macro macro = new Macro(scriptFolder, stage);

			// Switch scene from SettingPage to MainPage
			settingPage.addMainEvnet(() -> {
				stage.setScene(mainPage.body());
			});

			// Always On Top feature
			settingPage.alwaysOnTop().setSelected(settings.alwaysOnTop().value());
			stage.setAlwaysOnTop(settings.alwaysOnTop().value());

			settingPage.addAlwaysOnTopEvnet(() -> {
				settings.alwaysOnTop().switchValue();
			});

			settings.alwaysOnTop().addChangeEvent(isAlwaysOnTop -> {
				stage.setAlwaysOnTop(isAlwaysOnTop);
			});

			// Record Key Setting
			settingPage.showRecordKey("Record: " + KeyEvent.getKeyText(settings.recordKey().value()));

			settings.recordKey().addChangeEvent(key -> {
				settingPage.showRecordKey("Record: " + KeyEvent.getKeyText(key));
			});

			settingPage.addRecordEvnet(() -> {
				final KeyPrompt keyPrompt = new KeyPrompt(settingPage.root());
				keyPrompt.showText(KeyEvent.getKeyText(settings.recordKey().value()));
				keyPrompt.addSaveEvent(key -> {
					settings.recordKey().changeValue(key);
				});
				keyPrompt.show();
			});

			// Save script when switch scene from EditPage to MainPage
			editPage.addMainEvnet(() -> {
				try {
					final Path path = Paths.get(scriptFolder + "/" + editPage.scriptName() + ".txt");

					// Lines of recorded script
					final String[] lines = editPage.scriptArea().getText().split("\n");

					// Start Key
					final BufferedReader bufferedReader = Files.newBufferedReader(path);
					final String startKey = bufferedReader.readLine();
					bufferedReader.close();

					// Prepare to write script to text file
					final BufferedWriter writer = Files.newBufferedWriter(path);

					// Write first three lines for configuration
					writer.write(startKey + "\n");
					writer.write(editPage.loop().getText() + "\n");
					writer.write(editPage.sleep1().getText() + "\n");
					writer.write(editPage.sleep2().getText() + "\n");

					// Write script
					for (String command : lines) {
						writer.write(command + "\n");
						writer.flush();
					}

					writer.close();

				} catch (IOException ioException) {
					ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
					errorMessage.showThenClose();
				}
			});

			// Switch scene from EditPage to MainPage
			editPage.addMainEvnet(() -> {
				stage.setScene(mainPage.body());
			});
			
			// Clear button in EditPage
			editPage.addClearEvent(()->{
				editPage.scriptArea().clear();
			});

			// Switch scene from MainPage to SettingPage
			mainPage.addSettingEvent(() -> {
				stage.setScene(settingPage.body());
			});

			// From MainPage to EditPage
			mainPage.addEditEvent(() -> {
				final String fileName = mainPage.searchableList().selectedItem();

				// Making sure a script is selected
				if (mainPage.searchableList().selectedItem() != null) {
					// Prepare EditPage
					try {
						// Show script name
						editPage.showScriptName(fileName);

						// Path
						final Path path = Paths.get(scriptFolder + "/" + fileName + ".txt");

						// Reader
						final BufferedReader reader = Files.newBufferedReader(path);

						// Skip first line
						reader.readLine();

						// Loop and Sleep as string
						final String loop = reader.readLine();
						final String sleep1 = reader.readLine();
						final String sleep2 = reader.readLine();

						// Show Loop and Sleep informations
						editPage.loop().setText(loop);
						editPage.sleep1().setText(sleep1);
						editPage.sleep2().setText(sleep2);

						// Read rest of lines and store them to text area
						editPage.scriptArea().clear();
						String line = reader.readLine();
						while (line != null) {
							editPage.scriptArea().appendText(line + "\n");
							line = reader.readLine();
						}
						reader.close();

						// Show recording status as ready
						editPage.showRecording(false);

					} catch (Exception exception) {
						ErrorMessage errorMessage = new ErrorMessage(exception, stage);
						errorMessage.showThenClose();
					}

					// Go to EditPage
					stage.setScene(editPage.body());
				}
			});

			// Refresh button in MainPage
			mainPage.addRefreshEvent(() -> {
				// Clear SearchableList
				mainPage.searchableList().clear();

				// Insert all files into the SearchableList
				try {
					final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(scriptFolder));
					for (Path script : stream) {
						String fileName = script.getFileName().toString().split(".txt")[0];
						mainPage.searchableList().add(fileName);
					}
				} catch (IOException ioException) {
					ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
					errorMessage.showThenClose();
				}

				// Clear text of start key
				mainPage.showKey("", false);
			});

			// Populate initial list.
			Platform.runLater(() -> {
				mainPage.callRefreshEvents();
			});

			// Remove button in MainPage
			mainPage.addRemoveEvent(() -> {
				// Selected Item
				final String selectedItem = mainPage.searchableList().selectedItem();

				// Making sure an item has been selected
				if (selectedItem != null) {
					// Add txt extension
					final String removingFile = selectedItem + ".txt";

					// Delete the script
					try {
						Files.delete(Paths.get(scriptFolder + "/" + removingFile));
					} catch (IOException ioException) {
						final ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
						errorMessage.showThenClose();
					}

					// Refresh list
					mainPage.callRefreshEvents();
				}
			});

			// New button in MainPage
			mainPage.addNewEvent(() -> {
				final NewPrompt newPrompt = new NewPrompt(mainPage.root());

				// Create button
				newPrompt.addCreateEvent(fileName -> {
					// Show error when user entered nothing
					if (fileName.equals("")) {
						newPrompt.showError("Please enter a script name.");
						return;
					}

					// Create new file with default values
					try {
						Files.createFile(Paths.get(scriptFolder + "/" + fileName + ".txt"));

						final BufferedWriter writer = Files
								.newBufferedWriter(Paths.get(scriptFolder + "/" + fileName + ".txt"));
						writer.write("\n");
						writer.write("1\n");
						writer.write("100\n");
						writer.write("1000");
						writer.close();
					} catch (FileAlreadyExistsException fileAlreadyExistsException) {
						// When the file already exists
						newPrompt.showError("File already exists.");
						return;
					} catch (IOException ioException) {
						// Any other unexpected error
						final ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
						errorMessage.showThenClose();
					}

					// Refresh List
					mainPage.callRefreshEvents();

					// Remove prompt
					newPrompt.removePrompt();

					// Go to EditPage
					mainPage.searchableList().select(fileName);
					mainPage.callEditEvents();
				});

				// Cancel button
				newPrompt.addCancelEvents(() -> {
					newPrompt.removePrompt();
				});

				newPrompt.show();
			});

			// Show start key of selected script
			mainPage.searchableList().addSelectionEvent(item -> {
				final String fileName = mainPage.searchableList().selectedItem();
				final Path filePath = Paths.get(scriptFolder + "/" + fileName + ".txt");

				// Show key
				if (macro.duplicate().containsKey(filePath) == false) {
					try {
						final BufferedReader reader = Files.newBufferedReader(filePath);
						try {
							final int key = Integer.parseInt(reader.readLine());
							final String keyText = KeyEvent.getKeyText(key);
							mainPage.showKey(keyText, false);
						} catch (NumberFormatException numberFormatException) {
							// Start key is not defined yet
							mainPage.showKey("", false);
						}
						reader.close();
					} catch (IOException ioException) {
						ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
						errorMessage.showThenClose();
					}
					return;
				}

				// Show duplicated key
				if (macro.duplicate().containsKey(filePath)) {
					final Path duplicatePath = macro.duplicate().get(filePath);
					final String duplicateFileName = duplicatePath.getFileName().toString().split(".txt")[0];
					mainPage.showKey("Duplicate with " + duplicateFileName, true);
					return;
				}
			});

			// Key button in MainPage
			mainPage.addKeyEvent(() -> {
				// Making sure an item is selected
				if (mainPage.searchableList().selectedItem() != null) {
					// Remove macro for a bit
					GlobalScreen.removeNativeKeyListener(macro);

					// Initialize Key Prompt
					final KeyPrompt keyPrompt = new KeyPrompt(mainPage.root());

					// Show initial key
					try {
						final BufferedReader reader = Files.newBufferedReader(
								Paths.get(scriptFolder + "/" + mainPage.searchableList().selectedItem() + ".txt"));
						final String firstLine = reader.readLine();
						reader.close();
						keyPrompt.showText(KeyEvent.getKeyText(Integer.parseInt(firstLine)));
					} catch (NumberFormatException numberFormatException) {
						// Invalid start key
					} catch (IOException ioException) {
						ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
						errorMessage.showThenClose();
					}

					// Container for currently selected key
					Integer[] selectedKey = new Integer[1];

					// Mark currently selected key
					keyPrompt.addKeyEvents(keyCode -> {
						selectedKey[0] = keyCode;
					});

					// Save button of KeyPrompt
					keyPrompt.addSaveEvent(key -> {
						if (selectedKey[0] != null) {
							try {
								// Currently selected path
								final Path currentPath = Paths
										.get(scriptFolder + "/" + mainPage.searchableList().selectedItem() + ".txt");

								// Copy everything in the file.
								final List<String> copy = new ArrayList<String>();
								final BufferedReader reader = Files.newBufferedReader(currentPath);
								String line = reader.readLine();
								while (line != null) {
									copy.add(line);
									line = reader.readLine();
								}
								reader.close();

								// Write selected key and the rest of copy
								final BufferedWriter writer = Files.newBufferedWriter(currentPath);
								writer.write(Integer.toString(selectedKey[0]) + "\n");
								for (int x = 1; x < copy.size(); x++) {
									writer.write(copy.get(x) + "\n");
								}
								writer.close();

								// Refresh key data
								macro.refreshKeyData();

								// Select the item again to refresh key text
								final String item = mainPage.searchableList().selectedItem();
								mainPage.callRefreshEvents();
								mainPage.searchableList().select(item);
							} catch (Exception exception) {
								ErrorMessage errorMessage = new ErrorMessage(exception, stage);
								errorMessage.showThenClose();
							}
						}

						// Add macro back
						GlobalScreen.addNativeKeyListener(macro);
					});
					
					// Display KeyPrompt
					keyPrompt.show();
				}
			});
			
			// Show running status of macro
			macro.addStartEvent(scriptName->{
				Platform.runLater(()->{
					mainPage.showAsRunning(scriptName);
				});
			});
			
			macro.addCloseEvent(()->{
				Platform.runLater(()->{
					mainPage.showAsReady();
				});
			});

			// Turn on Script when scene is changed from MainPage to EditPage
			mainPage.addEditEvent(() -> {
				this.script = new Script(settings.recordKey().value());

				// When it starts to record
				this.script.addOnEvent(() -> {
					Platform.runLater(()->{
						editPage.showRecording(true);
					});
				});

				// When it ends recording
				this.script.addOffEvent(() -> {
					Platform.runLater(()->{
						editPage.showRecording(false);
					});
				});

				// When a command is recorded
				this.script.addRecordEvent(command -> {
					Platform.runLater(()->{
						editPage.scriptArea().appendText(command + "\n");
					});
				});

				// Register to GlboalScreen
				GlobalScreen.addNativeKeyListener(this.script);
				GlobalScreen.addNativeMouseListener(this.script);
				GlobalScreen.addNativeMouseWheelListener(this.script);
			});
			
			// Turn off Script when scene is changed from EditPage to MainPage
			editPage.addMainEvnet(()->{
				GlobalScreen.removeNativeKeyListener(this.script);
				GlobalScreen.removeNativeMouseListener(this.script);
				GlobalScreen.removeNativeMouseWheelListener(this.script);
			});
			
			// Only run macro when current scene is MainPage
			stage.sceneProperty().addListener(observable->{
				if (stage.getScene().equals(mainPage.body())){
					GlobalScreen.addNativeKeyListener(macro);
				} else {
					GlobalScreen.removeNativeKeyListener(macro);
				}
			});
			
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
			exception.printStackTrace();
			
			// Show error message when an exception occurs and then close the program.
			ErrorMessage errorMessage = new ErrorMessage(exception, stage);
			errorMessage.showThenClose();
		}
	}

	private Script script;
	
	/**
	 * JavaFX thread will be executed.
	 * @param args
	 * @since 1.0.0
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
