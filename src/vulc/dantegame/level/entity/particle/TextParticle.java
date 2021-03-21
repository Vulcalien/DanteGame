package vulc.dantegame.level.entity.particle;

import vulc.dantegame.gfx.Screen;

public class TextParticle extends Particle {

	private final String text;

	public TextParticle(int lifeTime, int x, int y, String text) {
		super(lifeTime, x, y);

		this.text = text;
	}

	public void render(Screen screen) {
		screen.setFont(Screen.FONT_X3);

		screen.fill(-screen.xOffset + x - screen.getFont().widthOf(text) / 2,
		            -screen.yOffset + y - screen.getFont().getHeight(),
		            -screen.xOffset + x + screen.getFont().widthOf(text),
		            -screen.yOffset + y + screen.getFont().getHeight(),
		            0xffffff);
		screen.writeOffset(text, 0x000000,
		                   x - screen.getFont().widthOf(text) / 2,
		                   y - screen.getFont().getHeight() / 2);

		screen.setFont(Screen.NORMAL_FONT);
	}

}
