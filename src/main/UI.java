package main;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Key;


//on screen user interface
public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0; //how long the message will be displayed
	public boolean gameFinished = false;
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		//import font types
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2){
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			//Do playstate stuff later
			
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		
	}
	
	public void drawPauseScreen() {
		String text = "GAME PAUSED";
		//displays text at center of the screen
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		//DIALOGUE WINDOW AT THE BOTTOM OF THE SCREEN
		int x = gp.tileSize*2;
		int y = gp.tileSize*7;
		int width = gp.screenWidth -(gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, gp.tileSize/7*5));
		x += gp.tileSize;
		y += gp.tileSize;
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=gp.tileSize/5*4;
		}
		
		
	}
	
	//draws dialogue window 
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 200); //Black color with 200 opacity
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(8));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}