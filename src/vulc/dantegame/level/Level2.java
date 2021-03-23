package vulc.dantegame.level;

import vulc.dantegame.level.entity.StoneWithInfo;
import vulc.dantegame.level.entity.mob.MovingPlatform;
import vulc.dantegame.level.entity.mob.Player;

public class Level2 extends Level {

	protected Level2(int levelID) {
		super(levelID);

		// stones
		addEntity(new StoneWithInfo(39, 12, "1"));

		player = new Player();
		player.x = Level.tileToPos(41);
		player.y = Level.tileToPos(10);
		player.setCheckpoint(player.x, player.y);
		addEntity(player);

		addEntity(new MovingPlatform(20, 9, 2, new int[] {
		    20, 9, 13, 9
		}));
		addEntity(new MovingPlatform(10, 15, 2, new int[] {
		    10, 15, 10, 9
		}));
		addEntity(new MovingPlatform(17, 30, 2, new int[] {
		    17, 30, 23, 30
		}));
	}

	public void tick() {
		super.tick();
	}

}
