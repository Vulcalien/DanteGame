package vulc.dantegame.level.entity.mob;

import java.util.List;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.entity.StoneWithInfo;
import vulc.dantegame.level.tile.Tile;

public class Player extends Mob {

	public Player() {
		xr = 32;
		yr = 32;

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

		List<Entity> inTile = level.getEntitiesInTile(xt0 - 1, yt0 - 1, xt1 + 1, yt1 + 1);
		MovingPlatform platform = null;
		for(Entity e : inTile) {
			if(e instanceof MovingPlatform && e.touches(this)) {
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
			// xm and ym requested by input
			int xmIn = 0, ymIn = 0;
			int speed = 4;

			if(KeyBindings.W.down() || KeyBindings.UP.down()) ymIn -= speed;
			if(KeyBindings.A.down() || KeyBindings.LEFT.down()) xmIn -= speed;
			if(KeyBindings.S.down() || KeyBindings.DOWN.down()) ymIn += speed;
			if(KeyBindings.D.down() || KeyBindings.RIGHT.down()) xmIn += speed;

			super.calculateDirAndMoveAnimation(xmIn, ymIn);

			xm += xmIn;
			ym += ymIn;
		}
		move(xm, ym);
	}

	public void render(Screen screen) {
		int xDst = x - Atlas.spriteSize(4) / 2 - screen.xOffset;
		int yDst = y - Atlas.spriteSize(8) / 2 - 112 - screen.yOffset; // player is shifted by 112 pixels in y-axix

		if(moveAnimation == 0) {
			Atlas.drawSprite(Atlas.PLAYER, dir * 4 * 0 + 8, 0, 4, 8,
			                 screen, xDst, yDst);
		} else {
			// DEBUG remove * 0 + 8 to enable direction
			Atlas.drawSprite(Atlas.PLAYER, dir * 4 * 0 + 8, (1 + ((moveAnimation / 8) % 4)) * 8, 4, 8,
			                 screen, xDst, yDst);
		}

		// uncomment to draw hitbox
//		screen.setPixel(-screen.xOffset + x - xr, -screen.yOffset + y - yr, 0xffffff);
//		screen.setPixel(-screen.xOffset + x + xr - 1, -screen.yOffset + y + yr - 1, 0xffffff);
	}

	protected void calculateDirAndMoveAnimation(int xm, int ym) {
		// manually calculate (because the player can be moved by a platform)
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public boolean isBlockedBy(Entity e) {
		if(e instanceof RollingRock) return true;
		if(e instanceof StoneWithInfo) return true;
		return false;
	}

	public void touchedBy(Entity e) {
		if(e instanceof RollingRock) {
			level.onPlayerDeath();
		}
	}

}
