package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;

public class StoneTile extends Tile {

	public StoneTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		Atlas.drawTile(2, 0, screen,
		               Level.tileToPos(xt) - screen.xOffset, Level.tileToPos(yt) - screen.yOffset);
	}

	public boolean mayPass(Entity e, int xm, int ym, Level level, int xt, int yt) {
		return false;
	}

}
