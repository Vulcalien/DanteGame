package vulc.engine.input;

import java.awt.event.KeyEvent;

import vulc.engine.Game;

public final class KeyBindings {
	private KeyBindings() {
	}

	public static final InputHandler.Key DEBUG = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_9);

	public static final InputHandler.Key W = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_W);
	public static final InputHandler.Key A = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_A);
	public static final InputHandler.Key S = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_S);
	public static final InputHandler.Key D = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_D);

	public static final InputHandler.Key UP = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_UP);
	public static final InputHandler.Key LEFT = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_LEFT);
	public static final InputHandler.Key DOWN = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_DOWN);
	public static final InputHandler.Key RIGHT = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_RIGHT);

}
