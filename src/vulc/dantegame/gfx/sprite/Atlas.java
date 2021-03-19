package vulc.dantegame.gfx.sprite;

import java.io.IOException;

import javax.imageio.ImageIO;

import vulc.bitmap.Bitmap;
import vulc.bitmap.IntBitmap;
import vulc.dantegame.level.Level;

public final class Atlas {

	private Atlas() {
	}

	public static final int SPRITE_SIZE = 32;

	public static final Bitmap<Integer> START_IMAGE;

	public static final Bitmap<Integer> TILES;
	public static final Bitmap<Integer> PLAYER;

	static {
		try {
			TILES = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/images/atlas.png")));
			PLAYER = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/images/player.png")));

			START_IMAGE = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/images/background/start.png")));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}

	public static Bitmap<Integer> getTile(int xt, int yt) {
		return getTile(xt, yt, 1, 1);
	}

	public static Bitmap<Integer> getTile(int xt, int yt, int wt, int ht) {
		return TILES.getSubimage(xt * Level.T_SIZE, yt * Level.T_SIZE, wt * Level.T_SIZE, ht * Level.T_SIZE);
	}

	public static Bitmap<Integer> getSprite(Bitmap<Integer> image, int xt, int yt, int wt, int ht) {
		return image.getSubimage(xt * SPRITE_SIZE, yt * SPRITE_SIZE, wt * SPRITE_SIZE, ht * SPRITE_SIZE);
	}

}
