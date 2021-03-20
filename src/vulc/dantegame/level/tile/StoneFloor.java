package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class StoneFloor extends Tile {

	public StoneFloor(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		Atlas.drawTile(0, 0, screen, Level.tileToPos(xt) - screen.xOffset, Level.tileToPos(yt) - screen.yOffset);
	}

}
