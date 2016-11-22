package com.psychology.experiment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class Square extends Actor {
    private ShapeRenderer shapeRenderer;

    public Square() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // ShapeRenderer uses its own batching system
        batch.end();

        // Ensure coordinate frame stays the same
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Fill entire area
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(getX(), getY(), getWidth(), getWidth()); // Only use width to ensure shape is a square
        shapeRenderer.end();

        // Re-enable batched drawing
        batch.begin();
    }
}
