package vulc.dantegame.level.entity.particle;

import vulc.dantegame.gfx.Screen;

public class TextParticle extends Particle {

	private final String text;

	public TextParticle(int lifeTime, int x, int y, String text) {
		super(lifeTime, x, y);

		this.text = text;
	}

	public void render(Screen screen) {
		int transparency = 0xff;
		if(remainingTime > 0 && remainingTime < 10) {
			transparency = 0xff * remainingTime / 10;
		}

		screen.setFont(Screen.FONT_X3);

		int padding = screen.getFont().getHeight() / 2;
		int height = screen.getFont().getHeight();
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == '\n') height += screen.getFont().getHeight() + screen.getFont().getLineSpacing();
		}

		screen.fill(-screen.xOffset + x - screen.getFont().widthOf(text) / 2 - padding,
		            -screen.yOffset + y - height / 2 - padding,
		            -screen.xOffset + x + screen.getFont().widthOf(text) / 2 + padding,
		            -screen.yOffset + y + height / 2 + padding,
		            0xffffff, transparency * 13 / 16);

		screen.writeOffset(text, 0x000000, transparency,
		                   x - screen.getFont().widthOf(text) / 2,
		                   y - height / 2);

		screen.setFont(Screen.NORMAL_FONT);
	}

}
