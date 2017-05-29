package page.edit;

import java.awt.event.KeyEvent;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import page.common.CorrectKey;

public class Script implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener {

	public Script(int recordKey, Label lReady, TextArea taScript) {
		this.recordKey = recordKey;
		this.lReady = lReady;
		this.taScript = taScript;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		// CorrectKey
		final CorrectKey correctKey = new CorrectKey();

		// Key Code
		int keyCode = correctKey.convert(key.getKeyCode(), key.getRawCode());

		// Turn on and off recording
		if (recordKey == keyCode) {
			if (started == false) {
				started = true;
				Platform.runLater(() -> {
					lReady.setStyle("-fx-border-color: silver; -fx-font-weight: bold; -fx-text-fill: limegreen;");
					lReady.setText("Recording");
				});
			} else {
				started = false;
				Platform.runLater(() -> {
					lReady.setStyle("-fx-border-color: silver; -fx-text-fill: black;");
					lReady.setText("Ready");
				});
			}
		}

		// Recording implementation
		if (started) {
			if (recordKey != keyCode) {
				String command = "kp(" + Integer.toString(keyCode) + "): " + KeyEvent.getKeyText(keyCode);

				showCommand(command);
				showCommand("sleep(auto1)");
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent key) {
		// CorrectKey
		final CorrectKey correctKey = new CorrectKey();

		// Key Code
		int keyCode = correctKey.convert(key.getKeyCode(), key.getRawCode());

		// Recording implementation
		if (started) {
			if (recordKey != keyCode) {
				String command = "kr(" + Integer.toString(keyCode) + "): " + KeyEvent.getKeyText(keyCode);

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

	@Override
	public void nativeMousePressed(NativeMouseEvent mouse) {
		// Recording implementation
		if (started) {
			// XY position of mouse cursor
			int xPos = mouse.getX();
			int yPos = mouse.getY();

			showCommand("pos(" + xPos + "," + yPos + ")");
			showCommand("mp" + mouse.getButton());
			showCommand("sleep(auto1)");
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent mouse) {
		// Recording implementation
		if (started) {
			// XY position of mouse cursor
			int xPos = mouse.getX();
			int yPos = mouse.getY();

			showCommand("pos(" + xPos + "," + yPos + ")");
			showCommand("mr" + mouse.getButton());
			showCommand("sleep(auto2)");
		}
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent wheel) {
		// Recording implementation
		if (started) {
			showCommand("wheel(" + wheel.getWheelRotation() + ")");
			showCommand("sleep(auto1)");
		}
	}

	private void showCommand(String line) {
		Platform.runLater(() -> {
			taScript.appendText(line + "\n");
		});
	}

	private boolean started = false;

	private final int recordKey;

	private final Label lReady;
	private final TextArea taScript;

}
