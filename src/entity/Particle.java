package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity{

	Entity generator; //entity that produces the particle
	Color color;
	int size;
	int xd;
	int yd;
	
	public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxHealth, int xd, int yd) {
		super(gp);
		
		this.generator = generator; //entity that particles appear from
		this.color = color;
		this.size = size; //size in pixels
		this.speed = speed; //how fast the particle flies
		this.maxHealth = maxHealth; //particle life on screen
		this.xd = xd;
		this.yd = yd;
		
		health = maxHealth; 
		
		//center particles to hit object (generator)
		int offset = gp.tileSize/2 - size/2;
		worldX = generator.worldX+offset;
		worldY = generator.worldY+offset;
		
	}
	
	public void update() {
		//make particle move
		
		health--;

		if(health < maxHealth/4) {yd++;}
		
		worldX+=xd*speed;
		worldY+=yd*speed;
		
		//gravity of particles
	
		if(health == 0) {alive = false;}
		
	}
	
	public void draw(Graphics2D g2) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);
	}

}
