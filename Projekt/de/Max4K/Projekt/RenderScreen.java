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

		if (!glfwInit()) {throw new Error("Not able to initialize GLFW");}


		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);



		window = glfwCreateWindow(1200, 800, "Labyrinth", NULL, NULL);

		if (window == 0)throw new Error("Failed to create the GLFW window");



		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		GL.createCapabilities();
		glfwShowWindow(window);



	}
	void loop() {


		glClearColor(0.2f, 0.2f, 0.5f, 1.0f);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0f, 800.0f, 600.0f, 0.0f, -1.0f, 1.0f);
		glMatrixMode(GL_MODELVIEW);

		while (!glfwWindowShouldClose(window)) {


			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


			glColor3f(0.5f, 0.5f, 0.5f);


			glBegin(GL_TRIANGLES);



			glVertex2f(50.0f, 300.0f);
			glVertex2f(100.0f, 300.0f);
			glVertex2f(50.0f, 400.0f);



			glEnd();

			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}

}
