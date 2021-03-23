package vulc.dantegame.level.entity;

import vulc.bitmap.Bitmap;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.entity.particle.TextParticle;

public class TalkingPerson extends Entity {

	public static String[] textArray;

	public final Bitmap<Integer> image;

	public TextParticle talkParticle;
	public int talkStadius = -1;

	public TalkingPerson(Bitmap<Integer> image, int xt, int yt) {
		this.image = image;

		xr = 32;
		yr = 24;

		this.x = Level.tileToPos(xt) + xr;
		this.y = Level.tileToPos(yt) + yr;
	}

	public void tick() {
	}

	public void render(Screen screen) {
		int xDst = x - Atlas.spriteSize(4) / 2 - screen.xOffset;
		int yDst = y - Atlas.spriteSize(8) / 2 - 112 - screen.yOffset; // player is shifted by 112 pixels in y-axix

		screen.draw(image, xDst, yDst);

		// uncomment to draw hitbox
//		screen.setPixel(-screen.xOffset + x - xr, -screen.yOffset + y - yr, 0xffffff);
//		screen.setPixel(-screen.xOffset + x + xr - 1, -screen.yOffset + y + yr - 1, 0xffffff);
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public void talkToPlayer(Player p) {
		talkStadius++;
		if(talkStadius > textArray.length) {
			talkStadius = 0;
		} else if(talkStadius == textArray.length) {
			p.isTalking = false;

			if(talkParticle != null) talkParticle.remove();
			return;
		}
		p.isTalking = true;
		if(talkParticle != null) talkParticle.remove();

		talkParticle = new TextParticle(-1, x, y - 260, textArray[talkStadius]);
		level.addEntity(talkParticle);
	}

}
