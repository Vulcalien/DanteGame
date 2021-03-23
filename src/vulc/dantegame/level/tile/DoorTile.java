package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class DoorTile extends Tile {

	public DoorTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		if(level.getTile(xt - 1, yt) != this && level.getTile(xt, yt - 1) != this) {
			Atlas.drawTile(3, 3, 2, 4, screen,
			               Level.tileToPos(xt) - screen.xOffset,
			               Level.tileToPos(yt) - screen.yOffset);
		}
	}

}
