package main;

import java.awt.BasicStroke;


import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;


//on screen user interface
public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0; //how long the message will be displayed
	ArrayList<String> msg = new ArrayList<>();
	ArrayList<Integer> msgCounter = new ArrayList<>();

	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; //0: first screen, 1: second screen
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int substate = 0;
	int counter = 0;
	public Entity npc;
	
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
		
		//CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzecoin = new OBJ_Coin_Bronze(gp);
		coin = bronzecoin.down1;

	}
	
	public void addMsg(String text) {
//		message = text;
//		messageOn = true;
		
		msg.add(text);
		msgCounter.add(0);
		
	}
	
	public void draw(Graphics2D g2){
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}else {
			//PLAY STATE
			if(gp.gameState == gp.playState) {
				drawPlayerLife();
				drawMsg();
			}
			//PAUSE STATE
			if(gp.gameState == gp.pauseState) {
				drawPauseScreen();
			}
			//DIALOGUE STATE
			if(gp.gameState == gp.dialogueState) {
				drawDialogueScreen();
			}
			//CHARACTER SCREEN
			if(gp.gameState == gp.characterState) {
				drawCharacterScreen();
				drawInventory(gp.player, true);
			}
			//OPTION SCREEN
			if(gp.gameState == gp.optionState) {
				drawOptionScreen();
			}
			//GAME OVER SCREEN
			if(gp.gameState == gp.gameOverState) {
				drawGameOverScreen();
			}
			if(gp.gameState == gp.transitionState) {
				drawTransition();
			}
			if(gp.gameState == gp.tradeState) {
				drawTradeScreen();
			}
		}
	}
	
	public void drawPlayerLife() {
		
		//gp.player.life = 5;
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//DRAW MAX LIFE
		while(i < gp.player.maxHealth/2){
			g2.drawImage(heart_blank,  x,  y,  null);
			i++;
			x+= gp.tileSize;
		}
		
		//reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//draw current life
		while(i < gp.player.health){
			g2.drawImage(heart_half,  x,  y,  null);
			i++;
			if(i < gp.player.health) {
				g2.drawImage(heart_full,  x,  y,  null);
			}
			i++;
			x+=gp.tileSize;
		}
		
		//DRAW MAX MANA
		x = gp.tileSize/2;
		y = gp.tileSize*3/2;
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x+= gp.tileSize*2/3;
		}
		x = gp.tileSize/2;
		y = gp.tileSize*3/2;
		i = 0;
		//DRAW CURRENT MANA
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x+= gp.tileSize*2/3;
		}
		
	}
	
	public void drawMsg() {
		
		int msgX = gp.tileSize/2;
		int msgY = gp.tileSize*5;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < msg.size(); i++) {
			
			if(msg.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(msg.get(i), msgX+3, msgY+3);
				g2.setColor(Color.white);
				g2.drawString(msg.get(i), msgX, msgY);
				
				//set counter += 1
				int counter = msgCounter.get(i)+1;
				msgCounter.set(i, counter);
				msgY+= 50;
				
				if(msgCounter.get(i) >= 180) {
					msg.remove(i);
					msgCounter.remove(i);
				}
			}
			
		}
		
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0,  gp.screenWidth,  gp.screenHeight);
			
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize*2));
			String text = "Adventure 2D Game";
			int x = getXForCenteredText(text);
			int y = gp.tileSize*3;
			
			//TEXT SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+gp.scale+1, y+gp.scale+1);
			
			//MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text,  x,  y);
			
			//CHARACTER IMAGE
			x = gp.screenWidth/2 - gp.tileSize;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize));
			text = "NEW GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize));
			text = "LOAD GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize*1;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.tileSize));
			text = "QUIT";
			x = getXForCenteredText(text);
			y += gp.tileSize*1;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
		}
		else if(titleScreenState == 1) {
			//CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, gp.tileSize));
			
			String text = "Select your class!";
			int x = getXForCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXForCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
			
			text = "Thief";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
			
			text = "Sorcerer";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
			
			text = "Back";
			x = getXForCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize/3*2, y);
			}
		}
		
		
	}
	
	public void drawPauseScreen() {
		String text = "GAME PAUSED";
		//displays text at center of the screen
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		//DIALOGUE WINDOW AT THE BOTTOM OF THE SCREEN
		int x = gp.tileSize*2;
		int y = gp.tileSize*7;
		int width = gp.screenWidth -(gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubwindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, gp.tileSize/7*5));
		x += gp.tileSize;
		y += gp.tileSize;
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=gp.tileSize/5*4;
		}
		
	}
	
	private void drawCharacterScreen() {

		//CREATE SUBWINDOW FRAME
		
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10+gp.tileSize/3;
		drawSubwindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(60f));
		
		//for left align, labels
		int textXRight = frameX + gp.tileSize/gp.scale;
		int textXLeft;
		int textY = frameY + gp.tileSize;
		final int lineHeight = gp.tileSize/gp.scale*4;
		
		//for right align, values
		int tailX = (frameX + frameWidth) - gp.tileSize/gp.scale*3;
		textY = frameY + gp.tileSize;
		String value;
		
		//Level
		g2.drawString("Level", textXRight, textY);
		value = String.valueOf(gp.player.level);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+= lineHeight;
		
		//Life
		g2.drawString("Health", textXRight, textY);
		value = String.valueOf(gp.player.health + "/"+ gp.player.maxHealth);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Mana
		g2.drawString("Mana", textXRight, textY);
		value = String.valueOf(gp.player.mana + "/"+ gp.player.maxMana);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Strength
		g2.drawString("Strength", textXRight, textY);
		value = String.valueOf(gp.player.str);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Vitality
		g2.drawString("Vitality", textXRight, textY);
		value = String.valueOf(gp.player.vit);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Attack
		g2.drawString("Attack", textXRight, textY);
		value = String.valueOf(gp.player.atk);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Defense
		g2.drawString("Defense", textXRight, textY);
		value = String.valueOf(gp.player.def);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//EXP
		g2.drawString("EXP", textXRight, textY);
		value = String.valueOf(gp.player.exp);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Next Level EXP
		g2.drawString("Next Level", textXRight, textY);
		value = String.valueOf(gp.player.nextLvlExp);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//Coins
		g2.drawString("Money", textXRight, textY);
		value = String.valueOf(gp.player.coins);
		textXLeft = getXforAlignRight(value, tailX);
		g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//WPN with IMG
		g2.drawString("Weapon", textXRight, textY);
		g2.drawImage(gp.player.currentWpn.down1, tailX-gp.tileSize+25, textY-gp.tileSize+25, null);
		//value = String.valueOf(gp.player.currentWpn.name);
		//textXLeft = getXforAlignRight(value, tailX);
		//g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//SHIELD with IMG
		g2.drawString("Shield", textXRight, textY);
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize+25, textY-gp.tileSize+25, null);
		//value = String.valueOf(gp.player.currentShield.name);
		//textXLeft = getXforAlignRight(value, tailX);
		//g2.drawString(value, textXLeft, textY);
		textY+=lineHeight;
		
		//VALUES
		
	}
	
	public void drawInventory(Entity entity, boolean cursor) {
		
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		if(entity == gp.player) {
			frameX = gp.tileSize*9;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		//FRAME
		drawSubwindow(frameX, frameY, frameWidth, frameHeight);
		
		//INVENTORY SLOTS
		final int slotXstart = frameX + gp.tileSize/2-gp.scale*2;
		final int slotYstart = frameY + gp.tileSize/2-gp.scale;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+gp.scale;
		
		//DRAW PLAYER INVENTORY ITEMS
		for(int i = 1; i < entity.inventory.size(); i++)	{
			
			//EQUIP CURSOR
			if(entity.inventory.get(i) == entity.currentWpn || 
			entity.inventory.get(i) == entity.currentShield) {
				g2.setColor(Color.green);
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			//DISPLAY ITEM AMOUNT
			if(entity == gp.player && entity.inventory.get(i).stackAmount > 1) {
				g2.setFont(g2.getFont().deriveFont(48f));
				int amountX;
				int amountY;
				
				String s = "" + entity.inventory.get(i).stackAmount;
				amountX = getXforAlignRight(s, slotX + gp.tileSize-gp.tileSize/10) + gp.tileSize/25;
				amountY = slotY + gp.tileSize;
				
				//TEXT SHADOW
				g2.setColor(new Color(60,60,60));
				g2.drawString(s, amountX, amountY);
				//TEXT
				g2.setColor(Color.white);
				g2.drawString(s, amountX-gp.tileSize/30, amountY-gp.tileSize/30);
			}
			
			slotX+=slotSize;
			if(i % 5 == 0) {
				slotY+=slotSize;
				slotX = slotXstart;
			}
		}
		
		//CURSOR
		if(cursor == true) {
			int cursorX = slotXstart+(slotSize*slotCol);
			int cursorY = slotYstart+(slotSize*slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(gp.scale));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, gp.tileSize/5, gp.tileSize/5);
			
			// ITEM DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
			//DRAW DESCRIPTION TXT
			int textX = dFrameX + gp.tileSize/4;
			int textY = dFrameY + gp.tileSize/2+gp.scale*4;
			g2.setFont(g2.getFont().deriveFont(50F));
			int itemIndex = getItemIndex(slotCol, slotRow);
			
			//DESCRIPTION OF CURRENTLY SELECTED ITEM
			if(itemIndex < entity.inventory.size()) {
				drawSubwindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY+=gp.tileSize/2;
				}
			}
		}
		
		
	}
	
	public void drawOptionScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(60F));
		
		//SUB WINDOW
		int frameX = gp.tileSize*4;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawSubwindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(substate) {
		case 0: option_top(frameX, frameY); break;
		case 1: break;
		case 2: option_controls(frameX, frameY); break;
		case 3: option_endGameConfirm(frameX, frameY); break;
		}
		
		gp.keyH.ePressed = false;
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0, 150));
		g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 190f));
		
		text = "GAME OVER";
		//text shadow
		g2.setColor(Color.black);
		x = getXForCenteredText(text);
		y = gp.tileSize*5;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.red);
		g2.drawString(text, x-gp.tileSize/10, y-gp.tileSize/10);
		
		//Retry
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(75f));;
		text = "Retry";
		x = getXForCenteredText(text);
		y+= gp.tileSize*3;
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize/2, y);
		}
		
		//Back to title screen
		text = "Quit";
		x = getXForCenteredText(text);
		y+= gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize/2, y);
		}
		
	}
	
	public void option_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "OPTIONS";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				else {
					gp.fullScreenOn = true;
				}
			}
		}
		
		//MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
		}
		
		//SOUND EFFECTS
		textY += gp.tileSize;
		g2.drawString("Sound Effects", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
		}
		
		//CONTROL
		textY += gp.tileSize;
		g2.drawString("View Controls", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				substate = 2;
				commandNum = 0;
			}
		}
		
		//EXIT GAME
		textY += gp.tileSize;
		g2.drawString("Exit Game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				substate = 3;
				commandNum = 0;
			}
		}
		
		//BACK
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				gp.gameState = gp.playState;
			}
		}
		
		
		//FULL SCREEN CHECKBOX
		textX = frameX + (int)(gp.tileSize*5.5);
		textY = frameY + gp.tileSize*2+gp.tileSize/2;
		g2.setStroke(new BasicStroke(3));
		if(gp.fullScreenOn == true) {
			g2.setColor(Color.GREEN);
			g2.fillRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
			g2.setColor(Color.WHITE);
		}
		g2.drawRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
		
		
		//MUSIC VOLUME
		textY+=gp.tileSize;
		int volumeWidth = gp.tileSize*2/5*gp.music.volumeScale;
		g2.setColor(Color.GREEN);
		g2.fillRect(textX, textY, volumeWidth, gp.tileSize/2);
		g2.setColor(Color.WHITE);
		g2.drawRect(textX, textY, gp.tileSize*2, gp.tileSize/2);
		
		
		//SOUND EFFECT VOLUME
		textY+=gp.tileSize;
		volumeWidth = gp.tileSize*2/5*gp.soundEffect.volumeScale;
		g2.setColor(Color.GREEN);
		g2.fillRect(textX, textY, volumeWidth, gp.tileSize/2);
		g2.setColor(Color.WHITE);
		g2.drawRect(textX, textY, gp.tileSize*2, gp.tileSize/2);
		
		gp.config.saveConfig();
	}
	
	public void option_controls(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "CONTROLS";
		textX = getXForCenteredText(text);
		textY = frameY+gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX+gp.tileSize/2;
		textY+=gp.tileSize;
		g2.drawString("Move", textX, textY); textY+=gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY+=gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY+=gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
		g2.drawString("Options", textX, textY); textY+=gp.tileSize;
		
		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
		g2.drawString("E", textX, textY); textY+=gp.tileSize;
		g2.drawString("R", textX, textY); textY+=gp.tileSize;
		g2.drawString("C", textX, textY); textY+=gp.tileSize;
		g2.drawString("P", textX, textY); textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY); textY+=gp.tileSize;
		
		//BACK BUTTON
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("BACK", textX, textY);
		
		if(commandNum == 0) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				substate = 0;
				commandNum = 3;
			}
		}
		
	}
	
	public void option_endGameConfirm(int frameX, int frameY) {
		int textX = frameX+gp.tileSize/2;
		int textY = frameY+ gp.tileSize*3;
		
		currentDialogue = "Quit the game and return \nto the title screen?";
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line,  textX,  textY);
			textY+=(int)(gp.tileSize/1.5);
		}
		
		//YES
		String text = "Yes";
		textX = getXForCenteredText(text);
		textY+=gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				substate = 0;
				gp.gameState = gp.titleState;
				gp.restart();
				gp.stopMusic();
				msg.clear();
				msgCounter.clear();
			}
		}
		
		//NO
		text = "No";
		textX = getXForCenteredText(text);
		textY+=gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-gp.tileSize/2, textY);
			if(gp.keyH.ePressed == true) {
				substate = 0;
				commandNum = 4;
			}
		}

	}
	
	//transition screen
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0,0,0, counter*15));
		g2.fillRect(0, 0, gp.screenWidth,  gp.screenHeight);
		
		//restore values after transitions
		if(counter >= 17) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	
	public void drawTradeScreen() {
		switch(substate) {
		case 0: tradeSelect(); break;
		case 1: tradeBuy(); break;
		case 2: tradeSell(); break;
		}
	}
	
	public void tradeSelect() {
		drawDialogueScreen();
		
		//draw option window
		int x = gp.tileSize * 11;
		int y = gp.tileSize * 4;
		int width = gp.screenWidth - (gp.tileSize*13);
		int height = gp.tileSize*3;
		drawSubwindow(x,y,width,height);
		
		//DRAW TEXTS
		x+= gp.tileSize;
		y+=(int)gp.tileSize/1.3;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize/2, y);
			if(gp.keyH.ePressed == true) {
				substate = 1;
				gp.keyH.ePressed = false;
			}
		}
		y+=(int)gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize/2, y);
			if(gp.keyH.ePressed == true) {
				substate = 2;
				gp.keyH.ePressed = false;
			}
		}
		y+=(int)gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize/2, y);
			if(gp.keyH.ePressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again!";
			}
		}
		y+=(int)gp.tileSize;
		
		
		
	}

	public void tradeBuy() {
		
		//DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		//DRAW NPC INVENTORY
		drawInventory(npc, true);
		
		//DRAW HINT WINDOW
		int x = gp.tileSize *2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubwindow(x,y,width,height);
		g2.drawString("[ESC] Back", x+gp.tileSize/2, (int)(y+(gp.tileSize*1.2)));
		
		//DRAW OWNED MONEY WINDOW
		x = gp.tileSize *9;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubwindow(x,y,width,height);
		g2.drawString("Your money: " +gp.player.coins, x+gp.tileSize/2, (int)(y+(gp.tileSize*1.2)));
		
		//DRAW PRICE WINDOW
		int itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x=(int)(gp.tileSize*5.5);
			y =(int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubwindow(x,y,width,height);
			g2.drawImage(coin, x+gp.tileSize/6, y+gp.tileSize/6, (int)(gp.tileSize/1.5), (int)(gp.tileSize/1.5), null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = ""+price;
			x = getXforAlignRight(text, gp.tileSize*8);
			g2.drawString(text, x-gp.tileSize/4, y+gp.tileSize-gp.tileSize/4);
			
			//BUY AN ITEM
			if(gp.keyH.ePressed == true) {
				gp.keyH.ePressed = false;
				if(npc.inventory.get(itemIndex).price > gp.player.coins) {
					substate = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You do not have enough money!";
					drawDialogueScreen();
				}
				else {
					if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
						gp.player.coins -= npc.inventory.get(itemIndex).price;
					}
					else {
						substate = 0;
						gp.gameState = gp.dialogueState;
						currentDialogue = "Your inventory is full!";
						drawDialogueScreen();
					}
				}
//				else if(gp.player.inventory.size() == gp.player.maxInventorySize) {
//					substate = 0;
//					gp.gameState = gp.dialogueState;
//					currentDialogue = "Your inventory is full!";
//					drawDialogueScreen();
//				}
//				else {
//					gp.player.coins -= npc.inventory.get(itemIndex).price;
//					gp.player.inventory.add(npc.inventory.get(itemIndex));
//				}
			}
		}
		
	}
	
	public void tradeSell() {
	
		//DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);
		
		int x;
		int y;
		int width;
		int height;
		//DRAW HINT WINDOW
		x = gp.tileSize *2;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubwindow(x,y,width,height);
		g2.drawString("[ESC] Back", x+gp.tileSize/2, (int)(y+(gp.tileSize*1.2)));
		
		//DRAW OWNED MONEY WINDOW
		x = gp.tileSize *9;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubwindow(x,y,width,height);
		g2.drawString("Your money: " +gp.player.coins, x+gp.tileSize/2, (int)(y+(gp.tileSize*1.2)));
		
		//DRAW PRICE WINDOW
		int itemIndex = getItemIndex(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			x=(int)(gp.tileSize*12.5);
			y =(int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubwindow(x,y,width,height);
			g2.drawImage(coin, x+gp.tileSize/6, y+gp.tileSize/6, (int)(gp.tileSize/1.5), (int)(gp.tileSize/1.5), null);
			
			int price = gp.player.inventory.get(itemIndex).price/2;
			String text = ""+price;
			x = getXforAlignRight(text, gp.tileSize*15);
			g2.drawString(text, x-gp.tileSize/4, y+gp.tileSize-gp.tileSize/4);
			
			if(gp.keyH.ePressed == true) {
				gp.keyH.ePressed = false;
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWpn 
				|| gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
					commandNum = 0;
					substate = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You cannot sell an equipped item!";
				}
				else {
					if(gp.player.inventory.get(itemIndex).stackAmount > 1) {
						gp.player.inventory.get(itemIndex).stackAmount--;
					}else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coins += price;
				}
			}
		}
	}
	
	public int getItemIndex(int slotCol, int slotRow) {
		int itemIndex = slotCol+(slotRow*5)+1;
		return itemIndex;
	}
	
	//draws dialogue window 
	public void drawSubwindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 200); //Black color with 200 opacity
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(8));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXforAlignRight(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}

