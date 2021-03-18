package vulc.engine.gfx.sprite;

import java.io.IOException;

import javax.imageio.ImageIO;

import vulc.bitmap.Bitmap;
import vulc.bitmap.IntBitmap;

public final class Atlas {

	private Atlas() {
	}

	private static final int SPRITE_SIZE = 32;

	public static final Bitmap<Integer> ATLAS;
	public static final Bitmap<Integer> PLAYER;

	static {
		try {
			ATLAS = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/images/atlas.png")));
			PLAYER = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/images/player.png")));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}

	public static Bitmap<Integer> getSprite(int xt, int yt) {
		return getSprite(xt, yt, 1, 1);
	}

	public static Bitmap<Integer> getSprite(int xt, int yt, int wt, int ht) {
		return ATLAS.getSubimage(xt * SPRITE_SIZE, yt * SPRITE_SIZE, wt * SPRITE_SIZE, ht * SPRITE_SIZE);
	}

	public static Bitmap<Integer> getSprite(Bitmap<Integer> image, int xt, int yt, int wt, int ht) {
		return image.getSubimage(xt * SPRITE_SIZE, yt * SPRITE_SIZE, wt * SPRITE_SIZE, ht * SPRITE_SIZE);
	}

}
