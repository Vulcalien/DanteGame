package vulc.dantegame.level;

import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.tile.Tile;

public class TutorialLevel1 extends Level {

	protected TutorialLevel1(int levelID) {
		super(levelID);

		Player player = new Player();
		player.x = Level.tileToPos(70);
		player.y = Level.tileToPos(37);

		addEntity(player);

		setTile(Tile.CHECKPOINT, 70, 34);
		setTile(Tile.CHECKPOINT, 70, 35);
		setTile(Tile.CHECKPOINT, 71, 34);
		setTile(Tile.CHECKPOINT, 71, 35);

		setTile(Tile.VOID, 65, 35);
		setTile(Tile.VOID, 65, 36);
		setTile(Tile.VOID, 66, 35);
		setTile(Tile.VOID, 66, 36);

		setTile(Tile.DOOR, 66, 40);
		setTile(Tile.DOOR, 67, 40);
		setTile(Tile.DOOR, 66, 41);
		setTile(Tile.DOOR, 67, 41);
		setTile(Tile.DOOR, 66, 42);
		setTile(Tile.DOOR, 67, 42);
		setTile(Tile.DOOR, 66, 43);
		setTile(Tile.DOOR, 67, 43);
	}

}
