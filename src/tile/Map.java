package tile;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Map extends TileManager{

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean minimapOn;
	
	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;
		createWorldMap();
	}
	
	public void createWorldMap() {
		
		worldMap = new BufferedImage[gp.maxMap];
		int worldMapWidth = gp.tileSize*gp.maxWorldCol;
		int worldMapHeight = gp.tileSize*gp.maxWorldRow;
		
		for(int i = 0; i < gp.maxMap; i++) {
			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
			
			int col = 0;
			int row = 0;
			
			//draw tile one by one 
			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				int tileNum = mapTileNum[i][col][row];
				int x = gp.tileSize * col;
				int y = gp.tileSize * row;
				g2.drawImage(tile[tileNum].image, x, y, null);
				col++;
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
		}
	}
	
	public void drawFullMapScreen(Graphics2D g2) {
		
		//Background color
		g2.setColor(new Color(0,0,0));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		//Draw Map at center of screen
		g2.setColor(Color.white);
		int width = 900;
		int height = 900;
		int x = (gp.screenWidth-width)/2;
		int y = (gp.screenHeight-height)/2;
		g2.setStroke(new BasicStroke(10));
		g2.fillRoundRect(x-8, y-8, width+16, height+16, 20, 20);
		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
		
		//Draw player on map
		double scale = (double)(gp.tileSize*gp.maxWorldCol)/width;
		int playerX = (int)(x + gp.player.worldX/scale);
		int playerY = (int)(y + gp.player.worldY/scale);
		int playerSize = (int)(gp.tileSize/scale);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
		
		//Hint
		g2.setFont(gp.ui.maruMonica.deriveFont(32f));
		g2.setColor(Color.white);
		g2.drawString("Press M or ESC to close", 750, 550);
	}
	
	public void drawMiniMap(Graphics2D g2) {
		
		if(minimapOn == true) {
			int width = 300;
			int height = 300;
			int x = gp.screenWidth - width - 50;
			int y = 50;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f));
			g2.drawImage(worldMap[gp.currentMap], x,y, width, height, null);
			
			//Draw player on map
			double scale = (double)(gp.tileSize*gp.maxWorldCol)/width;
			int playerX = (int)(x + gp.player.worldX/scale)-5;
			int playerY = (int)(y + gp.player.worldY/scale)-5;
			int playerSize = (int)(gp.tileSize/5);
			g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}

}
