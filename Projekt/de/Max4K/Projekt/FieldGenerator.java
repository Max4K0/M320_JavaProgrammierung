package de.Max4K.Projekt;

import java.util.Random;


public class FieldGenerator {

	public int targetX;
	public int targetY;

	public boolean[][] generateField(int GRID_SIZE) {

		boolean[][] field = new boolean[GRID_SIZE][GRID_SIZE];
		Random random = new Random();

		int centerX = GRID_SIZE / 2;
		int centerY = GRID_SIZE / 2;

		//startfeld in der mitte
		field[centerX][centerY] = true;


		for (int i = 0; i < 3; i++) {//i sind die versuche
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


				if (x<0 || x>=GRID_SIZE || y<0 ||y>=GRID_SIZE) {//rand erreicht? TODO: Methode zum Ziel
					break;
				}
			}
		}


		do {
			targetX = random.nextInt(GRID_SIZE);
			targetY = random.nextInt(GRID_SIZE);
		} while ((!field[targetX][targetY]) || (targetX < GRID_SIZE-5 && targetX > 5) && (targetY < GRID_SIZE-5 && targetY > 5));



		System.out.println("targetX: " + targetX + " targetY: " + targetY);



		return field;
	}


}
