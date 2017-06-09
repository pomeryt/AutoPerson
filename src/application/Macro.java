package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.event.PlainEvent;
import utility.event.StringEvent;

/**
 * It listens to keyboard action to start, cancel, or terminate executing script. <br />
 * It also detects duplicated start keys. <br />
 * <b>JNativeHook</b>, <b>ErrorMessage</b>, <b>PlainEvent</b>, and <b>StringEvent</b> are required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class Macro implements NativeKeyListener {
	/**
	 * Stores start keys and those duplicates.
	 * @param scriptFolder
	 * @param stage
	 * @throws IOException
	 * @since 1.0.0
	 */
	public Macro(String scriptFolder, Stage stage) throws IOException {
		this.scriptFolder = scriptFolder;
		this.stage = stage;
		refreshKeyData();
	}
	
	/**
	 * Start, cancel, or terminate executing script. <br />
	 * Note, close events will be executed when script is cancelled.
	 * @since 1.0.0
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		// Key Code
		final int keyCode = key.getRawCode();
		
		// Corresponding path
		final Path path = keyMap.get(keyCode);
		
		// Unimportant key is pressed
		if (path == null){
			return;
		}
		
		// Cancel script
		if (started && keyCode == runningKey){
			// Mark as stopped
			started = false;
			
			// Call close events
			for (PlainEvent event : closeEvents){
				event.handle();
			}
			
			// Stop script thread
			scriptThread.end();
			
			return;
		}
		
		// Quit current script
		if (started && runningKey != null && keyCode != runningKey){
			// Mark as stopped
			started = false;
			
			// Stop script thread
			scriptThread.end();
		}
		
		// Start new script
		{
			// Mark as running
			started = true;
			
			// Mark running key
			runningKey = keyCode;
			
			// Script name
			final String scriptName = keyMap.get(keyCode).getFileName().toString().split(".txt")[0];
			
			// Call start events
			for (StringEvent event : startEvents){
				event.handle(scriptName);
			}
			
			// Loop of script
			String loop = "";
			String auto1 = "";
			String auto2 = "";
			
			// Read script and store into ArrayList
			final List<String> script = new ArrayList<String>();
			try {
				final BufferedReader reader = Files.newBufferedReader(path);
				
				reader.readLine();
				loop = reader.readLine();
				auto1 = reader.readLine();
				auto2 = reader.readLine();
				
				String line = reader.readLine();
				while (line != null){
					script.add(line);
					line = reader.readLine();
				}
				reader.close();
				
			} catch (Exception exception){
				// Error
				final ErrorMessage errorMessage = new ErrorMessage(exception, stage);
				errorMessage.showThenClose();
			}
			
			// Settings in array
			final String[] settings = {loop, auto1, auto2};
			
			// Define close event of ScriptThread
			final PlainEvent closeEvent = new PlainEvent(){
				@Override
				public void handle() {
					// Mark as stopped
					started = false;
					
					// Call close events
					for (PlainEvent event : closeEvents){
						event.handle();
					}
				}
			};
			
			// Start script thread
			scriptThread = new ScriptThread(settings, script, closeEvent);
			scriptThread.start();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Refresh information of start keys and those duplicates.
	 * @throws IOException
	 * @since 1.0.0
	 */
	public void refreshKeyData() throws IOException{
		// Clear key map and duplicate map
		keyMap.clear();
		duplicate.clear();
		
		// populate key map
		final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(scriptFolder));
		for (Path path : stream){
			final BufferedReader reader = Files.newBufferedReader(path);
			try {
				// Key Code
				final int keyCode = Integer.parseInt(reader.readLine());
				
				// Put previous path to duplicate
				if (keyMap.containsKey(keyCode)){
					duplicate.put(keyMap.get(keyCode), path);
				}
				
				// Store keyCode and path
				keyMap.put(keyCode, path);
			} catch (NumberFormatException numberFormatException){
				// Ignore unknown key
			}
			reader.close();
		}
	}
	
	/**
	 * When the thread starts, the specified action will be executed. <br />
	 * The parameter of event would be the name of script.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addStartEvent(StringEvent event){
		startEvents.add(event);
	}
	
	/**
	 * When the thread ends, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addCloseEvent(PlainEvent event){
		closeEvents.add(event);
	}
	
	/**
	 * Return HashMap consists of start keys and those paths.
	 * @return map of start keys
	 * @since 1.0.0
	 */
	public Map<Integer, Path> keyData() {
		return keyMap;
	}
	
	/**
	 * Return HashMap consists of duplicated start keys and those paths.
	 * @return map of duplicated start keys
	 * @since 1.0.0
	 */
	public Map<Path, Path> duplicate(){
		return duplicate;
	}
	
	private boolean started = false;
	private Integer runningKey;
	private ScriptThread scriptThread;
	
	private final List<StringEvent> startEvents = new ArrayList<StringEvent>();
	private final List<PlainEvent> closeEvents = new ArrayList<PlainEvent>();
	private final String scriptFolder;
	private final Stage stage;
	private final Map<Integer, Path> keyMap = new HashMap<Integer, Path>();
	private final Map<Path, Path> duplicate = new HashMap<Path, Path>();
}
