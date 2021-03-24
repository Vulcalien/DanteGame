package vulc.dantegame.input;

import java.awt.event.KeyEvent;

import vulc.dantegame.Game;

public final class KeyBindings {
	private KeyBindings() {
	}

	public static final InputHandler.Key ESCAPE = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_ESCAPE);
	public static final InputHandler.Key F11 = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_F11);

	public static final InputHandler.Key W = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_W);
	public static final InputHandler.Key A = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_A);
	public static final InputHandler.Key S = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_S);
	public static final InputHandler.Key D = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_D);

	public static final InputHandler.Key UP = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_UP);
	public static final InputHandler.Key LEFT = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_LEFT);
	public static final InputHandler.Key DOWN = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_DOWN);
	public static final InputHandler.Key RIGHT = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_RIGHT);

	public static final InputHandler.Key ENTER = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_ENTER);
	public static final InputHandler.Key SPACE = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_SPACE);
	public static final InputHandler.Key E = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_E);

}
