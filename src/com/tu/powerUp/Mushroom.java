package com.tu.powerUp;

import java.awt.Graphics;
import java.util.Random;

import com.tu.mario.Game;
import com.tu.mario.Handler;
import com.tu.mario.Id;
import com.tu.mario.entity.Entity;
import com.tu.mario.tile.Tile;

public class Mushroom extends Entity {

	
	private Random rand = new Random();
	public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
		int dir = rand.nextInt(2);
		switch(dir) {
		case 0:
			setVelX(-3);
			break;
		case 1:
			setVelX(3);
			break;
		}
	}

	public void render(Graphics g) {
		g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		for (int i=0;i<handler.tile.size(); i++) {
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
			
//			if(falling) {
//				gravity += 0.1;
//				setVelY((int)gravity);
//			}

		}
	}

}
