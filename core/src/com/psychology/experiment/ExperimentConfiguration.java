package com.psychology.experiment;

import java.util.ArrayList;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class ExperimentConfiguration {
    public double changeAmount;
    public int expectedRounds;
    public int numberOfSeries;
    public double dpi;
    public float blankDuration;
    public String filename;
    public double gapLength;
    public ArrayList<Double> sizes;

    public ExperimentConfiguration() {
        sizes = new ArrayList<Double>();
    }
}
