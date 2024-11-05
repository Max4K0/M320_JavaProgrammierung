package de.Max4K.Projekt;

import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class FieldTexture {

	public FieldTexture() {
		try {
			loadTexture("resources/floor.png");
		} catch (Exception e) {
			System.err.println("Fehler beim Laden der Texturen: " + e.getMessage());
			e.printStackTrace();
		}
	}


	public int loadTexture(String filePath) throws IOException {
		BufferedImage image = ImageIO.read(new File(filePath));
		int width = image.getWidth();
		int height = image.getHeight();

		ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				buffer.put((byte) ((pixel >> 16) & 0xFF));  //Rot
				buffer.put((byte) ((pixel >> 8) & 0xFF));   //Grün
				buffer.put((byte) (pixel & 0xFF));          //Blau
				buffer.put((byte) ((pixel >> 24) & 0xFF));  //Alpha
			}
		}
		buffer.flip();


		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);


		//Textur setzen
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		//Bilddaten OpenGL geben.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


		glGenerateMipmap(GL_TEXTURE_2D);
		MemoryUtil.memFree(buffer);

		return textureID;
	}

}

