package com.tu.mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.tu.mario.Handler;
import com.tu.mario.Id;

public  class Entity {
	public int x, y;
	public int width, height;
	public int facing = 0;//0 is left , 1 is right
	
	public boolean solid;
	public boolean jumping = false;
	public boolean falling = true;
	
	public int velX, velY;
	
	public Id id;
	public Handler handler;
	
	public double gravity = 0.0;

	public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}

	public  void render(Graphics g) {
		
	}

	public  void tick() {
		x += velX;
		y += velY;
	}

	public void die() {
		handler.removeEntity(this);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Id getId() {
		return id;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	// https://www.itread01.com/p/319215.html
	// check locate
	public Rectangle getBounds() {

		return new Rectangle(getX(), getY(), width, height);

	}

	public Rectangle getBoundsTop() {
		return new Rectangle(getX() + 10, getY(), width - 20, 5);

	}

	public Rectangle getBoundsButtom() {
		return new Rectangle(getX() + 10, getY() + width - 5, width - 20, 5);

	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY() + 10, 5, height - 20);

	}

	public Rectangle getBoundsRight() {
		return new Rectangle(getX() + width - 5, getY() + 10, 5, height - 20);

	}

}
