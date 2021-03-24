package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class CheckpointTile extends Tile {

	public CheckpointTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		if(level.getTile(xt - 1, yt) != this && level.getTile(xt, yt - 1) != this) {
			Atlas.drawTile(1, 1, screen,
			               Level.tileToPos(xt) - screen.xOffset,
			               Level.tileToPos(yt) - screen.yOffset);
			Atlas.drawTile(1, 1, screen,
			               Level.tileToPos(xt) - screen.xOffset,
			               Level.tileToPos(yt + 1) - screen.yOffset);
			Atlas.drawTile(1, 1, screen,
			               Level.tileToPos(xt + 1) - screen.xOffset,
			               Level.tileToPos(yt) - screen.yOffset);
			Atlas.drawTile(1, 1, screen,
			               Level.tileToPos(xt + 1) - screen.xOffset,
			               Level.tileToPos(yt + 1) - screen.yOffset);

			// paper sheet
			Atlas.drawTile(0, 3, 2, 2, screen,
			               Level.tileToPos(xt) - Level.T_SIZE / 2 - screen.xOffset,
			               Level.tileToPos(yt) - Level.T_SIZE / 2 - screen.yOffset);
		}
	}

}
