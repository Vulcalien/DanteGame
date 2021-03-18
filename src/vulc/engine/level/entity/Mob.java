package vulc.engine.level.entity;

public class Mob extends Entity {

	public int dir;

	public boolean[] move(int xm, int ym) {
		boolean[] result = super.move(xm, ym);

		if(xm < 0) dir = 1;
		else if(xm > 0) dir = 3;

		if(ym < 0) dir = 0;
		else if(ym > 0) dir = 2;

		return result;
	}

}
