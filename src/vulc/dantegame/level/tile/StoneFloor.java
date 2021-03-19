package vulc.dantegame.level.tile;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class StoneFloor extends Tile {

	public StoneFloor(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		screen.renderSprite(Atlas.getTile(0, 0), xt * Level.T_SIZE, yt * Level.T_SIZE);
//		screen.renderSprite(new IntBitmap(64, 64, 0x888888), xt * Level.T_SIZE, yt * Level.T_SIZE);
	}

}
