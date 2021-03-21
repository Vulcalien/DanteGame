package vulc.dantegame.level.entity.mob;

import java.util.List;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.tile.Tile;

public class Player extends Mob {

	public Player() {
		xr = 48;
		yr = 48;

		// DEBUG
		x = 100;
		y = 350;
	}

	public void tick() {
		int xm = 0, ym = 0;

		int xt0 = Level.posToTile(x - xr);
		int yt0 = Level.posToTile(y - yr);
		int xt1 = Level.posToTile(x + xr - 1);
		int yt1 = Level.posToTile(y + yr - 1);

		List<Entity> inTile = level.getEntitiesInTile(xt0, yt0, xt1, yt1);
		MovingPlatform platform = null;
		for(Entity e : inTile) {
			if(e instanceof MovingPlatform) {
				platform = (MovingPlatform) e;
				break;
			}
		}
		if(platform != null) {
			xm += platform.xmGet();
			ym += platform.ymGet();
		}

		boolean isThereVoid = true;
		x_for:
		for(int xt = xt0; xt <= xt1; xt++) {
			for(int yt = yt0; yt <= yt1; yt++) {
				if(level.getTile(xt, yt) != Tile.VOID) {
					isThereVoid = false;
					break x_for;
				}
			}
		}
		if(isThereVoid && platform == null) {
			level.onPlayerDeath();
		}

		// do not accept movement input when there is an overlay
		if(Game.overlay == null) {
			int speed = 4;

			if(KeyBindings.W.down() || KeyBindings.UP.down()) ym -= speed;
			if(KeyBindings.A.down() || KeyBindings.LEFT.down()) xm -= speed;
			if(KeyBindings.S.down() || KeyBindings.DOWN.down()) ym += speed;
			if(KeyBindings.D.down() || KeyBindings.RIGHT.down()) xm += speed;
		}
		move(xm, ym);
	}

	public void render(Screen screen) {
		int xDst = x - Atlas.spriteSize(4) / 2 - screen.xOffset;
		int yDst = y - Atlas.spriteSize(8) / 2 - 80 - screen.yOffset; // player is shifted by 80 pixels in y-axix

		if(moveAnimation == 0) {
			Atlas.drawSprite(Atlas.PLAYER, dir * 4 * 0 + 8, 0, 4, 8,
			                 screen, xDst, yDst);
		} else {
			// DEBUG remove * 0 + 8 to enable direction
			Atlas.drawSprite(Atlas.PLAYER, dir * 4 * 0 + 8, (1 + ((moveAnimation / 8) % 4)) * 8, 4, 8,
			                 screen, xDst, yDst);
		}
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public boolean isBlockedBy(Entity e) {
		if(e instanceof RollingRock) return true;
		return false;
	}

	public void touchedBy(Entity e) {
		if(e instanceof RollingRock) {
			level.onPlayerDeath();
		}
	}

}
