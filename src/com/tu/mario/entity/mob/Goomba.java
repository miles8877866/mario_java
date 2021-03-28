package com.tu.mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import com.tu.mario.Game;
import com.tu.mario.Handler;
import com.tu.mario.Id;
import com.tu.mario.entity.Entity;
import com.tu.mario.gfx.Sprite;
import com.tu.mario.tile.Tile;

public class Goomba extends Entity {
	private Random rand = new Random();
	private boolean animate = false;
	private int frame = 0;
	private int frameDelay = 0;

	public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		// TODO Auto-generated constructor stub
		int dir = rand.nextInt(2);
		switch (dir) {
		case 0:
			setVelX(-3);
			break;
		case 1:
			setVelX(3);
			break;
		}
	}

	public void render(Graphics g) {
		if (facing == 0) {
			g.drawImage(Game.goomba[5+frame ].getBufferedImage(), x, y, width, height, null);
		} else if (facing == 1) {
			g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);

		}
		int dir = rand.nextInt(2);
		switch (dir) {
		case 0:
			setVelX(-3);
			break;
		case 1:
			setVelX(3);
			break;
		}
	}

	public void tick() {
		x += velX;
		y += velY;

		if (velX != 0)
			animate = true;
		else
			animate = false;

		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);
			if (!t.solid)
				break;

			if (t.getId() == Id.wall) {
				// inter touch

				if (getBoundsButtom().intersects(t.x, t.y, t.width, t.height)) {
					setVelY(0);
					if (falling)
						falling = false;
				} else {
					if (!falling) {
						falling = true;
						gravity = 0.8;

					}
				}
				if (getBoundsLeft().intersects(t.x, t.y, t.width, t.height)) {
					setVelX(3);

				}
				if (getBoundsRight().intersects(t.x, t.y, t.width, t.height)) {
					setVelX(-3);

				}
			}

//			if (falling) {
//				gravity += 0.1;
//				setVelY((int) gravity);
//			}

			if (animate) {
				frameDelay++;
				if (frameDelay >= 3) {
					frame++;
					if (frame >= 4) {
						frame = 0;
					}
					frameDelay = 0;
				}
			}

		}
	}
}
