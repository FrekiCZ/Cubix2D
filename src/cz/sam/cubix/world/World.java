package cz.sam.cubix.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.sam.cubix.gui.IProgressUpdate;
import cz.sam.cubix.log.Log;

public class World {
	
	private int WORLD_SIZE = 16;
	
	public Random rand = new Random();
	private int[][] map = new int[WORLD_SIZE][WORLD_SIZE];
	
	protected boolean generated;
	
	public World() {
		
	}
	
	public void generate(IProgressUpdate update) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int size = WORLD_SIZE * WORLD_SIZE - 2;
				int nowSize = 0;
				for(int x = 0; x < WORLD_SIZE; x++) {
					for(int y = 0; y < WORLD_SIZE; y++) {
						map[x][y] = rand.nextInt(2);
						update.intUpdate((int)(((float)nowSize / (float)size) * 100F));
						nowSize++;
					}
				}
				generated = true;
			}
			
		}).start();
	}
	
	public void tick() {
		
	}
	
	public int[][] getMap() {
		return this.map;
	}
	
	public boolean isGenrated() {
		return this.generated;
	}
	
}