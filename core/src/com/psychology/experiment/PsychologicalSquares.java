package com.psychology.experiment;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PsychologicalSquares extends Game {
	private ExperimentConfiguration configuration;
	private BitmapFont largeFont;
	private BitmapFont smallFont;
	
	@Override
	public void create () {
		// Load global fonts on start up
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SourceSansPro-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 72;
		largeFont = generator.generateFont(parameter);
		largeFont.setColor(Color.BLACK);

		parameter.size = 36;
		smallFont = generator.generateFont(parameter);
		smallFont.setColor(Color.BLACK);

		generator.dispose();

		// Default to setup screen
		setScreen(new SetupScreen(this));
	}

	public void setConfiguration(ExperimentConfiguration configuration) {
		this.configuration = configuration;
	}

	public ExperimentConfiguration getConfiguration() {
		return configuration;
	}

	public float centimetersToPixels(double centimeters) {
		return (float)(configuration.dpi * centimeters / 2.54);
	}

	public double pixelsToCentimeters(double pixels) {
		return pixels * 2.54 / configuration.dpi;
	}

	public BitmapFont getLargeFont() {
		return largeFont;
	}

	public BitmapFont getSmallFont() {
		return smallFont;
	}
}
