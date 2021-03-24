package vulc.dantegame.gfx.menu;

import static vulc.dantegame.input.KeyBindings.*;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;

public class StartMenu extends Menu {

	private static final int UNLOCK_GUI_TIME = 60;

	private static final int GUI_BUTTONS = 2;
	private static final int ID_INFO = 0;
	private static final int ID_START = 1;

	private int animation;

	private int selected;

	private boolean starting = false;

	public StartMenu() {
		selected = 1;
		animation = Game.menu == null ? 0 : UNLOCK_GUI_TIME * 5;
	}

	public void tick() {
		animation++;

		if(!starting && animation >= UNLOCK_GUI_TIME) {
			if(ENTER.pressed()) {
				switch(selected) {
					case ID_INFO:
						Game.menu = new InfoMenu(true);
						break;
					case ID_START:
						Game.overlay = new TransitionOverlay(240, 0x000000, () -> {
							Game.menu = null;
						});
						starting = true;
						break;
				}
			}
			if(A.pressed() || LEFT.pressed()) selected--;
			if(D.pressed() || RIGHT.pressed()) selected++;

			if(selected < 0) selected = GUI_BUTTONS - 1;
			if(selected >= GUI_BUTTONS) selected = 0;
		}
	}

	public void render(Screen screen) {
//		screen.draw(Atlas.START_IMAGE, 0, 0);
		screen.clear(0x999999);

		screen.setFont(Screen.BIGGEST_FONT);
		String text = Game.GAME_NAME;
		screen.write(text, 0x333333,
		             (screen.width - screen.getFont().widthOf(text)) / 2 + 4,
		             (screen.height - screen.getFont().getHeight()) / 3 + 4);
		screen.write(text, 0x000000,
		             (screen.width - screen.getFont().widthOf(text)) / 2,
		             (screen.height - screen.getFont().getHeight()) / 3);
		screen.setFont(Screen.NORMAL_FONT);

		int xcenter = screen.width / 2;
		int ycenter = screen.height / 2;

		int col = 0x000000;
		double anim = 1 + animation * animation / 60000.0;

		for(int x = 0; x < xcenter; x++) {
			for(int y = 0; y < ycenter; y++) {
				double gradient = Math.sqrt(1.0 * (x * x + y * y) / (xcenter * xcenter + ycenter * ycenter));

				int transparency = (int) (0xff * gradient / anim);

				screen.setPixel(xcenter + x, ycenter + y, col, transparency);
				screen.setPixel(xcenter - x - 1, ycenter + y, col, transparency);
				screen.setPixel(xcenter + x, ycenter - y - 1, col, transparency);
				screen.setPixel(xcenter - x - 1, ycenter - y - 1, col, transparency);
			}
		}

		if(animation >= UNLOCK_GUI_TIME) {
			int transparency = (animation - UNLOCK_GUI_TIME);
			if(transparency > 0xff) transparency = 0xff;

			int selectedBoost = (((animation / 15) & 1) == 0) ? 0 : 10;

			int normalColor = 0x000000;
			int selectedColor = 0xcccc00;

			screen.setFont(Screen.BIGGER_FONT);

			screen.write("INIZIO", (selected == ID_START ? selectedColor : normalColor), transparency,
			             (screen.width - screen.getFont().widthOf("INIZIO")) / 2,
			             screen.height - 120 - (selected == ID_START ? selectedBoost : 0));

			screen.write("INFO", (selected == ID_INFO ? selectedColor : normalColor), transparency,
			             (30) / 2,
			             screen.height - 80 - (selected == ID_INFO ? selectedBoost : 0));

			screen.setFont(Screen.NORMAL_FONT);

			screen.write("Premi ENTER per iniziare", normalColor, transparency * 2 / 3,
			             screen.width - screen.getFont().widthOf("Premi ENTER per iniziare") - 5,
			             screen.height - screen.getFont().getHeight() - 5);
		}
	}

}
