/*******************************************************************************
 * -------------------
 * --- Vulc Engine ---
 * -------------------
 * Copyright 2019-2021 Vulcalien
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.engine;

import vulc.engine.gfx.GameFrame;
import vulc.engine.gfx.Screen;
import vulc.engine.input.InputHandler;
import vulc.engine.input.KeyBindings;
import vulc.engine.level.Level;
import vulc.engine.level.entity.Player;

public abstract class Game {

	private Game() {
	}

	// the size of the game screen (not the Frame)
	public static final int WIDTH = 1280, HEIGHT = 720;

	// the number of Frame's pixels that correspond to 1 pixel of the game screen
	public static final int SCALE = 1;

	public static final double SCREEN_RATIO = 1.0 * WIDTH / HEIGHT;

	public static final String GAME_NAME = "Dante Game - by...";

	protected static GameFrame frame;

	public static final InputHandler INPUT = new InputHandler();

	public static Level level;
	public static Player player;

	private static void init() {
		level = new Level(20, 12);
		level.addEntity(player = new Player());
	}

	public static void tick() {
		INPUT.tick();

		if(level != null) level.tick();

		// DEBUG
		if(KeyBindings.DEBUG.released()) {
			createFrame(!frame.isFullScreen);
			frame.setVisible(true);
		}
	}

	public static void render(Screen screen) {
		screen.clear(0x000000);

		if(level != null) {
			// the screen is 20x11.25 tiles
			level.render(screen, 21, 13);
		}
	}

	private static void createFrame(boolean isFullScreen) {
		if(frame != null) frame.destroyFrame();
		frame = new GameFrame("game name", WIDTH, HEIGHT, SCALE, isFullScreen);
		frame.initFrame();
	}

	public static void main(String[] args) {
		createFrame(false);
		frame.setVisible(true);

		init();
		GameLoop.start();
	}

}
