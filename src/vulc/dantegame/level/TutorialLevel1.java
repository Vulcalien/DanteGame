package vulc.dantegame.level;

import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.entity.ExitDoor;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.mob.TalkingPerson;
import vulc.dantegame.level.tile.Tile;

public class TutorialLevel1 extends Level {

	protected TutorialLevel1(int levelID) {
		super(levelID);

		Player player = new Player();
		player.x = Level.tileToPos(70);
		player.y = Level.tileToPos(37);

		addEntity(player);

		// DEBUG
		setTile(Tile.CHECKPOINT, 70, 34);
		setTile(Tile.CHECKPOINT, 70, 35);
		setTile(Tile.CHECKPOINT, 71, 34);
		setTile(Tile.CHECKPOINT, 71, 35);

		setTile(Tile.VOID, 65, 35);
		setTile(Tile.VOID, 65, 36);
		setTile(Tile.VOID, 66, 35);
		setTile(Tile.VOID, 66, 36);

		addEntity(new ExitDoor(55, 35));

		addEntity(new TalkingPerson(Atlas.PLAYER, 60, 35));
	}

}
