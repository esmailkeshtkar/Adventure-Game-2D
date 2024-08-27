package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//user controls
public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, shotKeyPressed;
	//DEBUG
	boolean showDebugText = false;
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//gets the code of the key that was pressed
		int code = e.getKeyCode();
		//TITLE STATE (PRESS E)
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		//PLAY STATE
		else if(gp.gameState == gp.playState) {
			playState(code);
		}
			
		//PAUSE STATE (PRESS P)
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		
		//DIALGOUE STATE (PRESS E NEAR NPC)
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		
		//CHARACTER STATE (PRESS C)
		else if(gp.gameState == gp.characterState) {
			characterState(code);
		}
		
		else if(gp.gameState == gp.optionState) {
			optionState(code);
		}
	}
	
	public void titleState(int code) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 2)
			{
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_E) {
			if(gp.ui.commandNum == 0) {
				gp.playMusic(0);
				gp.gameState = gp.playState;
			}
			else if (gp.ui.commandNum == 1) {
				//add later
			}
			else if (gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	
	public void playState(int code) {
		//W Key pressed
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_E) {
			ePressed = true;
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_R) {
			shotKeyPressed = true;
		}
		//OPTION STATE
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
		}
		
		//DEBUG KEY
		if(code == KeyEvent.VK_T) {
			if(showDebugText == false) {
				showDebugText = true;
			}
			else if(showDebugText == true)
			{
				showDebugText = false;
			}
		}
		//refresh map
		if(code == KeyEvent.VK_R) {
			gp.tileM.loadMap("/maps/worldv2.txt");
		}
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_E) {
			gp.gameState = gp.playState;
		}
	}
	
	public void characterState(int code) {
		if(code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) { //can press any button to get out of character screen
			gp.gameState = gp.playState;
		}
		//maneuver cursor around inventory screen
		if(code == KeyEvent.VK_W) {
			if(gp.ui.slotRow > 0) {gp.ui.slotRow--;}
			gp.playSoundEffect(9);
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.slotCol > 0) {gp.ui.slotCol--;}
			gp.playSoundEffect(9);
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.slotRow < 3) {gp.ui.slotRow++;}
			gp.playSoundEffect(9);
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.slotCol < 4) {gp.ui.slotCol++;}
			gp.playSoundEffect(9);
		}
		if(code == KeyEvent.VK_E) {
			gp.player.selectItem();
		}

	}
	
	public void optionState(int code) {
		//GET OUT OF OPTION STATE
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		//ACCEPT KEY
		if(code == KeyEvent.VK_E || code == KeyEvent.VK_ENTER) {
			ePressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.substate) {
			case 0: maxCommandNum = 5; break;
			case 1: break;
			case 2: maxCommandNum = 0; break;
			case 3: maxCommandNum = 1; break;
		}
		
		//navigate option menu with W and S
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSoundEffect(9);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSoundEffect(9);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		//LEFT AND RIGHT FOR VOLUME CONTROLS
		if(code == KeyEvent.VK_A) {//decrease volume
			if(gp.ui.substate == 0) {
				//music
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSoundEffect(9);
				}
				//sound effects
				if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale > 0) {
					gp.soundEffect.volumeScale--;
					gp.playSoundEffect(9);
				}
			}
		}
		if(code == KeyEvent.VK_D) {//increase volume
			if(gp.ui.substate == 0) {
				//music
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSoundEffect(9);
				}
				//sound effects
				if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale < 5) {
					gp.soundEffect.volumeScale++;
					gp.playSoundEffect(9);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_E) {
			ePressed = false;
		}
		if(code == KeyEvent.VK_R) {
			shotKeyPressed = false;
		}
	}

}
