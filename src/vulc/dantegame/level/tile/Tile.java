/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;

public class Tile {

	public static int animationTicks = 0;

	public static final Tile[] TILES = new Tile[128];

	public static final Tile GRASS_FLOOR = new Tile(0);
	public static final Tile DIRT_FLOOR = new Tile(1);
	public static final Tile STONE_WALL = new WallTile(2);
	public static final Tile VOID = new Tile(3);

	public final byte id;

	public Tile(int id) {
		this.id = (byte) id;
		if(TILES[id] != null) {
			throw new RuntimeException("Duplicate tile ids");
		}
		TILES[id] = this;
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		Atlas.drawTile(id % 8, id / 8, screen,
		               Level.tileToPos(xt) - screen.xOffset, Level.tileToPos(yt) - screen.yOffset);
	}

	public boolean mayPass(Entity e, int xm, int ym, Level level, int xt, int yt) {
		return true;
	}

}
