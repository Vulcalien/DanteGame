package vulc.dantegame.level.entity.mob;

import vulc.dantegame.level.entity.Entity;

public class Mob extends Entity {

	public int dir = 2;
	public int moveAnimation = 0;

	public boolean[] move(int xm, int ym) {
		boolean[] result = super.move(xm, ym);

		if(xm != 0 || ym != 0) {
			if(xm < 0) dir = 1;
			else if(xm > 0) dir = 3;

			if(ym < 0) dir = 0;
			else if(ym > 0) dir = 2;

			moveAnimation++;
		} else {
			moveAnimation = 0;
		}
		return result;
	}

}
