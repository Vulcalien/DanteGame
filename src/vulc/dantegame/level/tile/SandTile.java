package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class SandTile extends Tile {

	public SandTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		Atlas.drawTile((xt * xt * (yt % 3)) % 4, 1, screen,
		               Level.tileToPos(xt) - screen.xOffset, Level.tileToPos(yt) - screen.yOffset);
	}

}
