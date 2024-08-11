package entity;

import java.awt.image.BufferedImage;

//super/parent class for player and character classes
public class Entity {
	
	public int worldX, worldY;
	public int speed;
	//an image with an accessible buffer of image data, stores image files
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
}
