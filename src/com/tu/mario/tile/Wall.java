package com.tu.mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import com.tu.mario.Game;
import com.tu.mario.Handler;
import com.tu.mario.Id;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);

	}

	public void render(Graphics g) {
		g.drawImage(Game.brick.getBufferedImage(), x, y, width, height, null);
//		g.setColor(Color.red);
//		g.fillRect(x, y, width, height);
//		g.setColor(Color.BLACK);
//		g.drawLine(x,y, x, y+height);
	}

	public void tick() {

	}

}
