package vulc.engine.level.tile;

import vulc.bitmap.IntBitmap;
import vulc.engine.gfx.Screen;
import vulc.engine.level.Level;

public class StoneFloor extends Tile {

	public StoneFloor(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int xt, int yt) {
		screen.renderSprite(new IntBitmap(64, 64, 0x888888), xt * Level.T_SIZE, yt * Level.T_SIZE);
	}

}
