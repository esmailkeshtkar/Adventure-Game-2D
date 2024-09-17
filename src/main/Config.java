package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			//Full screen settings
			bw.write(String.valueOf(gp.ui.tempFullScreenOn));
			bw.newLine();
			
			//Music volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			//Sound Effect Volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			//Full screen 
			gp.fullScreenOn = Boolean.parseBoolean(s);
			gp.ui.tempFullScreenOn = gp.fullScreenOn;
			
			//Music volume
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			//Sound Effect Volume
			s = br.readLine();
			gp.soundEffect.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
