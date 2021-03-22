package vulc.dantegame.level.entity.mob;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;

public class MovingPlatform extends Mob {

	private final int[] spots;
	private final int speed;

	private int currentSpot = 0;

	public MovingPlatform(int xt0, int yt0, int speed, int[] spots) {
		this.x = Level.tileToPos(xt0);
		this.y = Level.tileToPos(yt0);

		this.speed = speed;
		this.spots = spots;

		xr = 56;
		yr = 48;
	}

	public void tick() {
		move(xmGet(), ymGet());

		int x1 = Level.tileToPos(spots[currentSpot * 2]);
		int y1 = Level.tileToPos(spots[currentSpot * 2 + 1]);

		if(x == x1 && y == y1) {
			currentSpot++;
			currentSpot %= spots.length / 2;
		}
	}

	public int xmGet() {
		int xm = 0;

		int x1 = Level.tileToPos(spots[currentSpot * 2]);
		if(x < x1) xm = 1;
		else if(x > x1) xm = -1;

		int xspeed = Math.min(speed, Math.abs(x - x1));
		return xm * xspeed;
	}

	public int ymGet() {
		int ym = 0;

		int y1 = Level.tileToPos(spots[currentSpot * 2 + 1]);
		if(y < y1) ym = 1;
		else if(y > y1) ym = -1;

		int yspeed = Math.min(speed, Math.abs(y - y1));
		return ym * yspeed;
	}

	public void render(Screen screen) {
		screen.renderSprite(Atlas.MOVING_PLATFORM,
		                    x - Atlas.MOVING_PLATFORM.width / 2, y - Atlas.MOVING_PLATFORM.height / 2);
	}

}
