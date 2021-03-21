package vulc.dantegame.gfx.menu;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.input.KeyBindings;

public class PauseMenu extends Menu {

	public void tick() {
		if(KeyBindings.ESCAPE.pressed()) {
			Game.menu = null;
		}
	}

	public void render(Screen screen) {
		int height = screen.height * 3 / 4;
		int width = height * 3 / 2;

		screen.fill((screen.width - width) / 2, (screen.height - height) / 2,
		            (screen.width + width) / 2, (screen.height + height) / 2,
		            0xffffff, 0xaa);
	}

	public boolean preventsLevelRender() {
		return false;
	}

}
