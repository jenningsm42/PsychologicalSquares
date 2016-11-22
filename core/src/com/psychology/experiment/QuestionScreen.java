package com.psychology.experiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by KaseiFox on 11/5/2016.
 *
 * This class displays two squares and a question:
 *  "Is square A or square B bigger, or are they equal?"
 */
public class QuestionScreen implements Screen {
    private PsychologicalSquares app;
    private QuestionParameters parameters;
    private Stage stage;
    private float questionWidth;

    public QuestionScreen(PsychologicalSquares app, QuestionParameters parameters) {
        this.app = app;
        this.parameters = parameters;
    }

    public void bBigger() {
        if(parameters.responses.size() < app.getConfiguration().sizes.size()) {
            parameters.responses.add('B');
            parameters.roundCount++;
        }

        if(!parameters.bIncreasing) {
            // Update parameters, make square B length smaller by given factor
            parameters.currentSizeIndex--;
            parameters.oppositeBiggerCount = 0;

            // Lower bound the index
            if(parameters.currentSizeIndex < 0)
                parameters.currentSizeIndex = 0;
        } else {
            // Reverse trend - new series if selected twice in a row
            parameters.oppositeBiggerCount++;

            if(parameters.oppositeBiggerCount >= 2) {
                // Save data to CSV
                saveDataSeries();

                // Reset parameters
                parameters.currentSizeIndex = app.getConfiguration().sizes.size() - 1;
                parameters.roundCount = 0;
                parameters.seriesCount++;
                parameters.bIncreasing = false;
                parameters.oppositeBiggerCount = 0;

                parameters.responses.clear();

                // Finished?
                if (parameters.seriesCount >= app.getConfiguration().numberOfSeries) {
                    app.setScreen(new TrialFinishedScreen(app));
                    return;
                }
            } else {
                parameters.currentSizeIndex++;

                // Upper bound the index
                if(parameters.currentSizeIndex >= app.getConfiguration().sizes.size())
                    parameters.currentSizeIndex = app.getConfiguration().sizes.size() - 1;
            }
        }

        // Set blank screen
        app.setScreen(new BlankScreen(app, parameters));
    }

    public void aBigger() {
        if(parameters.responses.size() < app.getConfiguration().sizes.size()) {
            parameters.responses.add('S');
            parameters.roundCount++;
        }

        if(parameters.bIncreasing) {
            // Update parameters, make square B length bigger by given factor
            parameters.currentSizeIndex++;
            parameters.oppositeBiggerCount = 0;

            // Upper bound the index
            if(parameters.currentSizeIndex >= app.getConfiguration().sizes.size())
                parameters.currentSizeIndex = app.getConfiguration().sizes.size() - 1;
        } else {
            // Reverse trend - new series if selected twice
            parameters.oppositeBiggerCount++;

            if(parameters.oppositeBiggerCount >= 2) {
                // Save data to CSV
                saveDataSeries();

                // Reset parameters
                parameters.currentSizeIndex = 0;
                parameters.roundCount = 0;
                parameters.seriesCount++;
                parameters.bIncreasing = true;
                parameters.oppositeBiggerCount = 0;

                parameters.responses.clear();

                // Finished?
                if (parameters.seriesCount >= app.getConfiguration().numberOfSeries) {
                    app.setScreen(new TrialFinishedScreen(app));
                    return;
                }
            } else {
                parameters.currentSizeIndex--;

                // Lower bound the index
                if(parameters.currentSizeIndex < 0)
                    parameters.currentSizeIndex = 0;
            }
        }

        // Set blank screen
        app.setScreen(new BlankScreen(app, parameters));
    }

    public void equalInSize() {
        // Keep current trend
        if(parameters.responses.size() < app.getConfiguration().sizes.size()) {
            parameters.responses.add('E');
            parameters.roundCount++;
        }
        parameters.oppositeBiggerCount = 0;

        if(parameters.bIncreasing) {
            // Update parameters, make square B length bigger by given factor
            parameters.currentSizeIndex++;

            // Upper bound the index
            if(parameters.currentSizeIndex >= app.getConfiguration().sizes.size())
                parameters.currentSizeIndex = app.getConfiguration().sizes.size() - 1;
        } else {
            // Update parameters, make square B length smaller by given factor
            parameters.currentSizeIndex--;

            // Lower bound the index
            if(parameters.currentSizeIndex < 0)
                parameters.currentSizeIndex = 0;
        }

        // Set blank screen
        app.setScreen(new BlankScreen(app, parameters));
    }

