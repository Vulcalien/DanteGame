package vulc.dantegame.gfx.menu;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.input.KeyBindings;

public class GameOverMenu extends Menu {

	public void tick() {
		if(KeyBindings.E.pressed()) {
			Game.menu = new InfoMenu(false);
		}
	}

	public void render(Screen screen) {
		screen.clear(0x000000);

		screen.setFont(Screen.BIGGEST_FONT);
		screen.write("FINE", 0xffffff,
		             (screen.width - screen.getFont().widthOf("FINE")) / 2,
		             (screen.height - screen.getFont().getHeight()) / 2);
		screen.setFont(Screen.BIG_FONT);
		screen.write("Grazie per aver giocato!", 0xffffff, 0xcc,
		             (screen.width - screen.getFont().widthOf("Grazie per aver giocato!")) / 2,
		             (screen.height - screen.getFont().getHeight()) / 2 + 60);
		screen.setFont(Screen.NORMAL_FONT);
	}

}
