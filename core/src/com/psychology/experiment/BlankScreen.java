package com.psychology.experiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class BlankScreen implements Screen {
    private PsychologicalSquares app;
    private QuestionParameters parameters;

    public BlankScreen(PsychologicalSquares app, QuestionParameters parameters) {
        this.app = app;

        // These parameters are the updated ones, simply pass to QuestionScreen after delay
        this.parameters = parameters;
    }

    @Override
    public void show() {
        // Wait one second before going back to the question screen
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                app.setScreen(new QuestionScreen(app, parameters));
            }
        }, app.getConfiguration().blankDuration);
    }

    @Override
    public void render(float delta) {
        // Clear to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
