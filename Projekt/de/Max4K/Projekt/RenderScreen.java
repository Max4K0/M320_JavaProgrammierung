package de.Max4K.Projekt;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class RenderScreen {

	private long window;



	private static final int GRID_SIZE = 50;
	private final int MAX_LEVELS = 3;
	private final boolean FLY_MODE = false;
	private final float FOV = 45.0f;
	private final float RENDER_DISTANCE = 250;
	private boolean TEXTURE_MODE = false;

	private boolean[][] field;
	private final FieldGenerator fieldGen;
	private FieldColor fieldColor;
	private FieldTexture fieldTexture;
	private Inputs inputs;
	private int level = 1;
	private long startTime;
	private long gameTime = 0;

	public RenderScreen() {
		fieldGen = new FieldGenerator();

		fieldColor = FieldColor.getInstance();

		//fieldTexture = new FieldTexture("oak_planks.png", "oak_planks.png","oak_planks.png", "oak_planks.png");
	}


	public void run() {
		System.out.println("----- v" + Version.getVersion() + " -----");

		create();
		inputs = new Inputs(window);
		startTime = System.currentTimeMillis();
		inputs.setFlying(FLY_MODE);
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	void create() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {throw new Error("GLFW fehler");}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


		window = glfwCreateWindow(1500, 1000, "3D Labyrinth", NULL, NULL);

		if (window == 0) throw new Error("GLFW window fehler");



		glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			glViewport(0, 0, width, height);
			float aspectRatio = (float) width / height;
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			gluPerspective(FOV, aspectRatio, 0.01f, RENDER_DISTANCE);
			glMatrixMode(GL_MODELVIEW);
		});
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		GL.createCapabilities();
		glfwShowWindow(window);
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

	}

	void loop() {

		field = fieldGen.generateField(GRID_SIZE);


		glEnable(GL_DEPTH_TEST);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float aspect = 1200f / 800f;
		gluPerspective(FOV, aspect, 0.01f, RENDER_DISTANCE);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0.2f, 0.2f, 0.5f, 1.0f);//background



		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);



			float posX = inputs.getPosX();
			float posY = inputs.getPosY();
			float posZ = inputs.getPosZ();

			float angleX = inputs.getAngleX();
			float angleY = inputs.getAngleY();
			float oldPosX = inputs.getOldPosX();
			float oldPosZ = inputs.getOldPosZ();


			int gridX = (int) ((posX + (float) (GRID_SIZE*5)/2)/5);//field nachahmen
			int gridZ = (int) ((posZ + (float) (GRID_SIZE*5)/2)/5);

			//Grenzen prüfen und kollision hinzufügen.
			if (!field[gridX][gridZ]) {

				inputs.setPosX(oldPosX);
				inputs.setPosZ(oldPosZ);
				posX = inputs.getPosX();
				posZ = inputs.getPosZ();
			}

			inputs.KeyPresses(window);





			//rotate
			glLoadIdentity();
			glRotatef(angleX, 1.0f, 0.0f, 0.0f);
			glRotatef(angleY, 0.0f, 1.0f, 0.0f);
			glTranslatef(-posX, -posY, -posZ);


			float squareSize = 5.0f;//Grösse der Vierecke.
			float startX = -GRID_SIZE/2  * squareSize;
			float startZ = -GRID_SIZE/2 * squareSize;

			glBegin(GL_QUADS);
			for (int x = 0; x <  GRID_SIZE; x++) {
				for (int z = 0; z < GRID_SIZE; z++) {


					if (field[x][z]) {
						float xPos = startX + x * squareSize;
						float zPos = startZ + z * squareSize;

						if(TEXTURE_MODE) {
							//fieldTexture.setFieldTexture(x,z, false, GRID_SIZE,fieldGen.targetX, fieldGen.targetY);
						} else {
							fieldColor.setFieldColor(x,z, false, GRID_SIZE,fieldGen.targetX, fieldGen.targetY);
						}
						//setFieldColor(x,z, false); old

						glVertex3f(xPos, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos + squareSize);
						glVertex3f(xPos, -1.0f, zPos + squareSize);

						if(TEXTURE_MODE) {
							//fieldTexture.setFieldTexture(x,z, true, GRID_SIZE,fieldGen.targetX, fieldGen.targetY);
						} else {
							fieldColor.setFieldColor(x,z, true, GRID_SIZE,fieldGen.targetX, fieldGen.targetY);
						}
						//setFieldColor(x,z, true);




						renderWalls(x,z,xPos,zPos,squareSize);


					}
				}
			}



			glEnd();


			checkIfPlayerWon(gridX,gridZ);


			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}



	void renderWalls(int x, int z, float xPos, float zPos, float squareSize) {

		//left
		if(x==0 || !field[x-1][z]) {
			glVertex3f(xPos, -1.0f, zPos);
			glVertex3f(xPos, -1.0f + squareSize, zPos);
			glVertex3f(xPos, -1.0f + squareSize, zPos + squareSize);
			glVertex3f(xPos, -1.0f, zPos + squareSize);

		}
		//right
		if(x == GRID_SIZE - 1 || !field[x+1][z]) {
			glVertex3f(xPos + squareSize, -1.0f, zPos);
			glVertex3f(xPos + squareSize, -1.0f + squareSize, zPos);
			glVertex3f(xPos + squareSize, -1.0f + squareSize, zPos + squareSize);
			glVertex3f(xPos + squareSize, -1.0f, zPos + squareSize);
		}

		//front
		if(z == 0 || !field[x][z-1]) {
			glVertex3f(xPos, -1.0f, zPos);
			glVertex3f(xPos + squareSize, -1.0f, zPos);
			glVertex3f(xPos + squareSize, -1.0f + squareSize, zPos);
			glVertex3f(xPos, -1.0f + squareSize, zPos);


		}


		if(z == GRID_SIZE - 1 || !field[x][z+1]) {

			glVertex3f(xPos, -1.0f, zPos + squareSize);
			glVertex3f(xPos + squareSize, -1.0f, zPos + squareSize);
			glVertex3f(xPos + squareSize, -1.0f + squareSize, zPos + squareSize);
			glVertex3f(xPos, -1.0f + squareSize, zPos + squareSize);


		}


	}


