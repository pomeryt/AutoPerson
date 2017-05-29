package page.main;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
	public void execute(String command, String auto1, String auto2) throws Exception {
		
		// Robot
		final Robot robot = new Robot();
		
		// Sleep with auto1
		if (command.contains("sleep(auto1)")){
			double sleep = Double.parseDouble(auto1);
			long start = System.currentTimeMillis();
			long time = 0;
			while (time < sleep) {
				if (stop) {
					break;
				}
				Thread.sleep(1);
				time = System.currentTimeMillis() - start;
			}
			return;
		}
		
		// Sleep with auto2
		if (command.contains("sleep(auto2)")){
			double sleep = Double.parseDouble(auto2);
			long start = System.currentTimeMillis();
			long time = 0;
			while (time < sleep) {
				if (stop) {
					break;
				}
				Thread.sleep(1);
				time = System.currentTimeMillis() - start;
			}
			return;
		}

		// Sleep with digits
		final Pattern patternSleep3 = Pattern.compile("sleep[(](\\d+)[)]");
		final Matcher matcherSleep3 = patternSleep3.matcher(command);
		if (matcherSleep3.find()) {
			double sleep = Double.parseDouble(matcherSleep3.group(1));
			long start = System.currentTimeMillis();
			long time = 0;
			while (time < sleep) {
				if (stop) {
					break;
				}
				Thread.sleep(1);
				time = System.currentTimeMillis() - start;
			}
			return;
		}

		// Key Press
		final Pattern patternKp = Pattern.compile("kp[(](\\d+)[)]");
		final Matcher matcherKp = patternKp.matcher(command);
		if (matcherKp.find()) {
			robot.keyPress(Integer.parseInt(matcherKp.group(1)));
			return;
		}

		// Key Release
		final Pattern patternKr = Pattern.compile("kr[(](\\d+)[)]");
		final Matcher matcherKr = patternKr.matcher(command);
		if (matcherKr.find()) {
			robot.keyRelease(Integer.parseInt(matcherKr.group(1)));
			return;
		}

		// Position
		final Pattern patternPos = Pattern.compile("pos[(](\\d+),(\\d+)[)]");
		final Matcher matcherPos = patternPos.matcher(command);
		if (matcherPos.find()) {
			robot.mouseMove(Integer.parseInt(matcherPos.group(1)), Integer.parseInt(matcherPos.group(2)));
			return;
		}
		
		// Wheel
		final Pattern patternWheel = Pattern.compile("wheel[(](-?\\d+)[)]");
		final Matcher matcherWheel = patternWheel.matcher(command);
		if (matcherWheel.find()) {
			robot.mouseWheel(Integer.parseInt(matcherWheel.group(1)));
			return;
		}

		// Mouse Left Press
		if (command.contains("mp1")) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			return;
		}

		// Mouse Left Release
		if (command.contains("mr1")) {
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			return;
		}

		// Mouse Right Press
		if (command.contains("mp2")) {
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			return;
		}

		// Mouse Right Release
		if (command.contains("mr2")) {
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			return;
		}

		// Mouse Wheel Press
		if (command.contains("mp3")) {
			robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
			return;
		}

		// Mouse Wheel Release
		if (command.contains("mr3")) {
			robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
			return;
		}
	}

	public void stop() {
		this.stop = true;
	}

	private boolean stop = false;
}
