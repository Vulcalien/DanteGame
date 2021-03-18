package vulc.engine.level.entity;

import vulc.bitmap.Bitmap;
import vulc.bitmap.IntBitmap;
import vulc.engine.gfx.Screen;
import vulc.engine.gfx.sprite.Atlas;
import vulc.engine.input.KeyBindings;

public class Player extends Mob {

	public Player() {
		xr = 32;
		yr = 64;

		x = 60;
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
		screen.renderSprite(new IntBitmap(xr * 2, yr * 2, 0xff0000), x - xr, y - yr);

		Bitmap<Integer> sprite;
		if(moveAnimation == 0) {
			sprite = Atlas.getSprite(Atlas.PLAYER, dir * 2, 0, 2, 4);
		} else {
			sprite = Atlas.getSprite(Atlas.PLAYER, dir * 2, (1 + ((moveAnimation / 10) & 1)) * 4, 2, 4);
		}

		screen.renderSprite(sprite, x - xr, y - yr);
		screen.writeOffset(dir + "", 0x00ff00, x, y - 100);
	}

}
