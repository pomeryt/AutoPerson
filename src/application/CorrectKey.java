package application;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * This class is used to provide appropriate key code from native key code.
 * @author Rin
 * @since 1.0.0
 */
public class CorrectKey {
	/**
	 * Maps corresponding native key code to appropriate AWT key code.
	 * @since 1.0.0
	 */
	public CorrectKey(){
		keyMap.put(NativeKeyEvent.KEY_LOCATION_LEFT, KeyEvent.KEY_LOCATION_LEFT);
		keyMap.put(NativeKeyEvent.KEY_LOCATION_NUMPAD, KeyEvent.KEY_LOCATION_NUMPAD);
		keyMap.put(NativeKeyEvent.KEY_LOCATION_RIGHT, KeyEvent.KEY_LOCATION_RIGHT);
		keyMap.put(NativeKeyEvent.KEY_LOCATION_STANDARD, KeyEvent.KEY_LOCATION_STANDARD);
		keyMap.put(NativeKeyEvent.KEY_LOCATION_UNKNOWN, KeyEvent.KEY_LOCATION_UNKNOWN);
		keyMap.put(NativeKeyEvent.NATIVE_KEY_FIRST, KeyEvent.KEY_FIRST);
		keyMap.put(NativeKeyEvent.NATIVE_KEY_LAST, KeyEvent.KEY_LAST);
		keyMap.put(NativeKeyEvent.NATIVE_KEY_PRESSED, KeyEvent.KEY_PRESSED);
		keyMap.put(NativeKeyEvent.NATIVE_KEY_RELEASED, KeyEvent.KEY_RELEASED);
		keyMap.put(NativeKeyEvent.NATIVE_KEY_TYPED, KeyEvent.KEY_TYPED);
		keyMap.put(NativeKeyEvent.VC_0, KeyEvent.VK_0);
		keyMap.put(NativeKeyEvent.VC_1, KeyEvent.VK_1);
		keyMap.put(NativeKeyEvent.VC_2, KeyEvent.VK_2);
		keyMap.put(NativeKeyEvent.VC_3, KeyEvent.VK_3);
		keyMap.put(NativeKeyEvent.VC_4, KeyEvent.VK_4);
		keyMap.put(NativeKeyEvent.VC_5, KeyEvent.VK_5);
		keyMap.put(NativeKeyEvent.VC_6, KeyEvent.VK_6);
		keyMap.put(NativeKeyEvent.VC_7, KeyEvent.VK_7);
		keyMap.put(NativeKeyEvent.VC_8, KeyEvent.VK_8);
		keyMap.put(NativeKeyEvent.VC_9, KeyEvent.VK_9);
		keyMap.put(NativeKeyEvent.VC_A, KeyEvent.VK_A);
		keyMap.put(NativeKeyEvent.VC_ALT, KeyEvent.VK_ALT);
		keyMap.put(NativeKeyEvent.VC_B, KeyEvent.VK_B);
		keyMap.put(NativeKeyEvent.VC_BACK_SLASH, KeyEvent.VK_BACK_SLASH);
		keyMap.put(NativeKeyEvent.VC_BACKQUOTE, KeyEvent.VK_BACK_QUOTE);
		keyMap.put(NativeKeyEvent.VC_BACKSPACE, KeyEvent.VK_BACK_SPACE);
		keyMap.put(NativeKeyEvent.VC_C, KeyEvent.VK_C);
		keyMap.put(NativeKeyEvent.VC_CAPS_LOCK, KeyEvent.VK_CAPS_LOCK);
		keyMap.put(NativeKeyEvent.VC_CLEAR, KeyEvent.VK_CLEAR);
		keyMap.put(NativeKeyEvent.VC_CLOSE_BRACKET, KeyEvent.VK_CLOSE_BRACKET);
		keyMap.put(NativeKeyEvent.VC_COMMA, KeyEvent.VK_COMMA);
		keyMap.put(NativeKeyEvent.VC_CONTEXT_MENU, KeyEvent.VK_CONTEXT_MENU);
		keyMap.put(NativeKeyEvent.VC_CONTROL, KeyEvent.VK_CONTROL);
		keyMap.put(NativeKeyEvent.VC_D, KeyEvent.VK_D);
		keyMap.put(NativeKeyEvent.VC_DELETE, KeyEvent.VK_DELETE);
		keyMap.put(NativeKeyEvent.VC_DOWN, KeyEvent.VK_DOWN);
		keyMap.put(NativeKeyEvent.VC_E, KeyEvent.VK_E);
		keyMap.put(NativeKeyEvent.VC_END, KeyEvent.VK_END);
		keyMap.put(NativeKeyEvent.VC_ENTER, KeyEvent.VK_ENTER);
		keyMap.put(NativeKeyEvent.VC_EQUALS, KeyEvent.VK_EQUALS);
		keyMap.put(NativeKeyEvent.VC_ESCAPE, KeyEvent.VK_ESCAPE);
		keyMap.put(NativeKeyEvent.VC_F, KeyEvent.VK_F);
		keyMap.put(NativeKeyEvent.VC_F1, KeyEvent.VK_F1);
		keyMap.put(NativeKeyEvent.VC_F10, KeyEvent.VK_F10);
		keyMap.put(NativeKeyEvent.VC_F11, KeyEvent.VK_F11);
		keyMap.put(NativeKeyEvent.VC_F12, KeyEvent.VK_F12);
		keyMap.put(NativeKeyEvent.VC_F13, KeyEvent.VK_F13);
		keyMap.put(NativeKeyEvent.VC_F14, KeyEvent.VK_F14);
		keyMap.put(NativeKeyEvent.VC_F15, KeyEvent.VK_F15);
		keyMap.put(NativeKeyEvent.VC_F16, KeyEvent.VK_F16);
		keyMap.put(NativeKeyEvent.VC_F17, KeyEvent.VK_F17);
		keyMap.put(NativeKeyEvent.VC_F18, KeyEvent.VK_F18);
		keyMap.put(NativeKeyEvent.VC_F19, KeyEvent.VK_F19);
		keyMap.put(NativeKeyEvent.VC_F2, KeyEvent.VK_F2);
		keyMap.put(NativeKeyEvent.VC_F20, KeyEvent.VK_F20);
		keyMap.put(NativeKeyEvent.VC_F21, KeyEvent.VK_F21);
		keyMap.put(NativeKeyEvent.VC_F22, KeyEvent.VK_F22);
		keyMap.put(NativeKeyEvent.VC_F23, KeyEvent.VK_F23);
		keyMap.put(NativeKeyEvent.VC_F24, KeyEvent.VK_F24);
		keyMap.put(NativeKeyEvent.VC_F3, KeyEvent.VK_F3);
		keyMap.put(NativeKeyEvent.VC_F4, KeyEvent.VK_F4);
		keyMap.put(NativeKeyEvent.VC_F5, KeyEvent.VK_F5);
		keyMap.put(NativeKeyEvent.VC_F6, KeyEvent.VK_F6);
		keyMap.put(NativeKeyEvent.VC_F7, KeyEvent.VK_F7);
		keyMap.put(NativeKeyEvent.VC_F8, KeyEvent.VK_F8);
		keyMap.put(NativeKeyEvent.VC_F9, KeyEvent.VK_F9);
		keyMap.put(NativeKeyEvent.VC_G, KeyEvent.VK_G);
		keyMap.put(NativeKeyEvent.VC_H, KeyEvent.VK_H);
		keyMap.put(NativeKeyEvent.VC_HIRAGANA, KeyEvent.VK_HIRAGANA);
		keyMap.put(NativeKeyEvent.VC_HOME, KeyEvent.VK_HOME);
		keyMap.put(NativeKeyEvent.VC_I, KeyEvent.VK_I);
		keyMap.put(NativeKeyEvent.VC_INSERT, KeyEvent.VK_INSERT);
		keyMap.put(NativeKeyEvent.VC_J, KeyEvent.VK_J);
		keyMap.put(NativeKeyEvent.VC_K, KeyEvent.VK_K);
		keyMap.put(NativeKeyEvent.VC_KANJI, KeyEvent.VK_KANJI);
		keyMap.put(NativeKeyEvent.VC_KATAKANA, KeyEvent.VK_KATAKANA);
		keyMap.put(NativeKeyEvent.VC_L, KeyEvent.VK_L);
		keyMap.put(NativeKeyEvent.VC_LEFT, KeyEvent.VK_LEFT);
		keyMap.put(NativeKeyEvent.VC_M, KeyEvent.VK_M);
		keyMap.put(NativeKeyEvent.VC_META, KeyEvent.VK_META);
		keyMap.put(NativeKeyEvent.VC_MINUS, KeyEvent.VK_MINUS);
		keyMap.put(NativeKeyEvent.VC_N, KeyEvent.VK_N);
		keyMap.put(NativeKeyEvent.VC_NUM_LOCK, KeyEvent.VK_NUM_LOCK);
		keyMap.put(NativeKeyEvent.VC_O, KeyEvent.VK_O);
		keyMap.put(NativeKeyEvent.VC_OPEN_BRACKET, KeyEvent.VK_OPEN_BRACKET);
		keyMap.put(NativeKeyEvent.VC_P, KeyEvent.VK_P);
		keyMap.put(NativeKeyEvent.VC_PAGE_DOWN, KeyEvent.VK_PAGE_DOWN);
		keyMap.put(NativeKeyEvent.VC_PAGE_UP, KeyEvent.VK_PAGE_UP);
		keyMap.put(NativeKeyEvent.VC_PAUSE, KeyEvent.VK_PAUSE);
		keyMap.put(NativeKeyEvent.VC_PERIOD, KeyEvent.VK_PERIOD);
		keyMap.put(NativeKeyEvent.VC_PRINTSCREEN, KeyEvent.VK_PRINTSCREEN);
		keyMap.put(NativeKeyEvent.VC_Q, KeyEvent.VK_Q);
		keyMap.put(NativeKeyEvent.VC_QUOTE, KeyEvent.VK_QUOTE);
		keyMap.put(NativeKeyEvent.VC_R, KeyEvent.VK_R);
		keyMap.put(NativeKeyEvent.VC_RIGHT, KeyEvent.VK_RIGHT);
		keyMap.put(NativeKeyEvent.VC_S, KeyEvent.VK_S);
		keyMap.put(NativeKeyEvent.VC_SCROLL_LOCK, KeyEvent.VK_SCROLL_LOCK);
		keyMap.put(NativeKeyEvent.VC_SEMICOLON, KeyEvent.VK_SEMICOLON);
		keyMap.put(NativeKeyEvent.VC_SEPARATOR, KeyEvent.VK_SEPARATOR);
		keyMap.put(NativeKeyEvent.VC_SHIFT, KeyEvent.VK_SHIFT);
		keyMap.put(NativeKeyEvent.VC_SLASH, KeyEvent.VK_SLASH);
		keyMap.put(NativeKeyEvent.VC_SPACE, KeyEvent.VK_SPACE);
		keyMap.put(NativeKeyEvent.VC_SUN_AGAIN, KeyEvent.VK_AGAIN);
		keyMap.put(NativeKeyEvent.VC_SUN_COPY, KeyEvent.VK_COPY);
		keyMap.put(NativeKeyEvent.VC_SUN_CUT, KeyEvent.VK_CUT);
		keyMap.put(NativeKeyEvent.VC_SUN_FIND, KeyEvent.VK_FIND);
		keyMap.put(NativeKeyEvent.VC_SUN_HELP, KeyEvent.VK_HELP);
		keyMap.put(NativeKeyEvent.VC_SUN_PROPS, KeyEvent.VK_PROPS);
		keyMap.put(NativeKeyEvent.VC_SUN_UNDO, KeyEvent.VK_UNDO);
		keyMap.put(NativeKeyEvent.VC_T, KeyEvent.VK_T);
		keyMap.put(NativeKeyEvent.VC_TAB, KeyEvent.VK_TAB);
		keyMap.put(NativeKeyEvent.VC_U, KeyEvent.VK_U);
		keyMap.put(NativeKeyEvent.VC_UNDEFINED, KeyEvent.VK_UNDEFINED);
		keyMap.put(NativeKeyEvent.VC_UNDERSCORE, KeyEvent.VK_UNDERSCORE);
		keyMap.put(NativeKeyEvent.VC_UP, KeyEvent.VK_UP);
		keyMap.put(NativeKeyEvent.VC_V, KeyEvent.VK_V);
		keyMap.put(NativeKeyEvent.VC_W, KeyEvent.VK_W);
		keyMap.put(NativeKeyEvent.VC_X, KeyEvent.VK_X);
		keyMap.put(NativeKeyEvent.VC_Y, KeyEvent.VK_Y);
		keyMap.put(NativeKeyEvent.VC_Z, KeyEvent.VK_Z);
		keyMap.put(NativeKeyEvent.ALT_MASK, KeyEvent.ALT_MASK);
		keyMap.put(NativeKeyEvent.BUTTON1_MASK, KeyEvent.BUTTON1_MASK);
		keyMap.put(NativeKeyEvent.BUTTON2_MASK, KeyEvent.BUTTON2_MASK);
		keyMap.put(NativeKeyEvent.BUTTON3_MASK, KeyEvent.BUTTON3_MASK);
		// keyMap.put(NativeKeyEvent.CTRL_MASK, KeyEvent.CTRL_MASK); duplicate with VC_G
		// keyMap.put(NativeKeyEvent.META_MASK, KeyEvent.META_MASK); duplicate with F10
		// keyMap.put(NativeKeyEvent.SHIFT_MASK, KeyEvent.SHIFT_MASK); duplicate with VC_W
		keyMap.put(0xe36, KeyEvent.VK_SHIFT); // Right shift to regular shift
		keyMap.put(0xe4e, KeyEvent.VK_PLUS); // Plus on number pad
		keyMap.put(0xe4a, KeyEvent.VK_MINUS); // Minus on number pad
	}
	
	/**
	 * Provide AWT key code from either native key code or native raw code.
	 * @param keyCode native key code
	 * @param rawCode native raw key code
	 * @return corresponding AWT key code
	 * @since 1.0.0
	 */
	public int convert(int keyCode, int rawCode){
		// Convert with raw key code
		final Integer correctRaw = convertRaw(rawCode);
		if (correctRaw != null){
			return correctRaw;
		}
		
		// Convert with key code
		final Integer correctKey = keyMap.get(keyCode);
		if (correctKey == null){
			return KeyEvent.VK_UNDEFINED;
		}
		return correctKey;
	}
	
	/**
	 * Provide AWT key code from native raw code. <br />
	 * Sometimes, different key press gives same native key code. <br />
	 * In such case, using raw code can differentiate between them.
	 * @param rawCode native raw key code
	 * @return AWT key code
	 * @since 1.0.0
	 */
	private Integer convertRaw(int rawCode){
		switch (rawCode){
		case 106: return KeyEvent.VK_ASTERISK;
		}
		return null;
	}
	
	final private Map<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
}
