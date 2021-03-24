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
import vulc.dantegame.gfx.menu.PauseMenu;
import vulc.dantegame.gfx.menu.StartMenu;
import vulc.dantegame.input.InputHandler;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.tile.Tile;
import vulc.dantegame.sfx.Sound;

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
	public static int levelNumber = 1;
	public static Player player;

	public static Menu menu;
	public static Menu overlay;

	public static int ticks = 0;

	private static void init() {
		new Thread(() -> {
			Sound.init();
			Sound.THEME.loop();
		}).start();

		level = Level.loadLevel(levelNumber);
		player = level.player;

		// DEBUG uncomment this
		menu = new StartMenu();
	}

	public static void tick() {
		INPUT.tick();

		if(menu != null) {
			menu.tick();
		} else {
			if(KeyBindings.ESCAPE.pressed()) {
				menu = new PauseMenu();
			}
		}

		if(level != null && (menu == null || !menu.preventsLevelTick())) {
			ticks++;
			level.tick();
			Tile.animationTicks++;
		}

		if(overlay != null) overlay.tick();

//		if(KeyBindings.F11.released()) {
//			createFrame(!frame.isFullScreen);
//			frame.setVisible(true);
//		}
	}

	public static void render(Screen screen) {
		screen.clear(0x000000);

		if(level != null && (menu == null || !menu.preventsLevelRender())) {
			// the screen is 20x11.25 tiles
			level.render(screen, 21, 13);
		}
		if(menu != null) menu.render(screen);
		if(overlay != null) overlay.render(screen);
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
