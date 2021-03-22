package vulc.dantegame.level.entity.mob;

import vulc.bitmap.Bitmap;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.sprite.Atlas;
import vulc.dantegame.level.Level;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.entity.particle.TextParticle;

public class TalkingPerson extends Mob {

	public static String[] textArray;

	public final Bitmap<Integer> spriteAtlas;

	public TextParticle talkParticle;
	public int talkStadius = -1;

	public TalkingPerson(Bitmap<Integer> atlas, int xt, int yt) {
		this.spriteAtlas = atlas;

		this.x = Level.tileToPos(xt);
		this.y = Level.tileToPos(yt);

		xr = 32;
		yr = 24;
	}

	public void tick() {
	}

	public void render(Screen screen) {
		int xDst = x - Atlas.spriteSize(4) / 2 - screen.xOffset;
		int yDst = y - Atlas.spriteSize(8) / 2 - 112 - screen.yOffset; // player is shifted by 112 pixels in y-axix

		if(moveAnimation == 0) {
			Atlas.drawSprite(spriteAtlas, dir * 4 * 0 + 8, 0, 4, 8,
			                 screen, xDst, yDst);
		} else {
			Atlas.drawSprite(spriteAtlas, dir * 4, (1 + ((moveAnimation / 8) % 4)) * 8, 4, 8,
			                 screen, xDst, yDst);
		}
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