//old
	void setFieldColor(int x, int z, boolean wall) {

		if(wall) {
			glColor3f(0.8f, 0.8f, 0.8f);
			return;
		}
		if (x == GRID_SIZE / 2 && z == GRID_SIZE / 2) {
			glColor3f(1.0f, 0.0f, 0.0f);//Hauptfeld
		} else if(x == fieldGen.targetX && z ==fieldGen.targetY) {
			glColor3f(1.0f, 1.0f, 0.0f);//Zielfeld
		} else {
			glColor3f(0.3f, 0.6f, 0.3f);//Boden
		}
	}

	void checkIfPlayerWon(int gridX, int gridZ) {
		if(gridX == fieldGen.targetX && gridZ == fieldGen.targetY) {
			long endTime = System.currentTimeMillis();
			long timeTaken = (endTime - startTime) / 1000;
			System.out.println("Level " + level + " geschafft. \nZeit: " + timeTaken + " Sekunden. \n--------------------");
			gameTime += timeTaken;

			level++;
			if (level > MAX_LEVELS) {
				try {
					throw new GameOverException("Du hast alle Level geschafft und " + gameTime + " Sekunden gebraucht.");
				} catch (GameOverException e) {
					System.out.println(e.getMessage());
					glfwSetWindowShouldClose(window, true);
				}
			}

			field = fieldGen.generateField(GRID_SIZE);
			inputs.setPosX(0.0f);
			inputs.setPosZ(0.0f);
			startTime = System.currentTimeMillis();
		}
	}


	//LWJGL klasse für 3d
	public static void gluPerspective(float fovY, float aspect, float zNear, float zFar) {
		float fH = (float) Math.tan(fovY / 360 * Math.PI) * zNear;
		float fW = fH * aspect;
		glFrustum(-fW, fW, -fH, fH, zNear, zFar);
	}
}
