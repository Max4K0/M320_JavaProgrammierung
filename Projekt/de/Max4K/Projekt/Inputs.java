package de.Max4K.Projekt;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Inputs {

	private float angleX = 0;
	private float angleY = 0;
	private double lastMouseX = -1.0;
	private double lastMouseY = -1.0;
	private float speed = 0.1f;

	private float posX = 2 ;
	private float posY = 1.0f;
	private float posZ = 2 ;

	public Inputs(long window) {

		GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				if(lastMouseX != -1 && lastMouseY != -1) {
					double deltaX = xpos - lastMouseX;
					double deltaY = ypos - lastMouseY;


					angleX += deltaY/5;
					angleY += deltaX/5;
				}
				lastMouseX = xpos;
				lastMouseY = ypos;
			}
		});





	}



	public void KeyPresses(long window) {
		if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
			posX += speed * Math.sin(Math.toRadians(angleY));
			posZ -= speed * Math.cos(Math.toRadians(angleY));
			//System.out.println(Math.toRadians(angleY));
			if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
				speed = 0.2f;
			} else {
				speed = 0.1f;
			}
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

	}

	public float getAngleX() {
		return this.angleX;
	}
	public float getAngleY() {
		return this.angleY;
	}
	public float getPosX() {
		return this.posX;
	}
	public float getPosY() {
		return this.posY;
	}
	public float getPosZ() {
		return this.posZ;
	}

}
