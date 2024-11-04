package de.Max4K.Projekt;

import static org.lwjgl.opengl.GL11.*;

public class FieldColor {


	private static FieldColor instance;

	private FieldColor() {}



	public static FieldColor getInstance() {
		if (instance == null) {
			instance = new FieldColor();
		}
		return instance;
	}

	public void setFieldColor(int x, int z, boolean wall, int GRID_SIZE, int targetX, int targetY) {
		if(wall) {
			glColor3f(0.8f, 0.8f, 0.8f);
			return;
		}
		if (x == GRID_SIZE / 2 && z == GRID_SIZE / 2) {
			glColor3f(1.0f, 0.0f, 0.0f);//Hauptfeld
		} else if(x == targetX && z ==targetY) {
			glColor3f(1.0f, 1.0f, 0.0f);//Zielfeld
		} else {
			glColor3f(0.3f, 0.6f, 0.3f);//Boden
		}
	}
}
