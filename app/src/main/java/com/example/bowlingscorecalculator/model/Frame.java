package com.example.bowlingscorecalculator.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.bowlingscorecalculator.BR;

import java.util.ArrayList;
import java.util.Objects;

public class Frame extends BaseObservable {
    private final String name;
    private final ArrayList<Integer> rolls;
    private int score = -1;

    public Frame(String name) {
        this.name = name;
        rolls = new ArrayList<>();
    }

    public void addRoll(int pins) {
        rolls.add(pins);
        notifyPropertyChanged(BR.rolls);
    }

    @Bindable
    public ArrayList<Integer> getRolls() {
        return rolls;
    }

    public void setScore(int score) {
        this.score = score;
        notifyPropertyChanged(BR.score);
    }

    @Bindable
    public int getScore() {
        return score;
    }

    public int getTotalPins() {
        int score = 0;
        for (int i = 0; i < rolls.size(); i++) {
            score += rolls.get(i);
        }
        return score;
    }

    public boolean isFinished() {
        if (rolls.size() == 1) {
            return isStrike();
        } else return rolls.size() >= 2;
    }

    public boolean isSpare() {
        if (rolls.size() >= 2) return rolls.get(0) + rolls.get(1) == 10 && rolls.get(1) != 0;
        return false;
    }

    public boolean isStrike() {
        if (rolls.isEmpty()) return false;
        return rolls.get(0) == 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return score == frame.score && Objects.equals(name, frame.name) && Objects.equals(rolls, frame.rolls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rolls, score);
    }
}