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
	private float angleX = 0;
	private float angleY = 0;
	private double lastMouseX = -1.0, lastMouseY = -1.0;


	private float posX = 0.0f;
	private float posY = 0.0f;
	private float posZ = 5.0f;
	float speed = 0.1f;

	public void run() {
		System.out.println(Version.getVersion());

		create();
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
	}

	void loop() {
		glEnable(GL_DEPTH_TEST);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float aspect = 1200f / 800f;
		gluPerspective(45.0f, aspect, 0.1f, 100.0f);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0.2f, 0.2f, 0.5f, 1.0f);//background

		glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
			if (lastMouseX != -1 && lastMouseY != -1) {
				double deltaX = xpos - lastMouseX;
				double deltaY = ypos - lastMouseY;

				angleX += 0 / 5; //Nach oben und unten schauen an/aus
				angleY += deltaX / 5;
			}
			lastMouseX = xpos;
			lastMouseY = ypos;
		});





		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			//tasten


			if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
				posX += speed * Math.sin(Math.toRadians(angleY));
				posZ -= speed * Math.cos(Math.toRadians(angleY));
				//System.out.println(Math.toRadians(angleY));
				System.out.println("Position  x: " + posX + ", y: " + posY + ", z: " + posZ);
			}

			if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
				posX -= speed * Math.sin(Math.toRadians(angleY));
				posZ += speed * Math.cos(Math.toRadians(angleY));
			}

			if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
				posX -= speed * Math.cos(Math.toRadians(angleY));
				posZ -= speed * Math.sin(Math.toRadians(angleY));
			}

			if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
				posX += speed * Math.cos(Math.toRadians(angleY));
				posZ += speed * Math.sin(Math.toRadians(angleY));
			}

			if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
				posY += speed;
			}

			if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
				posY -= speed;
			}

			// bregrenzen
			if(posY > 50) {
				posY = 50;
			}
			if(posY < -50) {
				posY = -50;
			}




			//beta rotate
			glLoadIdentity();
			glRotatef(angleX, 1.0f, 0.0f, 0.0f);
			glRotatef(angleY, 0.0f, 1.0f, 0.0f);
			glTranslatef(-posX, -posY, -posZ);





			glBegin(GL_QUADS);

			//cube
			glColor3f(1.0f, 0.0f, 0.0f);
			glVertex3f(-1.0f, -1.0f, 1.0f);
			glVertex3f(1.0f, -1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 1.0f);
			glVertex3f(-1.0f, 1.0f, 1.0f);

			glColor3f(0.0f, 1.0f, 0.0f);
			glVertex3f(-1.0f, -1.0f, -1.0f);
			glVertex3f(-1.0f, 1.0f, -1.0f);
			glVertex3f(1.0f, 1.0f, -1.0f);
			glVertex3f(1.0f, -1.0f, -1.0f);

			glColor3f(0.0f, 0.0f, 1.0f);
			glVertex3f(-1.0f, -1.0f, -1.0f);
			glVertex3f(-1.0f, -1.0f, 1.0f);
			glVertex3f(-1.0f, 1.0f, 1.0f);
			glVertex3f(-1.0f, 1.0f, -1.0f);

			glColor3f(1.0f, 1.0f, 0.0f);
			glVertex3f(1.0f, -1.0f, -1.0f);
			glVertex3f(1.0f, -1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, -1.0f);

			glColor3f(1.0f, 0.0f, 1.0f);
			glVertex3f(-1.0f, 1.0f, -1.0f);
			glVertex3f(-1.0f, 1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, -1.0f);

			glColor3f(0.0f, 1.0f, 1.0f);
			glVertex3f(-1.0f, -1.0f, -1.0f);
			glVertex3f(-1.0f, -1.0f, 1.0f);
			glVertex3f(1.0f, -1.0f, 1.0f);
			glVertex3f(1.0f, -1.0f, -1.0f);

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
