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

		window = glfwCreateWindow(1200, 800, "3D Labyrinth", NULL, NULL);

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

				angleX += deltaY / 5;
				angleY += deltaX / 5;
			}
			lastMouseX = xpos;
			lastMouseY = ypos;
		});

		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


			//reset
			glLoadIdentity();
			glTranslatef(0.0f, 0.0f, -5.0f);//move
			glRotatef(angleX, 1.0f, 0.0f, 0.0f);//rotate
			glRotatef(angleY, 0.0f, 1.0f, 0.0f);


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
