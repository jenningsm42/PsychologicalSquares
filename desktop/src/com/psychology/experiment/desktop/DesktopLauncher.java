package com.psychology.experiment.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.psychology.experiment.PsychologicalSquares;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		config.fullscreen = true;
		config.vSyncEnabled = true;

		new LwjglApplication(new PsychologicalSquares(), config);
	}
}
