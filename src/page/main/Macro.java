package page.main;

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

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.event.PlainEvent;

public class Macro implements NativeKeyListener {
	
	public Macro(String scriptFolder, Stage stage, Label lReady) throws IOException {
		this.scriptFolder = scriptFolder;
		this.stage = stage;
		this.lReady = lReady;
		refreshKeyData();
	}
	
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
			
			// Show ready status
			Platform.runLater(()->{
				lReady.setStyle("-fx-border-color: silver;");
				lReady.setText("Ready");
			});
			
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
			
			// Show running status
			Platform.runLater(()->{
				lReady.setStyle("-fx-border-color: silver; -fx-font-weight: bold; -fx-text-fill: limegreen;");
				lReady.setText(scriptName);
			});
			
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
				ErrorMessage errorMessage = new ErrorMessage(exception, stage);
				errorMessage.showThenClose();
			}
			
			// Settings in array
			String[] settings = {loop, auto1, auto2};
			
			// Start script thread
			PlainEvent closeEvent = new PlainEvent(){
				@Override
				public void handle() {
					// Mark as stopped
					started = false;
					
					// Show ready status
					Platform.runLater(()->{
						lReady.setStyle("-fx-border-color: silver;");
						lReady.setText("Ready");
					});
				}
			};
			
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
	
	public void refreshKeyData() throws IOException{
		// Clear key map and duplicate map
		keyMap.clear();
		duplicate.clear();
		
		// populate key map
		DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(scriptFolder));
		for (Path path : stream){
			BufferedReader reader = Files.newBufferedReader(path);
			try {
				// Key Code
				int keyCode = Integer.parseInt(reader.readLine());
				
				// Put previous path to duplicate
				if (keyMap.containsKey(keyCode)){
					duplicate.put(keyMap.get(keyCode), path);
				}
				
				// Store keyCode and path
				keyMap.put(keyCode, path);
			} catch (NumberFormatException numberFormatException){}
			reader.close();
		}
	}
	
	public Map<Integer, Path> keyData() {
		return keyMap;
	}
	
	public Map<Path, Path> duplicate(){
		return duplicate;
	}
	
	private boolean started = false;
	private Integer runningKey;
	private ScriptThread scriptThread;
	
	private final String scriptFolder;
	
	private final Label lReady;
	private final Stage stage;
	
	private final Map<Integer, Path> keyMap = new HashMap<Integer, Path>();
	private final Map<Path, Path> duplicate = new HashMap<Path, Path>();
}
