package vulc.dantegame.level.entity.mob;

import java.util.List;

import vulc.bitmap.Bitmap;
import vulc.bitmap.IntBitmap;
import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.menu.GameOverMenu;
import vulc.dantegame.gfx.menu.TransitionOverlay;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.entity.ExitDoor;
import vulc.dantegame.level.entity.TalkingPerson;
import vulc.dantegame.level.entity.particle.TextParticle;
import vulc.dantegame.level.tile.Tile;
import vulc.dantegame.sfx.Sound;
import vulc.util.Geometry;

public class Player extends Mob {

	private Bitmap<Integer> shadow;

	public boolean isTalking = false;
	private boolean hasTalked = false;

	private boolean switchingLevel = false;

	public int xCheckpoint, yCheckpoint;
	public int lastSayNotTalked = -Integer.MAX_VALUE / 2;
	public int lastSetCheckpointTime = -Integer.MAX_VALUE / 2;

	public Player() {
		xr = 32;
		yr = 24;

		shadow = new IntBitmap(64, 48);
		Bitmap<Integer> tmp = new IntBitmap(64, 64, 0xff00ff);
		Geometry.fillCircle(tmp, 0x000000, 32, 32, 30);
		shadow = tmp.fGetScaledByDimension(shadow.width, shadow.height);
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
		if(platform != null && platform.waiting <= 0) {
			xm += platform.xmGet();
			ym += platform.ymGet();
		}

		boolean isThereVoid = true;
		boolean isThereCheckpoint = false;

		for(int xt = xt0; xt <= xt1; xt++) {
			for(int yt = yt0; yt <= yt1; yt++) {
				Tile tile = level.getTile(xt, yt);
				if(tile != Tile.VOID) isThereVoid = false;
				if(tile == Tile.CHECKPOINT) isThereCheckpoint = true;
			}
		}
		if(isThereVoid && platform == null) {
			level.onPlayerDeath();
		}
		if(isThereCheckpoint && Game.ticks - lastSetCheckpointTime >= 300) {
			setCheckpoint(x, y);
			Sound.CHECKPOINT.play();

			level.addEntity(new TextParticle(180, x, y - 256, "Checkpoint impostato"));

			lastSetCheckpointTime = Game.ticks;
		}

		// do not accept movement input when there is an overlay
		// or if talking to someone
		if(Game.overlay == null && !isTalking) {
			// xm and ym requested by input
			int xmIn = 0, ymIn = 0;
			int speed = 6;

			if(KeyBindings.W.down() || KeyBindings.UP.down()) ymIn -= 1;
			if(KeyBindings.A.down() || KeyBindings.LEFT.down()) xmIn -= 1;
			if(KeyBindings.S.down() || KeyBindings.DOWN.down()) ymIn += 1;
			if(KeyBindings.D.down() || KeyBindings.RIGHT.down()) xmIn += 1;

			if(xmIn != 0 && ymIn != 0) {
				xmIn *= speed * 2 / 3;
				ymIn *= speed * 2 / 3;
			} else {
				xmIn *= speed;
				ymIn *= speed;
			}

			super.calculateDirAndMoveAnimation(xmIn, ymIn);

			xm += xmIn;
			ym += ymIn;

			if(moveAnimation % 20 == 19) {
				Sound.SAND.play();
			}
		}
		move(xm, ym);
	}

	public void render(Screen screen) {
		int xDst = x - Atlas.spriteSize(4) / 2 - screen.xOffset;
		int yDst = y - Atlas.spriteSize(8) / 2 - 112 - screen.yOffset; // player is shifted by 112 pixels in y-axix

		// draw shadow
//		screen.renderSprite(shadow, 0x7e,
//		                    x - shadow.width / 2, y - shadow.height / 2 + 6);

		if(moveAnimation == 0) {
			Atlas.drawSprite(Atlas.PLAYER, dir * 4, 0, 4, 8,
			                 screen, xDst, yDst);
		} else {
			Atlas.drawSprite(Atlas.PLAYER, dir * 4, (1 + ((moveAnimation / 8) % 4)) * 8, 4, 8,
			                 screen, xDst, yDst);
		}

		// hitbox (debug)
//		screen.setPixel(-screen.xOffset + x - xr, -screen.yOffset + y - yr, 0xffffff);
//		screen.setPixel(-screen.xOffset + x + xr - 1, -screen.yOffset + y + yr - 1, 0xffffff);

		// interaction (debug)
//		screen.fill(x0 - screen.xOffset, y0 - screen.yOffset, x1 - screen.xOffset, y1 - screen.yOffset, 0xff00ff, 0x7e);
	}

	private void interact(int x0, int y0, int x1, int y1) {
		for(Entity e : level.getEntities(x0, y0, x1, y1)) {
			if(e instanceof TalkingPerson) {
				TalkingPerson tp = (TalkingPerson) e;
				tp.talkToPlayer(this);

				hasTalked = true;
			} else if(e instanceof ExitDoor) {
				ExitDoor door = (ExitDoor) e;
				if(hasTalked) {
					door.open();
				} else {
					if(Game.ticks - lastSayNotTalked >= 180) {
						level.addEntity(new TextParticle(180, x, y - 256, "Parla prima con il personaggio"));

						lastSayNotTalked = Game.ticks;
					}
				}
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
		if(e instanceof TalkingPerson) return true;
		if(e instanceof ExitDoor) return true;
		return false;
	}

	public void touchedBy(Entity e) {
		if(e instanceof RollingRock) {
			level.onPlayerDeath();
		} else if(e instanceof ExitDoor) {
			ExitDoor door = (ExitDoor) e;
			if(hasTalked && !switchingLevel && door.open) {
				Game.levelNumber++;
				switchingLevel = true;
				Game.overlay = new TransitionOverlay(240, 0x000000, () -> {
					try {
						Game.level = Level.loadLevel(Game.levelNumber);
						Game.player = Game.level.player;
					} catch(Exception ex) {
						Game.menu = new GameOverMenu();
						Game.level = null;
						Game.player = null;
					}
				});
			}
		}
	}

	public void setCheckpoint(int x, int y) {
		this.xCheckpoint = x;
		this.yCheckpoint = y;
	}

}
