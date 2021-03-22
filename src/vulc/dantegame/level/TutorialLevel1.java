package vulc.dantegame.level;

import vulc.dantegame.level.entity.mob.Player;

public class TutorialLevel1 extends Level {

	protected TutorialLevel1(int levelID) {
		super(levelID);
		addEntity(player = new Player());
		player.x = Level.tileToPos(70);
		player.y = Level.tileToPos(37);
	}

}
