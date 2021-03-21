package vulc.dantegame.gfx.menu;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;

public class TransitionOverlay extends Menu {

	private int animation = 0;

	private final int time;
	private final int color;
	private final Runnable action;

	public TransitionOverlay(int time, int color, Runnable action) {
		this.time = time;
		this.color = color;
		this.action = action;
	}

	public TransitionOverlay(int time, int color) {
		this(time, color, null);
	}

	public void tick() {
		animation++;

		if(animation == time / 2 && action != null) {
			action.run();
		} else if(animation == time) {
			Game.overlay = null;
		}
	}

	public void render(Screen screen) {
		int transparency;
		if(animation < time / 2) {
			transparency = (int) (1.0 * 0xff * animation / time * 2);
			if(transparency > 0xff) transparency = 0xff;
		} else {
			transparency = 0xff - (int) (1.0 * 0xff * (animation - time / 2) / time * 2);
			if(transparency < 0) transparency = 0;
		}
		screen.fill(0, 0, screen.width - 1, screen.height - 1, color, transparency);
	}

}
