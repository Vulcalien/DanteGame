package vulc.dantegame.level.entity.mob;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.tile.Tile;

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

		int xt0 = Level.posToTile(x - xr);
		int yt0 = Level.posToTile(y - yr);
		int xt1 = Level.posToTile(x + xr - 1);
		int yt1 = Level.posToTile(y + yr - 1);

		boolean isThereVoid = true;
		for(int xt = xt0; xt <= xt1; xt++) {
			for(int yt = yt0; yt <= yt1; yt++) {
				Tile tile = level.getTile(xt, yt);
				if(tile != Tile.VOID) isThereVoid = false;
			}
		}
		if(isThereVoid) remove();
	}

	public void render(Screen screen) {
		screen.renderSprite(Atlas.ROLLING_ROCK.getRotated(animation / 6 % 2 * 2),
		                    x - Atlas.ROLLING_ROCK.width / 2, y - Atlas.ROLLING_ROCK.height / 2);
	}

	public void touchedBy(Entity e) {
		// TODO touched
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public boolean isBlockedBy(Entity e) {
		return true;
	}

}
