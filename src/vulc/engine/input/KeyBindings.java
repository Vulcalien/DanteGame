package vulc.engine.input;

import java.awt.event.KeyEvent;

import vulc.engine.Game;

public final class KeyBindings {
	private KeyBindings() {
	}

	public static final InputHandler.Key DEBUG = Game.INPUT.new Key(InputHandler.KEYBOARD, KeyEvent.VK_A);

}
