package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//user controls
public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed;
	//DEBUG
	boolean checkDrawTime = false;
	
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
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
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
		
//		else if(gp.ui.titleScreenState == 1) {
//			if(gp.gameState == gp.titleState) {
//				if(code == KeyEvent.VK_W) {
//					gp.ui.commandNum--;
//					if(gp.ui.commandNum < 0) {
//						gp.ui.commandNum = 3;
//					}
//				}
//				if(code == KeyEvent.VK_S) {
//					gp.ui.commandNum++;
//					if(gp.ui.commandNum > 3)
//					{
//						gp.ui.commandNum = 0;
//					}
//				}
//				
//				if(code == KeyEvent.VK_E) {
//					if(gp.ui.commandNum == 0) {
//						System.out.println("Do fighter specific stuff");
//						gp.gameState = gp.playState;
//						gp.playMusic(0);
//					}
//					else if (gp.ui.commandNum == 1) {
//						System.out.println("Do fighter specific stuff");
//						gp.gameState = gp.playState;
//						gp.playMusic(0);
//					}
//					else if (gp.ui.commandNum == 2) {
//						System.out.println("Do fighter specific stuff");
//						gp.gameState = gp.playState;
//						gp.playMusic(0);
//					}
//					else if (gp.ui.commandNum == 3) {
//						gp.ui.titleScreenState = 0;
//					}
//				}
//			}
//		}
		
		//PLAY STATE
		else if(gp.gameState == gp.playState) {
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
			
			//DEBUG KEY
			if(code == KeyEvent.VK_T) {
				if(checkDrawTime == false) {
					checkDrawTime = true;
				}
				else if(checkDrawTime == true)
				{
					checkDrawTime = false;
				}
			}
		}
			
		//PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
		}
		
		//DIALGOUE STATE
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_E) {
				gp.gameState = gp.playState;
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
	}

}
