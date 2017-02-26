package com.game.agar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.agar.Game;

import java.util.ArrayList;
import java.util.List;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = 1280;
		//config.height = 720;
		new LwjglApplication(new Game(), config);

		List<Integer> el = new ArrayList<>();

		el.add(5);
		for (Integer anEl : el) {
			if(anEl > 5)
				System.out.println(anEl);
		}

		el.stream().filter((e) -> e > 5).forEach(System.out::print);
	}
}
