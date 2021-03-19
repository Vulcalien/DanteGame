/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;

public abstract class Tile {

	public static int animationTicks = 0;

	public static final Tile[] TILES = new Tile[128];

	public static final Tile STONE_FLOOR = new StoneFloor(0);

	public final byte id;

	public Tile(int id) {
		this.id = (byte) id;
		if(TILES[id] != null) {
			throw new RuntimeException("Duplicate tile ids");
		}
		TILES[id] = this;
	}

	public void render(Screen screen, Level level, int xt, int yt) {
	}

	public boolean mayPass(Entity e, int xm, int ym, Level level, int xt, int yt) {
		return true;
	}

}
