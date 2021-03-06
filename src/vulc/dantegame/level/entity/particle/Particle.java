/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.level.entity.particle;

import vulc.dantegame.level.entity.Entity;

public abstract class Particle extends Entity {

	public final int lifeTime;
	public int remainingTime;

	public Particle(int lifeTime, int x, int y) {
		this.lifeTime = lifeTime;
		remainingTime = lifeTime;

		this.x = x;
		this.y = y;
	}

	public void tick() {
		remainingTime--;
		if(remainingTime == 0) remove();
	}

	public void resetRemainingTime() {
		this.remainingTime = lifeTime;
	}

}
