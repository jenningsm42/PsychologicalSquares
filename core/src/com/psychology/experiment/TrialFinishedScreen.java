package com.psychology.experiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class TrialFinishedScreen implements Screen {
    private PsychologicalSquares app;
    private SpriteBatch batch;
    private float textWidth;
    private float textHeight;

    public TrialFinishedScreen(PsychologicalSquares app) {
        this.app = app;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        GlyphLayout layout = new GlyphLayout();
        layout.setText(app.getLargeFont(), "Thank you for participating!");
        textWidth = layout.width;
        textHeight = layout.height;
    }

    @Override
    public void render(float delta) {
        // Clear to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Display "Thank you"
        app.getLargeFont().draw(batch, "Thank you for participating!", (Gdx.graphics.getWidth() - textWidth) / 2, (Gdx.graphics.getHeight() - textHeight) / 2);

        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            app.setScreen(new TrialScreen(app));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
