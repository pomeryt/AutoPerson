package application;

import java.util.List;

import utility.event.PlainEvent;

/**
 * Thread for executing script. <br />
 * <b>PlainEvent</b> in utility package is required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class ScriptThread extends Thread {
	/**
	 * An event when thread ends must be defined. <br />
	 * First parameter is an array of script configurations.
	 * @param settings [0]=loop, [1]=auto1, [2]=auto2
	 * @param script Entire script
	 * @param closeEvent An event to be executed when the thread ends
	 * @since 1.0.0
	 */
	public ScriptThread(String[] settings, List<String> script, PlainEvent closeEvent){
		this.loop = settings[0];
		this.auto1 = settings[1];
		this.auto2 = settings[2];
		this.script = script;
		this.closeEvent = closeEvent;
	}
	
	/**
	 * Start thread.
	 * @since 1.0.0
	 */
	@Override
	public void run(){
		// Infinite Loop
		if(loop.equals("i")){
			while (stop == false){
				process();
			}
			return;
		}
		
		// Finite Loop
		try {
			for (int x = 0; x < Integer.parseInt(loop); x++){
				if (stop){
					break;
				}
				process();
			}
		} catch (NumberFormatException numberFormatException){
			// Do nothing if the value of loop is invalid
		}
		
		// Close
		closeEvent.handle();
	}
	
	/**
	 * Terminate the thread.
	 * @since 1.0.0
	 */
	public void end(){
		stop = true;
		command.stop();
	}
	
	/**
	 * Execute the entire script.
	 * @since 1.0.0
	 */
	private void process(){
		// Execute each line of script
		for (String line : script){
			if (stop){
				break;
			}
			
			try {
				command.execute(line, auto1, auto2);
			} catch (Exception exception) {
				// Ignore error and continue
				exception.printStackTrace();
			}
		}
	}
	
	private boolean stop = false;
	private final PlainEvent closeEvent;
	
	private final Command command = new Command();
	private final String loop;
	private final String auto1;
	private final String auto2;
	private final List<String> script;
}
