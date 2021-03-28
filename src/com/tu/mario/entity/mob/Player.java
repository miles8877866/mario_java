package com.tu.mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;

import com.tu.mario.Game;
import com.tu.mario.Handler;
import com.tu.mario.Id;
import com.tu.mario.entity.Entity;
import com.tu.mario.tile.Tile;

public class Player extends Entity {

	private int frame = 0;
	private int frameDelay = 0;
	private boolean animate = false;

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);

	}

	public void render(Graphics g) {
		if (facing == 0) {
			g.drawImage(Game.player[frame + 4].getBufferedImage(), x, y, width, height, null);
		} else if (facing == 1) {
			g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);

		}

//		g.setColor(Color.blue);
//		g.fillRect(x, y, width, height);
	}

	public void tick() {

		x += velX;
		y += velY;

		
		
		if (y + height >= 760)
			y = 760 - height;

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
				if (getBoundsTop().intersects(t.x, t.y, t.width, t.height)) {
					setVelY(0);
					if (jumping) {
						jumping = false;
						gravity = 1.0;
						falling = true;
					}
				}
				if (getBoundsButtom().intersects(t.x, t.y, t.width, t.height)) {
					setVelY(0);
					if (falling)
						falling = false;
				} else {
					if (!falling && !jumping) {
						gravity = 0.8;
						falling = true;
					}
				}
				if (getBoundsLeft().intersects(t.x, t.y, t.width, t.height)) {
					setVelX(0);
					x = t.getX() + t.width;
				}
				if (getBoundsRight().intersects(t.x, t.y, t.width, t.height)) {
					setVelX(0);
					x = t.getX() - t.width;
				}
			}

		}
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity en = handler.entity.get(i);

			if (en.getId() == Id.mushroom) {
				if (getBounds().intersects(en.getBounds())) {
					int tpX = getX();
					int tpY = getY();
					width *= 1.1;
					height *= 1.1;
					setX(tpX - width);
					setY(tpY - height);
					en.die();
				}
			} else if (en.getId() == Id.goomba) {
				if (getBounds().intersects(en.getBounds())) {
					die();
				}
			}
			if (jumping) {
				gravity -= 0.02;
				setVelY((int) -gravity);
				if (gravity <= 0.0) {
					jumping = false;
					falling = true;
				}
			}
			if (falling) {
				gravity += 0.02;
				setVelY((int) gravity);
			}
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