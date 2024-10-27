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


	private static final int GRID_SIZE = 30;
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
						} else {
							glColor3f(0.3f, 0.6f, 0.3f);
						}


						glVertex3f(xPos, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos);
						glVertex3f(xPos + squareSize, -1.0f, zPos + squareSize);
						glVertex3f(xPos, -1.0f, zPos + squareSize);




					}
				}
			}









			glEnd();





			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}


	//LWJGL klasse für 3d
	public static void gluPerspective(float fovY, float aspect, float zNear, float zFar) {
		float fH = (float) Math.tan(fovY / 360 * Math.PI) * zNear;
		float fW = fH * aspect;
		glFrustum(-fW, fW, -fH, fH, zNear, zFar);
	}
}
