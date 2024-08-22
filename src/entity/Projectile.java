package entity;

import main.GamePanel;

public class Projectile extends Entity{

	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
		
		
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.health = this.maxHealth;
	}
	
	public void update() {
		
		//check collision with player or monster
		if(user == gp.player) {
			int monIndex = gp.cDetector.checkEntity(this, gp.mon);
			if(monIndex != 999) {
				gp.player.dmgMonster(monIndex, atk);
				alive = false;
			}
		}
		else {
			boolean contactPlayer = gp.cDetector.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				dmgPlayer(atk);
				alive = false;
			}
		}
		
		switch(direction) {
		case "up": worldY -= speed; break;
		case "down": worldY += speed; break;
		case "left": worldX -= speed; break;
		case "right": worldX += speed; break;
		}
		
		//projectile has a max distance it can travel
		health--;
		if(health <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public boolean hasResource(Entity user) {
		
		boolean haveResource = false;
		return haveResource;
	}

	public void useResource(Entity user) {
	}

}
