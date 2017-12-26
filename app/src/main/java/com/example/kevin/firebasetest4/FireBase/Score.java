package com.example.kevin.firebasetest4.FireBase;

/**
 * Created by kevin on 2017/11/30.
 */

public class Score {

    String name;
    String score;

    public Score(){


    }


    public Score(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
