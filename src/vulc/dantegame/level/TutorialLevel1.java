package vulc.dantegame.level;

import vulc.dantegame.Game;
import vulc.dantegame.level.entity.ExitDoor;
import vulc.dantegame.level.entity.StoneWithInfo;
import vulc.dantegame.level.entity.mob.MovingPlatform;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.mob.RollingRock;

public class TutorialLevel1 extends Level {

	protected TutorialLevel1(int levelID) {
		super(levelID);

		Player player = new Player();
		player.x = Level.tileToPos(45);
		player.y = Level.tileToPos(35);

		player.setCheckpoint(player.x, player.y);

		addEntity(player);

		addEntity(new StoneWithInfo(43, 34, "1"));
		addEntity(new StoneWithInfo(32, 35, "2"));
		addEntity(new StoneWithInfo(42, 46, "3"));
		addEntity(new StoneWithInfo(15, 44, "4"));
		addEntity(new StoneWithInfo(8, 35, "5"));
		addEntity(new StoneWithInfo(8, 21, "6"));
		addEntity(new StoneWithInfo(14, 17, "7"));
		addEntity(new StoneWithInfo(37, 15, "8"));
		addEntity(new StoneWithInfo(74, 13, "9"));

		addEntity(new MovingPlatform(16, 19, 3, new int[] {
		    16, 19, 25, 19
		}));

		addEntity(new ExitDoor(92, 17));

		// DEBUG
//		setTile(Tile.CHECKPOINT, 70, 34);
//		setTile(Tile.CHECKPOINT, 70, 35);
//		setTile(Tile.CHECKPOINT, 71, 34);
//		setTile(Tile.CHECKPOINT, 71, 35);
//
//		setTile(Tile.VOID, 65, 35);
//		setTile(Tile.VOID, 65, 36);
//		setTile(Tile.VOID, 66, 35);
//		setTile(Tile.VOID, 66, 36);
//
//		addEntity(new ExitDoor(55, 35));
//
//		addEntity(new TalkingPerson(Atlas.PLAYER, 60, 35));
//
//		addEntity(new RollingRock(Level.tileToPos(67), Level.tileToPos(35), -1, 0));
//
//		addEntity(new MovingPlatform(65, 35, -2, new int[] {
//		    66, 35
//		}));
//
//		addEntity(new StoneWithInfo(65, 33, ""));
	}

	public void tick() {
		super.tick();

		if(Game.ticks % 120 == 0) {
			addEntity(new RollingRock(Level.tileToPos(54), Level.tileToPos(11),
			                          0, 4));
		} else if(Game.ticks % 120 == 60) {
			addEntity(new RollingRock(Level.tileToPos(46), Level.tileToPos(11),
			                          0, 4));
			addEntity(new RollingRock(Level.tileToPos(63), Level.tileToPos(18),
			                          0, -4));
		}
	}

}
