package unsw.dungeon.back;

import javafx.scene.image.Image;

public class Texture {
	private String imageSrc;
	private char c;
	public Texture(char c, String imageSrc) {
		this.c = c;
		this.imageSrc = imageSrc;
	}
	public char getChar() {
		return this.c;
	}
	public String getImageSrc() {
		return this.imageSrc;
	}
}
