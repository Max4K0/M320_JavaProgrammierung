package de.Max4K.Projekt;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.stb.STBImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class FieldTexture {
	private int mainFieldTexture;
	private int targetFieldTexture;
	private int groundTexture;
	private int wallTexture;


	public FieldTexture(String mainFieldPath, String targetFieldPath, String groundPath, String wallPath) {
		this.mainFieldTexture = loadTexture("resources/" + mainFieldPath);
		this.targetFieldTexture = loadTexture("resources/" + targetFieldPath);
		this.groundTexture = loadTexture("resources/" + groundPath);
		this.wallTexture = loadTexture("resources/" + wallPath);
	}

	public void setFieldTexture(int x, int z, boolean wall, int gridSize, int targetX, int targetY) {
		if (wall) {
			glBindTexture(GL_TEXTURE_2D, wallTexture);
			return;
		}

		if (x == gridSize / 2 && z == gridSize / 2) {
			glBindTexture(GL_TEXTURE_2D, mainFieldTexture);
		} else if (x == targetX && z == targetY) {
			glBindTexture(GL_TEXTURE_2D, targetFieldTexture);
		} else {
			glBindTexture(GL_TEXTURE_2D, groundTexture);
		}
	}

	//Laden einer Textur
	private int loadTexture(String path) {
		int textureID = Integer.MIN_VALUE;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);


			ByteBuffer image = stbi_load(path, width, height, channels, 4);
			if (image == null) {
				throw new RuntimeException("Failed to load texture file: " + path + "\n" + stbi_failure_reason());
			}


			textureID = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, textureID);

			//setzen
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			//laden
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
			glGenerateMipmap(GL_TEXTURE_2D);

			//freigeben
			stbi_image_free(image);
		}

		return textureID;
	}
}