    public void saveDataSeries() {
        try {
            // Attempt to open file with append on
            BufferedWriter output = new BufferedWriter(new FileWriter(app.getConfiguration().filename + ".csv", true));

            // Parse sizes into CSV row
            String line = "";
            int blankCount = app.getConfiguration().sizes.size() - parameters.roundCount;
            if(!parameters.bIncreasing) {
                for (int i = 0; i < blankCount; i++) {
                    line += "-,";
                }
                for (int i = parameters.roundCount - 1; i >= 0; i--) {
                    line += parameters.responses.get(i) + ",";
                }
            } else {
                for (int i = 0; i < parameters.roundCount; i++) {
                    line += parameters.responses.get(i) + ",";
                }
                for (int i = 0; i < blankCount; i++) {
                    line += "-,";
                }
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
        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        float screenWidth = Gdx.graphics.getDisplayMode().width;
        float screenHeight = Gdx.graphics.getDisplayMode().height;

        // Set square dimensions and positioning from experiment configuration
        Square squareA = new Square();
        squareA.setWidth((float) parameters.squareALength);
        squareA.setX(screenWidth / 2f - squareA.getWidth() - app.centimetersToPixels(app.getConfiguration().gapLength) / 2f); // Align on left half of screen
        squareA.setY((screenHeight - squareA.getWidth()) / 2f);
        stage.addActor(squareA);

        Square squareB = new Square();
        squareB.setWidth(app.getConfiguration().sizes.get(parameters.currentSizeIndex).floatValue());
        squareB.setX(screenWidth / 2f + app.centimetersToPixels(app.getConfiguration().gapLength) / 2f); // Align on right half of screen
        squareB.setY((screenHeight - squareB.getWidth()) / 2f);
        stage.addActor(squareB);

        // Get question text width to center align
        GlyphLayout layout = new GlyphLayout();
        layout.setText(app.getSmallFont(), "Is square A or square B bigger, or are they equal in size?");
        questionWidth = layout.width;

        // Set up UI elements
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Square A bigger
        TextButton aBiggerButton = new TextButton("A is bigger", skin);
        aBiggerButton.setSize(200f, 50f);
        aBiggerButton.setPosition(Gdx.graphics.getWidth() / 2f - 350f, 100f);
        aBiggerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                aBigger();
            }
        });
        stage.addActor(aBiggerButton);

        // Equal
        TextButton equalButton = new TextButton("Equal", skin);
        equalButton.setSize(200f, 50f);
        equalButton.setPosition(Gdx.graphics.getWidth() / 2f - 100f, 100f);
        equalButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                equalInSize();
            }
        });
        stage.addActor(equalButton);

        // Square B bigger
        TextButton bBiggerButton = new TextButton("B is bigger", skin);
        bBiggerButton.setSize(200f, 50f);
        bBiggerButton.setPosition(Gdx.graphics.getWidth() / 2f + 150f, 100f);
        bBiggerButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                bBigger();
            }
        });
        stage.addActor(bBiggerButton);
    }

    @Override
    public void render(float delta) {
        // Clear to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        stage.getBatch().begin();

        app.getLargeFont().draw(stage.getBatch(), "A", Gdx.graphics.getWidth() / 2 - 255f, Gdx.graphics.getHeight() - 200f);
        app.getLargeFont().draw(stage.getBatch(), "B", Gdx.graphics.getWidth() / 2 + 200f, Gdx.graphics.getHeight() - 200f);

        app.getSmallFont().draw(stage.getBatch(), "Is square A or square B bigger, or are they equal in size?",
                (Gdx.graphics.getWidth() - questionWidth) / 2, 250f);

        stage.getBatch().end();
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
