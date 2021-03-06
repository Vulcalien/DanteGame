package vulc.dantegame.gfx.menu;

import vulc.dantegame.gfx.Screen;

public abstract class Menu {

	public abstract void tick();

	public abstract void render(Screen screen);

	public boolean preventsLevelTick() {
		return true;
	}

	public boolean preventsLevelRender() {
		return true;
	}

}
