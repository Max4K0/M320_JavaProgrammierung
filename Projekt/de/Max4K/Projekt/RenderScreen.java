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
	private boolean[][] field;
	private FieldGenerator fieldGen;
	private Inputs inputs;




	public RenderScreen() {
		fieldGen = new FieldGenerator();
	}

	public void run() {
		System.out.println(Version.getVersion());

		create();
		inputs = new Inputs(window);
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
			gluPerspective(45.0f, aspectRatio, 0.1f, 100.0f);
			glMatrixMode(GL_MODELVIEW);
		});
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		GL.createCapabilities();
		glfwShowWindow(window);
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

	}

	void loop() {

		field = fieldGen.generateField();


		glEnable(GL_DEPTH_TEST);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float aspect = 1200f / 800f;
		gluPerspective(45.0f, aspect, 0.1f, 100.0f);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0.2f, 0.2f, 0.5f, 1.0f);//background



		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


			float posX = inputs.getPosX();
			float posY = inputs.getPosY();
			float posZ = inputs.getPosZ();
			float angleX = inputs.getAngleX();
			float angleY = inputs.getAngleY();
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



						if (x == GRID_SIZE / 2 && z == GRID_SIZE / 2) {
							glColor3f(1.0f, 0.0f, 0.0f); // Hauptfeld
						} else if(x == fieldGen.targetX && z ==fieldGen.targetY) {
							glColor3f(1.0f, 1.0f, 0.0f);
						} else {
							glColor3f(0.3f, 0.6f, 0.3f);
						}


						glVertex3f(xPos, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos + squareSize);
						glVertex3f(xPos, -1.0f, zPos + squareSize);

						renderWalls(x,z,xPos,zPos,squareSize);


					}
				}
			}



			glEnd();





			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}



	void renderWalls(int x, int z, float xPos, float zPos, float squareSize) {


		glColor3f(0.8f, 0.8f, 0.8f);

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










	//LWJGL klasse für 3d
	public static void gluPerspective(float fovY, float aspect, float zNear, float zFar) {
		float fH = (float) Math.tan(fovY / 360 * Math.PI) * zNear;
		float fW = fH * aspect;
		glFrustum(-fW, fW, -fH, fH, zNear, zFar);
	}
}
