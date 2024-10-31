package de.Max4K.Projekt;

import org.lwjgl.glfw.GLFWCursorPosCallback;


import static org.lwjgl.glfw.GLFW.*;

public class Inputs {

	private float angleX = 0;
	private float angleY = 0;
	private float speed = 0.2f;
	private boolean mouselock = true;


	private float posX = 2 ;
	private float posY = 1.0f;
	private float posZ = 2 ;
	private float oldPosX = posX;
	private float oldPosZ = posZ;
	public Inputs(long window) {


		glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() { //TODO: passender methodenname
			@Override
			public void invoke(long window, double xpos, double ypos) {



				double deltaX = xpos - 750;
				double deltaY = ypos - 500;

				angleX += deltaY / 10;
				angleY += deltaX / 10;



				glfwSetCursorPos(window, 750, 500); //750, 500 je nach bildschirmgrösse



			}
		});





	}



	public void KeyPresses(long window) {


		oldPosX = posX;
		oldPosZ = posZ;

		if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
			posX += speed * Math.sin(Math.toRadians(angleY));
			posZ -= speed * Math.cos(Math.toRadians(angleY));


			//System.out.println(Math.toRadians(angleY));
			if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
				speed = 0.5f;
			} else {
				speed = 0.2f;
			}
			//System.out.println("Position  x: " + posX + ", y: " + posY + ", z: " + posZ);
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
		if (glfwGetKey(window, GLFW_KEY_F11) == GLFW_TRUE) {

			if(glfwGetWindowAttrib(window, GLFW_MAXIMIZED) == GLFW_PRESS) {

				glfwRestoreWindow(window);
			} else {
				glfwMaximizeWindow(window);
			}
		}

		if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {

			glfwSetWindowShouldClose(window, true);
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

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosZ(float posZ) {
		this.posZ = posZ;
	}

	public float getOldPosX() {
		return oldPosX;
	}

	public float getOldPosZ() {
		return oldPosZ;
	}
}
