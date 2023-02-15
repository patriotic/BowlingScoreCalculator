package com.example.bowlingscorecalculator.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bowlingscorecalculator.model.Frame;
import com.example.bowlingscorecalculator.model.FrameManager;

import java.util.ArrayList;

public class ActivityViewModel extends ViewModel {
    private final FrameManager frameManager;

    public ActivityViewModel() {
        frameManager = new FrameManager();
    }

    public void roll(int pins) {
        frameManager.roll(pins);
    }

    public LiveData<ArrayList<Frame>> getFrames() {
        return frameManager.getFrames();
    }

    public void restart() {
        frameManager.restart();
    }
}
