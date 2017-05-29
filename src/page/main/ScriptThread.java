package page.main;

import java.util.List;

import utility.event.PlainEvent;

public class ScriptThread extends Thread {
	public ScriptThread(String[] settings, List<String> script, PlainEvent closeEvent){
		this.loop = settings[0];
		this.auto1 = settings[1];
		this.auto2 = settings[2];
		this.script = script;
		this.closeEvent = closeEvent;
	}
	
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
				process();
			}
		} catch (NumberFormatException numberFormatException){}
		
		// Close
		closeEvent.handle();
	}
	
	public void end(){
		stop = true;
		command.stop();
	}
	
	private void process(){
		// Execute each line of script
		for (String line : script){
			if (stop){
				break;
			}
			
			try {
				command.execute(line, auto1, auto2);
			} catch (Exception exception) {
				// Unknown Key
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
