/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.gfx;

import vulc.bitmap.Bitmap;
import vulc.bitmap.IntBitmap;
import vulc.bitmap.font.Font;

public class Screen extends IntBitmap {

	public static final Font SMALL_FONT = new Font(Screen.class.getResourceAsStream("/fonts/tinyfont.fv4"));
	public static final Font NORMAL_FONT = SMALL_FONT.getScaled(2);
	public static final Font FONT_X3 = SMALL_FONT.getScaled(3);
	public static final Font BIG_FONT = SMALL_FONT.getScaled(4);
	public static final Font BIGGER_FONT = SMALL_FONT.getScaled(6);
	public static final Font BIGGEST_FONT = SMALL_FONT.getScaled(8);

	public int xOffset = 0, yOffset = 0;

	public Screen(int width, int height, int[] pixels) {
		super(width, height, pixels);

		setTransparent(0xff00ff);
		setFont(NORMAL_FONT);
	}

	public void setOffset(int x, int y) {
		this.xOffset = x;
		this.yOffset = y;
	}

	public void renderSprite(Bitmap<Integer> sprite, int x, int y) {
		draw(sprite, x - xOffset, y - yOffset);
	}

	public void renderSprite(Bitmap<Integer> sprite, int transparency, int x, int y) {
		draw(sprite, transparency, x - xOffset, y - yOffset);
	}

	public void writeOffset(String text, int color, int x, int y) {
		write(text, color, x - xOffset, y - yOffset);
	}

	public void writeOffset(String text, int color, int transparency, int x, int y) {
		write(text, color, transparency, x - xOffset, y - yOffset);
	}

}
