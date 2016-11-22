package com.psychology.experiment;

import java.util.ArrayList;

/**
 * Created by KaseiFox on 11/5/2016.
 */
public class QuestionParameters {
    public double squareALength;
    public int currentSizeIndex;

    public int roundCount;
    public int seriesCount;

    public int oppositeBiggerCount;

    public ArrayList<Character> responses;

    public boolean bIncreasing; // i.e. the current trend

    public QuestionParameters() {
        responses = new ArrayList<Character>();
        roundCount = 0;
        seriesCount = 0;
        bIncreasing = false;
        oppositeBiggerCount = 0;
    }
}
