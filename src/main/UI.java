package main;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;


//on screen user interface
public class UI {
	
	GamePanel gp;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0; //how long the message will be displayed
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);//instantiate font here so that it does not get instantiated every time draw is called
		arial_80B = new Font("Arial", Font.BOLD, 80);//larger font for celebration
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2){
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if(gameFinished == true) {
			
			String text;
			int textLength;
			int x;
			int y;
			
			//treasure message
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //used to calculate the exact coordinates of the center
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			//play time message
			text = "Your time is: " +dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //used to calculate the exact coordinates of the center
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			//congratulations message
			g2.setFont(arial_80B);
			g2.setColor(Color.yellow);
			text = "CONGRATULATIONS!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //used to calculate the exact coordinates of the center
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		}else {
			g2.drawImage(keyImage,  gp.tileSize/2,  gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("x " + gp.player.numKey, 120, 100);
			
			//PLAYTIME
			playTime += (double)1/60;
			g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*13, 75);
			
			//MESSAGE
			if(messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.screenWidth/2-((int)g2.getFontMetrics().getStringBounds(message, g2).getWidth())/2, gp.tileSize*7);
				messageCounter++;
				
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
		
		
	}
}
