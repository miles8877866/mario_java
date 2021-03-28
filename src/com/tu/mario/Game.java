package com.tu.mario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.tu.mario.entity.Entity;
import com.tu.mario.entity.mob.Player;
import com.tu.mario.gfx.Sprite;
import com.tu.mario.gfx.SpriteSheet;
import com.tu.mario.input.KeyInput;
import com.tu.mario.tile.Wall;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 270;
	public static final int HEIGHT = WIDTH / 14 * 10;
	public static final int SCALE = 4;
	private static final String TITLE = "Super Mario";

	public static Handler handler;
	public static SpriteSheet sheet_player;
	public static SpriteSheet sheet_wall;
	public static Camera cam;//鏡頭跟著
	
	public static Sprite brick;
	public static Sprite []player;
	public static Sprite mushroom;
	public static Sprite []goomba;
	
	public String imageFile_mario = "res/mario.png";
	public String imageFile_wall = "res/wall.png";
	
	private boolean running = false;
	private Thread thread;
	private BufferedImage image;

	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		//
		setPreferredSize(size); // �H���f�վ�
		setMaximumSize(size);
		setMinimumSize(size);
	}

	private void init() {
		handler = new Handler();
		// ***.png
		sheet_player = new SpriteSheet(imageFile_mario);
		sheet_wall = new SpriteSheet(imageFile_wall);
		cam = new Camera();
		
		addKeyListener(new KeyInput());
		
		brick = new Sprite(sheet_wall, 1, 1);
		mushroom = new Sprite(sheet_wall, 2, 1);
		player = new Sprite[8];
		goomba = new Sprite[10]; 
		
		
		
		for(int i=0;i<goomba.length;i++ ) {
			goomba[i] = new Sprite(sheet_player, i+1, 2);
		}
		
		for (int i = 0; i < player.length; i++) {
			player[i] = new Sprite(sheet_player, i + 1, 1);
		}
		
//		handler.addEntity(new Player(0, 0, 64, 64, true, Id.player, handler));
		try {
			image = ImageIO.read(new File("res/map1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.createLevel(image);
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			// ������ // https://openhome.cc/Gossip/JavaGossip-V2/JoinThread.htm
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// �p�ɥ�
		init();
		// focus
		// requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;

			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " Frames per sec " + ticks + " Update per sec ");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	public void tick() {
		handler.tick();
		for(Entity en : handler.entity) {
			if(en.getId() == Id.player) {
				cam.tick(en);
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());
		handler.render(g);
		g.dispose();
		bs.show();
	}

	private int getFrameWidth() {
		return WIDTH * SCALE;
	}

	private int getFrameHieght() {
		return HEIGHT * SCALE;
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		// �T��վ�j�p
		frame.setResizable(false);
		//
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}

}
