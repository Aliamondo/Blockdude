package com.aliamondo.blockdude;


import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Blockdude";
		cfg.useGL20 = false;
		cfg.width = 1300;
		cfg.height = 783;
		cfg.resizable = false;
		
		//for mac
		cfg.addIcon("blockdude_shadow.png", FileType.Internal);
		//for windows/linux
		cfg.addIcon("ic_launcher.png", FileType.Internal);
		
		new LwjglApplication(new Blockdude(), cfg);
	}
}
