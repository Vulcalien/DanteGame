package vulc.dantegame.gfx.menu;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.input.KeyBindings;

public class InfoMenu extends Menu {

	private final boolean startup;

	public InfoMenu(boolean startup) {
		this.startup = startup;
	}

	public void tick() {
		if(startup && KeyBindings.ENTER.pressed()) {
			Game.menu = new StartMenu();
		}
	}

	public void render(Screen screen) {
		String text = "Videogioco fatto per il Dantedi' 2021\n"
		              + "dalla classe 4BA Liceo Vittorini Gela\n"
		              + "\n"
		              + "      Crediti\n"
		              + "\n"
		              + "Programmazione:\n"
		              + "Grafiche:\n"
		              + "Musica e suoni:\n"
		              + "Livelli:\n"
		              + "Testo:\n"
		              + "Test:\n";

		screen.setFont(Screen.FONT_X3);
		screen.write(text, 0xffffff, 16, 16);
		screen.setFont(Screen.NORMAL_FONT);

		text = " Premi ENTER per tornare al menu principale";
		screen.write(text, 0xffffff, 0xaa,
		             screen.width - screen.getFont().widthOf(text) - 5,
		             screen.height - screen.getFont().getHeight() - 5);
	}

}
