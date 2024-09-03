package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

//where most of the game functionality occurs
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
	public final int maxMap = 10;
	public int currentMap = 0;
	
	//FPS, the number of frames to be drawn per second
	int FPS = 60;
	
	//SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	public CollisionDetector cDetector = new CollisionDetector(this);
	public AssetPlacer aPlacer = new AssetPlacer(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;
	Config config = new Config(this);
	
	//player, entities, npcs, objects, monsters
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][10];
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity mon[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	
	//Game State variables
	public int gameState;
	public int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	public boolean fullScreenOn = false;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aPlacer.setObject();
		aPlacer.setNPC();
		aPlacer.setMonster();
		aPlacer.setInteractiveTile();
		//playMusic(0); //plays the bgm
		//stopMusic();
		gameState = titleState; //game starts from the title
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);//passing Gamepanel to thread constructor
		gameThread.start();
		
	}
	
	public void retry() {
		player.setDefaultValues();
		aPlacer.setNPC();
		aPlacer.setMonster();
	}
	
	public void restart() {
		player.setDefaultValues();
		player.setItems();
		aPlacer.setObject();
		aPlacer.setNPC();
		aPlacer.setMonster();
		aPlacer.setInteractiveTile();
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
		
		if(gameState == playState) {
			//PLAYER
			player.update();
			//NPCS
			for(int i = 0; i < npc[1].length; i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			//MONSTERS
			for(int i = 0; i < mon[1].length; i++) {
				if(mon[currentMap][i] !=null) {
					if(mon[currentMap][i].alive == true && mon[currentMap][i].dying == false) { 
						mon[currentMap][i].update(); 
					}else if(mon[currentMap][i].alive == false) { 
						mon[currentMap][i].checkDrop();
						mon[currentMap][i] = null;
					}
				}
			}
			//PROJECTILES
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) !=null) {
					if(projectileList.get(i).alive == true) { 
						projectileList.get(i).update(); 
					}else if(projectileList.get(i).alive == false) { 
						projectileList.remove(i);
					}
				}
			}
			//PARTICLES 
			for(int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) !=null) {
					if(particleList.get(i).alive == true) { 
						particleList.get(i).update(); 
					}else if(particleList.get(i).alive == false) { 
						particleList.remove(i);
					}
				}
			}
			//INTERACTIVE TILES
			for(int i = 0; i <iTile[1].length; i++) {
				if(iTile[currentMap][i] !=null) {
					iTile[currentMap][i].update();
				}
			}
		}
		
		if(gameState == pauseState) {
			//nothing
		}
		
	}
	
	//standard method to draw on JPanel
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG FOR PERFORMANCE
		long drawStart = 0;
		if(keyH.showDebugText == true) {
			drawStart = System.nanoTime();
		}
		
		//Title Screen
		if(gameState == titleState) {
			
			ui.draw(g2);
		}
		
		//Others
		else {
			//DRAW TILES
			tileM.draw(g2);
			
			//INTERACTIVE TILES
			for(int i = 0; i < iTile[1].length; i++) {
				if(iTile[currentMap][i] !=null)
					iTile[currentMap][i].draw(g2);
			}
			
			//add player to entity list
			entityList.add(player);
			
			//add npcs to entity list
			for(int i = 0; i < npc[1].length; i++) {
				if(npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			
			//add obj to entity list
			for(int i = 0; i < obj[1].length; i++) {
				if(obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}
			
			//add monsters to entity list
			for(int i = 0; i < mon[1].length; i++) {
				if(mon[currentMap][i] != null) {
					entityList.add(mon[currentMap][i]);
				}
			}
			
			//add projectiles
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			//add particles
			for(int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}
			
			// SORT ENTITY LIST BASED ON WORLD Y
			Collections.sort(entityList, new Comparator<Entity>(){
				
				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			
			//DRAW ENTITIES
			for(int i = 0; i < entityList.size(); i++){
				entityList.get(i).draw(g2);
			}
			
			//EMPTY ENTITY LIST
			entityList.clear();
			
			//DRAW UI
			ui.draw(g2);
		}
		
		// DEBUG FOR PERFORMANCE
		if(keyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
			g2.setColor(Color.white);
			int x = 1000;
			int y = 50;
			int lineHeight = 36;
			
			g2.drawString("DEBUG PLAYER INFO", x, y); y+= lineHeight;
			g2.drawString("World X is " +player.worldX, x, y); y+= lineHeight;
			g2.drawString("World Y is " +player.worldY, x, y); y+= lineHeight;
			g2.drawString("Col is " +(player.worldX + player.collisionArea.x)/tileSize, x, y); y+= lineHeight;
			g2.drawString("Row is " +(player.worldY + player.collisionArea.y)/tileSize, x, y); y+= lineHeight;
			g2.drawString("Draw time :" +passed, x, y);
			System.out.println("Draw Time: " +passed);
		}
		
		g2.dispose();
	}
	
	
	//music and SFX
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		
		soundEffect.setFile(i);
		soundEffect.play();
	}
	
	
}
