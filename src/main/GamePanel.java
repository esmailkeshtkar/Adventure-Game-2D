package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

//game screen
public class GamePanel extends JPanel implements Runnable{
	
	//Screen settings
	public final int originalTileSize = 16; //16x16 tile standard for retro games
	public final int scale = 5; //scaling the size according to monitor screen sizes
	public final int tileSize = originalTileSize * scale;
	//4:3 ratio 16 tiles horizontally and 12 vertically
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //1024 pixels
	public final int screenHeight = tileSize * maxScreenRow; //768 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight  = tileSize * maxWorldRow;
	
	//FPS, the number of frames to be drawn per second
	int FPS = 60;
	
	public TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionDetector cDetector = new CollisionDetector(this);
	public AssetPlacer aPlacer = new AssetPlacer(this);
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aPlacer.setObject();
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);//passing Gamepanel to thread constructor
		gameThread.start();
		
	}
	
	@Override
	//creating game loop in the run method
	/*
	 * public void run() {
	 * 
	 * double drawInterval = 1000000000/FPS; // 1 billion nanoseconds = one second
	 * divided by FPS for 60 FPS double nextDrawTime = System.nanoTime() +
	 * drawInterval; //next draw time will be current time + drawInterval
	 * 
	 * //as long as the gameThread exists while(gameThread != null) {
	 * 
	 * // 1 UPDATE: update information such as character positions update();
	 * 
	 * // 2 DRAW: draw the screen with the updated information repaint();
	 * 
	 * //subtract current time from next draw Time, how much time remaining until
	 * next draw time try { double remainingTime = nextDrawTime - System.nanoTime();
	 * remainingTime = remainingTime/1000000;
	 * 
	 * if(remainingTime < 0) { remainingTime = 0; }
	 * 
	 * //pauses game loop until sleep time is over Thread.sleep((long)
	 * remainingTime);
	 * 
	 * nextDrawTime += drawInterval;
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * }
	 */
	
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0; 
		int drawCount = 0;
		 
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			//how much time has passed, divided by the draw interval added to the delta
			delta += (currentTime - lastTime) / drawInterval; 
			timer+= (currentTime - lastTime);
			lastTime = currentTime;
			
			//every loop past time is added to delta, when delta reaches the draw interval we update and repaint and reset delta
			if(delta >= 1){
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
		}
	}
	public void update() {
		
		player.update();
		
	}
	
	//standard method to draw on JPanel
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//draw tile
		tileM.draw(g2);

		//draw object
		for(int i = 0; i <  obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		//draw player
		player.draw(g2);
		
		g2.dispose();
	}
	
}
