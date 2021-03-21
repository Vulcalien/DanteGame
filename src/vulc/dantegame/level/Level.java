/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import vulc.dantegame.Game;
import vulc.dantegame.gfx.Screen;
import vulc.dantegame.gfx.menu.TransitionOverlay;
import vulc.dantegame.level.entity.Entity;
import vulc.dantegame.level.entity.mob.MovingPlatform;
import vulc.dantegame.level.entity.mob.Player;
import vulc.dantegame.level.tile.Tile;
import vulc.vdf.VDFObject;
import vulc.vdf.io.binary.BinaryVDF;

public class Level {

	// Tile size: the number of pixels per tile
	public static final int T_SIZE = 64;

	public final int width, height;
	public final byte[] tiles;

	public final List<Entity> entities = new ArrayList<Entity>();
	public final List<Entity>[] entitiesInTile;

	public final Comparator<Entity> renderSorter = (e1, e2) -> {
		// player is always over platform
		if(e1 instanceof MovingPlatform && e2 instanceof Player) return -1;
		return 0;
	};

	public Player player;

	@SuppressWarnings("unchecked")
	public Level(int width, int height) {
		this.width = width;
		this.height = height;

		this.tiles = new byte[width * height];
		this.entitiesInTile = new ArrayList[width * height];
		for(int i = 0; i < entitiesInTile.length; i++) {
			entitiesInTile[i] = new ArrayList<Entity>();
		}
	}

	@SuppressWarnings("unchecked")
	public Level(String filename) {
		VDFObject obj = null;
		try {
			obj = (VDFObject) BinaryVDF.deserialize(Level.class.getResourceAsStream("/levels/" + filename));
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.tiles = obj.getByteArray("tiles");

		this.width = obj.getInt("mapWidth");
		this.height = tiles.length / width;

		this.entitiesInTile = new ArrayList[width * height];
		for(int i = 0; i < entitiesInTile.length; i++) {
			entitiesInTile[i] = new ArrayList<Entity>();
		}
	}

	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);

			int xt0 = posToTile(e.x);
			int yt0 = posToTile(e.y);

			e.tick();

			if(e.removed) {
				removeEntityFromTile(e, xt0, yt0);
				removeEntity(e);
				i--;
			} else {
				int xt1 = posToTile(e.x);
				int yt1 = posToTile(e.y);

				if(xt1 != xt0 || yt1 != yt0) {
					removeEntityFromTile(e, xt0, yt0);
					insertEntityInTile(e, xt1, yt1);
				}
			}
		}
	}

	public void render(Screen screen, int xTiles, int yTiles) {
		screen.setOffset(Game.player.x - screen.width / 2,
		                 Game.player.y - 80 - screen.height / 2); // player is shifted by 80 pixels in y-axix

		int xt0 = posToTile(screen.xOffset);
		int yt0 = posToTile(screen.yOffset);
		int xt1 = xt0 + xTiles - 1;
		int yt1 = yt0 + yTiles - 1;

		for(int yt = yt0; yt <= yt1; yt++) {
			if(yt < 0 || yt >= height) continue;

			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt < 0 || xt >= width) continue;

				getTile(xt, yt).render(screen, this, xt, yt);
			}
		}

		List<Entity> entities = getEntitiesInTile(xt0 - 1, yt0 - 1, xt1 + 1, yt1 + 1);
		entities.sort(renderSorter);
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}

	public void setTile(Tile tile, int xt, int yt) {
		if(xt < 0 || xt >= width || yt < 0 || yt >= height) return;
		tiles[xt + yt * width] = tile.id;
	}

	public Tile getTile(int xt, int yt) {
		if(xt < 0 || xt >= width || yt < 0 || yt >= height) return null;
		return Tile.TILES[tiles[xt + yt * width]];
	}

	public void addEntity(Entity e) {
		if(e instanceof Player) this.player = (Player) e;

		entities.add(e);
		insertEntityInTile(e, posToTile(e.x), posToTile(e.y));
		e.removed = false;
		e.level = this;
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
		removeEntityFromTile(e, posToTile(e.y), posToTile(e.y));
		e.removed = true;
	}

	private void insertEntityInTile(Entity e, int xt, int yt) {
		if(xt < 0 || xt >= width || yt < 0 || yt >= height) return;
		entitiesInTile[xt + yt * width].add(e);
	}

	private void removeEntityFromTile(Entity e, int xt, int yt) {
		if(xt < 0 || xt >= width || yt < 0 || yt >= height) return;
		entitiesInTile[xt + yt * width].remove(e);
	}

	public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
		List<Entity> result = new ArrayList<Entity>();

		int xt0 = posToTile(x0) - 1;
		int yt0 = posToTile(y0) - 1;
		int xt1 = posToTile(x1) + 1;
		int yt1 = posToTile(y1) + 1;

		for(int yt = yt0; yt <= yt1; yt++) {
			if(yt < 0 || yt >= height) continue;

			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt < 0 || xt >= width) continue;

				List<Entity> inTile = entitiesInTile[xt + yt * width];
				for(int i = 0; i < inTile.size(); i++) {
					Entity e = inTile.get(i);

					if(e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}

	public List<Entity> getEntitiesInTile(int xt0, int yt0, int xt1, int yt1) {
		List<Entity> result = new ArrayList<Entity>();
		for(int yt = yt0; yt <= yt1; yt++) {
			if(yt < 0 || yt >= height) continue;

			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt < 0 || xt >= width) continue;

				result.addAll(entitiesInTile[xt + yt * width]);
			}
		}
		return result;
	}

	public static int tileToPos(int tile) {
		return tile * T_SIZE;
	}

	public static int posToTile(int pos) {
		return Math.floorDiv(pos, T_SIZE);
	}

	public void onPlayerDeath() {
		if(Game.overlay != null) return;
		Game.overlay = new TransitionOverlay(240, 0x000000, this::onPlayerDeathAction);
	}

	protected void onPlayerDeathAction() {
	}

}
