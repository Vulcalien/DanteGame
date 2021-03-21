package vulc.dantegame.level.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.particle.TextParticle;
import vulc.vdf.VDFObject;
import vulc.vdf.io.text.TextVDF;

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

	public void render(Screen screen) {
		screen.renderSprite(Atlas.STONE_WITH_INFO,
		                    x - Atlas.STONE_WITH_INFO.width / 2, y - Atlas.STONE_WITH_INFO.height / 2);

		if(level.player != null) {
			Player p = level.player;

			if(Math.abs(x - p.x) + Math.abs(y - p.y) < 256) {
				String text = textFile.getString(textID);

				if(particle != null && !particle.removed) {
					particle.resetRemainingTime();
				} else {
					particle = new TextParticle(300, x, y - 64, text);
					level.addEntity(particle);
				}
			}
		}
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public static void init() {
		try(Reader in = new InputStreamReader(StoneWithInfo.class.getResourceAsStream("/text/stone_text.vdf"))) {
			textFile = (VDFObject) TextVDF.deserialize(new BufferedReader(in));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
