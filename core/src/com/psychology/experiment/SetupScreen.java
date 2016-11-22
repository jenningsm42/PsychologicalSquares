package com.psychology.experiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class SetupScreen implements Screen {
    private PsychologicalSquares app;
    private Stage stage;

    private TextField changeAmountField;
    private TextField expectedRoundsField;
    private TextField seriesField;
    private TextField dpiField;
    private TextField blankDurationField;
    private TextField filenameField;
    private TextField gapLengthField;

    public SetupScreen(PsychologicalSquares app) {
        this.app = app;
    }

    @Override
    public void show() {
        // Set up stage with input handling
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Load UI skin
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Setup button -- finalizes the configuration
        TextButton setupButton = new TextButton("Setup", skin);
        setupButton.setSize(300f, 50f);
        setupButton.setPosition((Gdx.graphics.getWidth() - setupButton.getWidth()) / 2f, Gdx.graphics.getHeight() - 600f);
        setupButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                ExperimentConfiguration config = new ExperimentConfiguration();
                config.changeAmount = Double.valueOf(changeAmountField.getText());
                config.expectedRounds = Integer.valueOf(expectedRoundsField.getText());
                config.numberOfSeries = Integer.valueOf(seriesField.getText());
                config.dpi = Double.valueOf(dpiField.getText());
                config.blankDuration = Float.valueOf(blankDurationField.getText());
                config.filename = filenameField.getText();
                config.gapLength = Double.valueOf(gapLengthField.getText());
                app.setConfiguration(config); // To be able to use pixel-cm conversion for next portion

                double lengthA = Gdx.graphics.getHeight() / 3f;
                for(int i = -config.expectedRounds; i <= config.expectedRounds; i++) {
                    config.sizes.add(lengthA + app.centimetersToPixels(config.changeAmount) * i);
                }

                app.setConfiguration(config);
                app.setScreen(new TrialScreen(app));
            }
        });
        stage.addActor(setupButton);

        // Change amount
        Label changeAmountLabel = new Label("Change amount (cm):", skin);
        changeAmountLabel.setPosition(Gdx.graphics.getWidth() / 2f - changeAmountLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 200f);
        stage.addActor(changeAmountLabel);

        changeAmountField = new TextField("0.1", skin);
        changeAmountField.setSize(100f, 30f);
        changeAmountField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 200f);
        stage.addActor(changeAmountField);

        // Expected number of rounds
        Label expectedRoundsLabel = new Label("Expected number of rounds:", skin);
        expectedRoundsLabel.setPosition(Gdx.graphics.getWidth() / 2f - expectedRoundsLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 250f);
        stage.addActor(expectedRoundsLabel);

        expectedRoundsField = new TextField("7", skin);
        expectedRoundsField.setSize(100f, 30f);
        expectedRoundsField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 250f);
        stage.addActor(expectedRoundsField);

        // Number of series
        Label seriesLabel = new Label("Number of series:", skin);
        seriesLabel.setPosition(Gdx.graphics.getWidth() / 2f - seriesLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 300f);
        stage.addActor(seriesLabel);

        seriesField = new TextField("7", skin);
        seriesField.setSize(100f, 30f);
        seriesField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 300f);
        stage.addActor(seriesField);

        // DPI
        Label dpiLabel = new Label("DPI/PPI:", skin);
        dpiLabel.setPosition(Gdx.graphics.getWidth() / 2f - dpiLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 350f);
        stage.addActor(dpiLabel);

        dpiField = new TextField("226", skin);
        dpiField.setSize(100f, 30f);
        dpiField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 350f);
        stage.addActor(dpiField);

        // Blank duration
        Label blankDurationlabel = new Label("Blank duration (seconds):", skin);
        blankDurationlabel.setPosition(Gdx.graphics.getWidth() / 2f - blankDurationlabel.getWidth() - 10f, Gdx.graphics.getHeight() - 400f);
        stage.addActor(blankDurationlabel);

        blankDurationField = new TextField("1.0", skin);
        blankDurationField.setSize(100f, 30f);
        blankDurationField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 400f);
        stage.addActor(blankDurationField);

        // Filename
        Label filenameLabel = new Label("Filename:", skin);
        filenameLabel.setPosition(Gdx.graphics.getWidth() / 2f - filenameLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 450f);
        stage.addActor(filenameLabel);

        filenameField = new TextField("results", skin);
        filenameField.setSize(200f, 30f);
        filenameField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 450f);
        stage.addActor(filenameField);

        // Gap length
        Label gapLengthLabel = new Label("Gap length (cm):", skin);
        gapLengthLabel.setPosition(Gdx.graphics.getWidth() / 2f - gapLengthLabel.getWidth() - 10f, Gdx.graphics.getHeight() - 500f);
        stage.addActor(gapLengthLabel);

        gapLengthField = new TextField("0.5", skin);
        gapLengthField.setSize(100f, 30f);
        gapLengthField.setPosition(Gdx.graphics.getWidth() / 2f + 10f, Gdx.graphics.getHeight() - 500f);
        stage.addActor(gapLengthField);
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
