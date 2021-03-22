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

	public boolean isTalking = false;

	public Player() {
		xr = 32;
		yr = 24;

		// DEBUG
		x = 100;
		y = 350;
	}

	public void tick() {
		// interaction
		if(KeyBindings.E.pressed() || KeyBindings.ENTER.pressed() || KeyBindings.SPACE.pressed()) {
			int size = 48;
			int dist = 80;

			if(dir == 0) interact(x - size, y - dist - size, x + size, y - dist + size);
			if(dir == 1) interact(x - dist - size, y - size, x - dist + size, y + size);
			if(dir == 2) interact(x - size, y + dist - size, x + size, y + dist + size);
			if(dir == 3) interact(x + dist - size, y - size, x + dist + size, y + size);
		}

		// movement
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
		// or if talking to someone
		if(Game.overlay == null && !isTalking) {
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

		// hitbox (debug)
//		screen.setPixel(-screen.xOffset + x - xr, -screen.yOffset + y - yr, 0xffffff);
//		screen.setPixel(-screen.xOffset + x + xr - 1, -screen.yOffset + y + yr - 1, 0xffffff);

		// interaction (debug)
//		screen.fill(x0 - screen.xOffset, y0 - screen.yOffset, x1 - screen.xOffset, y1 - screen.yOffset, 0xff00ff, 0x7e);
	}

	// DEBUG
	int x0, y0, x1, y1;

	private void interact(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		for(Entity e : level.getEntities(x0, y0, x1, y1)) {
			if(e instanceof TalkingPerson) {
				TalkingPerson tp = (TalkingPerson) e;
				tp.talkToPlayer(this);
			}
		}
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
		if(e instanceof TalkingPerson) return true;
		return false;
	}

	public void touchedBy(Entity e) {
		if(e instanceof RollingRock) {
			level.onPlayerDeath();
		}
	}

}
