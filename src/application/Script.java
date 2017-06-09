package application;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import utility.event.PlainEvent;
import utility.event.StringEvent;

/**
 * Record keyboard and mouse actions. <br />
 * <b>JNativeHook</b>, <b>PlainEvent</b>, and <b>StringEvent</b> are required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class Script implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener {
	/**
	 * Record Key is required.
	 * @param recordKey keyboard key to start recording
	 * @since 1.0.0
	 */
	public Script(int recordKey) {
		this.recordKey = recordKey;
	}
	
	/**
	 * Turn on and off recording. <br />
	 * Also record keyboard press.
	 * @since 1.0.0
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		// CorrectKey
		final CorrectKey correctKey = new CorrectKey();

		// Key Code
		final int keyCode = correctKey.convert(key.getKeyCode(), key.getRawCode());

		// Turn on and off recording
		if (recordKey == keyCode) {
			if (started == false) {
				started = true;
				for (PlainEvent event : onEvents){
					event.handle();
				}
			} else {
				started = false;
				for (PlainEvent event : offEvents){
					event.handle();
				}
			}
		}

		// Recording implementation
		if (started) {
			if (recordKey != keyCode) {
				final String command = "kp(" + Integer.toString(keyCode) + "): " + KeyEvent.getKeyText(keyCode);
				showCommand(command);
				showCommand("sleep(auto1)");
			}
		}
	}
	
	/**
	 * Record keyboard release.
	 * @since 1.0.0
	 */
	@Override
	public void nativeKeyReleased(NativeKeyEvent key) {
		// CorrectKey
		final CorrectKey correctKey = new CorrectKey();

		// Key Code
		final int keyCode = correctKey.convert(key.getKeyCode(), key.getRawCode());

		// Recording implementation
		if (started) {
			if (recordKey != keyCode) {
				final String command = "kr(" + Integer.toString(keyCode) + "): " + KeyEvent.getKeyText(keyCode);

				showCommand(command);
				showCommand("sleep(auto2)");
			}
		}

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Record mouse cursor position and mouse press.
	 * @since 1.0.0
	 */
	@Override
	public void nativeMousePressed(NativeMouseEvent mouse) {
		// Recording implementation
		if (started) {
			// XY position of mouse cursor
			final int xPos = mouse.getX();
			final int yPos = mouse.getY();

			showCommand("pos(" + xPos + "," + yPos + ")");
			showCommand("mp" + mouse.getButton());
			showCommand("sleep(auto1)");
		}
	}
	
	/**
	 * Record mouse cursor position and mouse release.
	 * @since 1.0.0
	 */
	@Override
	public void nativeMouseReleased(NativeMouseEvent mouse) {
		// Recording implementation
		if (started) {
			// XY position of mouse cursor
			final int xPos = mouse.getX();
			final int yPos = mouse.getY();

			showCommand("pos(" + xPos + "," + yPos + ")");
			showCommand("mr" + mouse.getButton());
			showCommand("sleep(auto2)");
		}
	}
	
	/**
	 * Record mouse wheel.
	 * @since 1.0.0
	 */
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent wheel) {
		// Recording implementation
		if (started) {
			showCommand("wheel(" + wheel.getWheelRotation() + ")");
			showCommand("sleep(auto1)");
		}
	}
	
	/**
	 * When it starts to record, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addOnEvent(PlainEvent event){
		onEvents.add(event);
	}
	
	/**
	 * When it ends record, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addOffEvent(PlainEvent event){
		offEvents.add(event);
	}
	
	/**
	 * When new command is recorded, the specified action will be executed. <br />
	 * The parameter of event would be a command.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRecordEvent(StringEvent event){
		recordEvents.add(event);
	}
	
	/**
	 * Call record events.
	 * @param line A command
	 * @since 1.0.0
	 */
	private void showCommand(String line) {
		for (StringEvent event : recordEvents){
			event.handle(line);
		}
	}

	private boolean started = false;
	
	private final List<StringEvent> recordEvents = new ArrayList<StringEvent>();
	private final List<PlainEvent> onEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> offEvents = new ArrayList<PlainEvent>();
	private final int recordKey;

}