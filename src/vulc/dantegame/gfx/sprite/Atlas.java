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

	// entities
	public static final Bitmap<Integer> PLAYER;
	public static final Bitmap<Integer> VIRGILIO;
	public static final Bitmap<Integer> CATONE;
	public static final Bitmap<Integer> ROLLING_ROCK;
	public static final Bitmap<Integer> MOVING_PLATFORM;
	public static final Bitmap<Integer> STONE_WITH_INFO;

	static {
		try {
			TILES = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/atlas.png")));

			// entiites
			PLAYER = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/dante.png")));
			VIRGILIO = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/virgilio.png")));
			CATONE = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/catone.png")));
			ROLLING_ROCK = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/rolling_rock.png")));
			MOVING_PLATFORM =
			        new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/moving_platform.png")));
			STONE_WITH_INFO =
			        new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/stone_with_info.png")));

			START_IMAGE = new IntBitmap(ImageIO.read(Atlas.class.getResourceAsStream("/gfx/background/start.png")));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}

	@Deprecated
	public static Bitmap<Integer> getTile(int xt, int yt) {
		return getTile(xt, yt, 1, 1);
	}

	@Deprecated
	public static Bitmap<Integer> getTile(int xt, int yt, int wt, int ht) {
		return TILES.getSubimage(xt * Level.T_SIZE, yt * Level.T_SIZE, wt * Level.T_SIZE, ht * Level.T_SIZE);
	}

	public static void drawTile(int xt, int yt, Bitmap<Integer> dest, int xDst, int yDst) {
		drawTile(xt, yt, 1, 1, dest, xDst, yDst);
	}

	public static void drawTile(int xt, int yt, int wt, int ht, Bitmap<Integer> dest, int xDst, int yDst) {
		dest.drawSubimage(TILES, xt * Level.T_SIZE, yt * Level.T_SIZE, wt * Level.T_SIZE, ht * Level.T_SIZE,
		                  xDst, yDst);
	}

	@Deprecated
	public static Bitmap<Integer> getSprite(Bitmap<Integer> image, int xt, int yt, int wt, int ht) {
		return image.getSubimage(xt * SPRITE_SIZE, yt * SPRITE_SIZE, wt * SPRITE_SIZE, ht * SPRITE_SIZE);
	}

	public static void drawSprite(Bitmap<Integer> spriteset, int xt, int yt, int wt, int ht,
	                              Bitmap<Integer> dest, int xDst, int yDst) {
		dest.drawSubimage(spriteset, xt * SPRITE_SIZE, yt * SPRITE_SIZE, wt * SPRITE_SIZE, ht * SPRITE_SIZE,
		                  xDst, yDst);
	}

	public static int spriteSize(int u) {
		return u * SPRITE_SIZE;
	}

}
