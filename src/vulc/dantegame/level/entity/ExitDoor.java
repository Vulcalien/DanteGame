package vulc.dantegame.level.entity;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class ExitDoor extends Entity {

	public boolean open = false;

	public ExitDoor(int xt, int yt) {
		xr = 64;
		yr = 128;

		this.x = Level.tileToPos(xt) + xr;
		this.y = Level.tileToPos(yt) + yr;
	}

	public void render(Screen screen) {
		if(!open) {
			Atlas.drawTile(5, 3, 2, 4, screen,
			               x - xr - screen.xOffset,
			               y - yr - screen.yOffset);
		}
		Atlas.drawTile(3, 3, 2, 4, screen,
		               x - xr - screen.xOffset,
		               y - yr - screen.yOffset);
	}

	public boolean blocks(Entity e) {
		return !open;
	}

	public void touchedBy(Entity e) {
		e.touchedBy(this);
	}

	public void open() {
		open = true;
	}

}
