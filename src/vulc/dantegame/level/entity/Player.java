package vulc.dantegame.level.entity;

import vulc.bitmap.Bitmap;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.input.KeyBindings;

public class Player extends Mob {

	public Player() {
		xr = 48;
		yr = 92;

		x = 100;
		y = 150;
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
		Bitmap<Integer> sprite;
		if(moveAnimation == 0) {
			sprite = Atlas.getSprite(Atlas.PLAYER, dir * 4, 0, 4, 8);
		} else {
			sprite = Atlas.getSprite(Atlas.PLAYER, dir * 4, (1 + ((moveAnimation / 10) % 4)) * 8, 4, 8);
		}

		// shifted by 32 pixels in y-axix
		screen.renderSprite(sprite, x - sprite.width / 2, y - sprite.height / 2 - 32);
		screen.writeOffset(dir + "", 0x00ff00, x, y - 100);
	}

}
