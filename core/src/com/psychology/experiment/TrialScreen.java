package com.psychology.experiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.DoubleSummaryStatistics;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class TrialScreen implements Screen {
    private PsychologicalSquares app;
    private Stage stage;

    public TrialScreen(PsychologicalSquares app) {
        this.app = app;
    }

    public void beginTrial() {
        ExperimentConfiguration config = app.getConfiguration();

        // Initialize square sizes based on expected number of rounds and change amount
        QuestionParameters param = new QuestionParameters();
        param.squareALength = Gdx.graphics.getHeight() / 3f;
        param.currentSizeIndex = config.sizes.size() - 1;

        saveDataTitle();

        app.setScreen(new QuestionScreen(app, param));
    }

    public void saveDataTitle() {
        try {
            // Attempt to open file with append on
            BufferedWriter output = new BufferedWriter(new FileWriter(app.getConfiguration().filename + ".csv", true));

            // Parse sizes into CSV row
            String line = "";
            for(double size : app.getConfiguration().sizes) {
                line += Double.toString(app.pixelsToCentimeters(size)) + ",";
            }

            // Write the line with a new line afterwards
            output.write(line.substring(0, line.length() - 1)); // Remove last comma
            output.newLine();

            output.close();
        }
        catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        // Set up stage with input handling
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Load UI skin
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Begin button -- starts the trial
        TextButton beginButton = new TextButton("Begin", skin);
        beginButton.setSize(400f, 100f);
        beginButton.setPosition((Gdx.graphics.getWidth() - beginButton.getWidth()) / 2f, (Gdx.graphics.getHeight() - beginButton.getHeight()) / 2f);
        beginButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                beginTrial();
            }
        });
        stage.addActor(beginButton);
    }

    @Override
    public void render(float delta) {
        // Clear to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render stage
        stage.act(delta);
        stage.draw();
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
