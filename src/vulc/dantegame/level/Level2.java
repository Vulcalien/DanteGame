package vulc.dantegame.level;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.entity.ExitDoor;
import vulc.dantegame.level.entity.StoneWithInfo;
import vulc.dantegame.level.entity.TalkingPerson;
import vulc.dantegame.level.entity.mob.MovingPlatform;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.mob.RollingRock;

public class Level2 extends Level {

	protected Level2(int levelID) {
		super(levelID);

		// stones
		addEntity(new StoneWithInfo(39, 12, "1"));
		addEntity(new StoneWithInfo(22, 12, "2"));
		addEntity(new StoneWithInfo(13, 13, "3"));
		addEntity(new StoneWithInfo(19, 21, "4"));
		addEntity(new StoneWithInfo(18, 35, "5"));
		addEntity(new StoneWithInfo(27, 29, "6")); // broken
		addEntity(new StoneWithInfo(41, 26, "7"));
		addEntity(new StoneWithInfo(33, 31, "8"));

		// platforms
		addEntity(new MovingPlatform(20, 9, 2, new int[] {
		    20, 9, 13, 9
		}));
		addEntity(new MovingPlatform(10, 15, 2, new int[] {
		    10, 15, 10, 9
		}));
		addEntity(new MovingPlatform(17, 30, 2, new int[] {
		    17, 30, 23, 30
		}));

		// exit door
		addEntity(new ExitDoor(41, 31));

		// talking person
		addEntity(new TalkingPerson(Atlas.CATONE, 25, 22));

		// player
		player = new Player();
		player.x = Level.tileToPos(41);
		player.y = Level.tileToPos(10);
		player.setCheckpoint(player.x, player.y);
		addEntity(player);
	}

	public void tick() {
		super.tick();

		if(Game.ticks % 120 == 0) {
			addEntity(new RollingRock(Level.tileToPos(15), Level.tileToPos(16),
			                          0, 4));
			addEntity(new RollingRock(Level.tileToPos(35), Level.tileToPos(20),
			                          0, 4));
		}
	}

}
