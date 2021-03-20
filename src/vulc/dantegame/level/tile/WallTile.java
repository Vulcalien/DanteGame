package vulc.dantegame.level.tile;

import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;

public class WallTile extends Tile {

	public WallTile(int id) {
		super(id);
	}

	public boolean mayPass(Entity e, int xm, int ym, Level level, int xt, int yt) {
		return false;
	}

}
