package vulc.dantegame.level.entity.mob;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.input.KeyBindings;
import vulc.dantegame.level.entity.Entity;

public class Player extends Mob {

	public Player() {
		xr = 48;
		yr = 48;

		// DEBUG
		x = 100;
		y = 350;
	}

	public void tick() {
		int xm = 0;
		int ym = 0;

		int speed = 4;

		if(KeyBindings.W.down() || KeyBindings.UP.down()) ym -= speed;
		if(KeyBindings.A.down() || KeyBindings.LEFT.down()) xm -= speed;
		if(KeyBindings.S.down() || KeyBindings.DOWN.down()) ym += speed;
		if(KeyBindings.D.down() || KeyBindings.RIGHT.down()) xm += speed;

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
		screen.setPixel(screen.width / 2, screen.height / 2, 0xffffff);
	}

	public boolean isBlockedBy(Entity e) {
		if(e instanceof RollingRock) return true;
		return false;
	}

}
