/*******************************************************************************
 * -------------------
 * --- Vulc Engine ---
 * -------------------
 * Copyright 2019-2021 Vulcalien
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame;

import vulc.dantegame.gfx.GameFrame;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.menu.Menu;
import vulc.dantegame.input.InputHandler;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.mob.MovingPlatform;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.mob.RollingRock;
import vulc.dantegame.level.tile.Tile;

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

	public static Menu menu;
	public static Menu overlay;

	private static void init() {
		// testing stuff
		level = new Level("test-level.bvdf");
//		level = new Level(20, 12);
		level.addEntity(player = new Player());

		// DEBUG uncomment this
//		menu = new StartMenu();

		level.addEntity(new RollingRock(300, 100, -1, 0));
		level.addEntity(new MovingPlatform(1, 3, 10, new int[] {
		    1, 3, 6, 8, 9, 1
		}));
	}

	public static void tick() {
		INPUT.tick();

		if(menu != null) {
			menu.tick();
		} else if(level != null) {
			level.tick();
			Tile.animationTicks++;
		}

		if(overlay != null) {
			overlay.tick();
		}

		// DEBUG
		if(KeyBindings.DEBUG.released()) {
			createFrame(!frame.isFullScreen);
			frame.setVisible(true);
		}
	}

	public static void render(Screen screen) {
		screen.clear(0x000000);

		if(menu != null) {
			menu.render(screen);
		} else if(level != null) {
			// the screen is 20x11.25 tiles
			level.render(screen, 21, 13);
		}

		if(overlay != null) {
			overlay.render(screen);
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

		// DEBUG
		frame.setAlwaysOnTop(true);

		init();
		GameLoop.start();
	}

}
