package com.tu.mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.tu.mario.entity.Entity;
import com.tu.mario.entity.mob.Goomba;
import com.tu.mario.entity.mob.Player;
import com.tu.mario.tile.Tile;
import com.tu.mario.tile.Wall;
import com.tu.powerUp.Mushroom;

public class Handler {

	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();

//	public Handler() {
//		createLevel();
//	}

	public void render(Graphics g) {
		// for each tipe
		for (Entity en : entity) {
			en.render(g);
		}
		for (Tile ti : tile) {
			ti.render(g);
		}

	}

	public void tick() {
		for (Entity en : entity) {
			en.tick();
		}
		for (Tile ti : tile) {
			ti.tick();
		}
	}

	public void addEntity(Entity en) {
		entity.add(en);

	}

	public void removeEntity(Entity en) {
		entity.remove(en);
	}

	public void addTile(Tile ti) {
		tile.add(ti);

	}

	public void removeTile(Tile ti) {
		tile.remove(ti);
	}

	public void createLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = level.getRGB(x, y);

				// hex : 2 byte 1 -> 1, 1 byte 0 -> 0, 2byte 0 -> 0
				// byte to int process
				int red = (pixel >> 16) & 0xff; //ff0000
				int green = (pixel >> 8) & 0xff;//00ff00
				int blue = (pixel) & 0xff;//0000ff

				if (red == 0 && blue == 0 && green == 0) {
					addTile(new Wall(x * 64, y * 64, 64, 64, true, Id.wall, this));
				}
				if (red == 0 && blue == 255 && green == 0) {
					addEntity(new Player(x * 64, y * 64, 64, 64, false, Id.player, this));
				}
				if (red == 255 && blue == 0 && green == 0) {
					addEntity(new Mushroom(x * 64, y * 64, 64, 64, true, Id.mushroom, this));
				}
				if (red == 255 && blue == 0 && green == 119) {
					addEntity(new Goomba(x * 64, y * 64, 64, 64, true, Id.goomba, this));
				}
			}
		}
	}

}
