package de.Max4K.Projekt;

import java.util.Random;

public class FieldGenerator {
	private static final int GRID_SIZE = 30;

	public boolean[][] generateField() {
		boolean[][] field = new boolean[GRID_SIZE][GRID_SIZE];
		Random random = new Random();
		int centerX = GRID_SIZE / 2;
		int centerY = GRID_SIZE / 2;

		//startfeld in der mitte
		field[centerX][centerY] = true;


		for (int i = 0; i < 2; i++) {//i sind die versuche
			int x = centerX;
			int y = centerY;


			while (true) {

				field[x][y] = true; //feld erscheint.


				int direction = random.nextInt(4);//random richtung
				switch (direction) {
					case 0: x++; break;//rechts
					case 1: x--; break;//links
					case 2: y++; break;//oben
					case 3: y--; break;//unten

					default: break;
				}


				if (x<0 || x>=GRID_SIZE || y<0 ||y>=GRID_SIZE) {//rand erreicht?
					break;
				}
			}
		}
		return field;
	}
}
