package vulc.dantegame.level.entity.mob;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.entity.Entity;

public class RollingRock extends Mob {

	private int animation = 0;
	private int xm, ym;

	public RollingRock(int x, int y, int xm, int ym) {
		this.x = x;
		this.y = y;
		this.xm = xm;
		this.ym = ym;

		xr = 64;
		yr = 64;
	}

	public void tick() {
		animation++;

		move(xm, ym);
	}

	public void render(Screen screen) {
		screen.renderSprite(Atlas.ROLLING_ROCK.getRotated(animation / 6),
		                    x - Atlas.ROLLING_ROCK.width / 2, y - Atlas.ROLLING_ROCK.height / 2);
	}

	public void touchedBy(Entity e) {
		// TODO touched
	}

	public boolean blocks(Entity e) {
		return true;
	}

}
