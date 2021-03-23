package vulc.dantegame.level.entity;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.particle.TextParticle;
import vulc.vdf.VDFObject;

public class StoneWithInfo extends Entity {

	public static VDFObject textFile;

	public final String textID;
	private TextParticle particle;

	public StoneWithInfo(int xt, int yt, String textID) {
		x = Level.tileToPos(xt);
		y = Level.tileToPos(yt);

		xr = 64;
		yr = 64;

		this.textID = textID;
	}

	public void tick() {
		if(level.player != null) {
			Player p = level.player;

			if(Math.abs(x - p.x) + Math.abs(y - p.y) < 256) {
				String text = textFile.getString(textID);

				if(particle != null && !particle.removed) {
					particle.resetRemainingTime();
				} else {
					particle = new TextParticle(150, x, y - 64, text);
					level.addEntity(particle);
				}
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(Atlas.STONE_WITH_INFO,
		                    x - Atlas.STONE_WITH_INFO.width / 2, y - Atlas.STONE_WITH_INFO.height / 2);
	}

	public boolean blocks(Entity e) {
		return true;
	}

}
