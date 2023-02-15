package com.example.bowlingscorecalculator.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class FrameManager {
    // It is used to store the frames.
    private final ArrayList<Frame> frames = new ArrayList<>();
    // framesLiveData keeps reference of the frames and exposes to the observer.
    private final MutableLiveData<ArrayList<Frame>> framesLiveData = new MutableLiveData<>(frames);
    private int currentFrameIndex;

    //  Add pins in a roll to a frame.
    public void roll(int pins) {
        //  Return if all of the 10 frames are completed.
        if (isComplete()) return;
        //  Check if the pin is valid.
        if (isValidPin(pins)) {
            //  Add rolls to the frame.
            addRoll(pins);
            // Update scores if all the frames are completed.
            if (isComplete()) {
                updateScores();
            }
        }
    }

    /*  Check whether all 10 frames are completed.
     *  The last frame can be finished in any of the two ways:
     *  1. Two rolls with no strike and no spare.
     *  2. Three rolls with strike or spare.
     */
    private boolean isComplete() {
        Frame lastFrame;
        if (frames.size() == 10) {
            lastFrame = frames.get(9);
            if (lastFrame.isFinished()) {
                if (lastFrame.isStrike() || lastFrame.isSpare()) {
                    return lastFrame.getRolls().size() == 3;
                } else return true;
            }
        }
        return false;
    }

    //  Check whether the pin is in range 0 and 10 (inclusive).
    private boolean isValidPin(int pins) {
        return pins >= 0 && pins <= 10;
    }

    //  Get a frame and add pins in a roll.
    private void addRoll(int pins) {
        Frame frame = getCurrentFrame();
        frame.addRoll(pins);
    }

    //  Get the last frame score as a total score.
    public int getTotalScore() {
        return (!frames.isEmpty()) ? frames.get(frames.size() - 1).getScore() : 0;
    }

    // Scores are updated after all the frames are completed.
    public void updateScores() {
        int score = 0;
        for (int counter = 0; counter < frames.size(); counter++) {
            Frame frame = frames.get(counter);
            score += frame.getTotalPins();

            /*  This condition is used to calculate scores upto the 9th frame.
             *  This is used because there might a strike or spare in the 10th frame which leads to
             *  update the bonus from the next unavailable frame.
             */
            if (counter < frames.size() - 1) {
                // If the frame is spare, update the frame score with spare bonus (add next one roll).
                if (frame.isSpare()) {
                    score += frames.get(counter + 1).getRolls().get(0);
                }
                // If the frame is strike, update the frame score with strike bonus (add next two rolls).
                if (frame.isStrike()) {
                    // Add next first roll as bonus.
                    score += frames.get(counter + 1).getRolls().get(0);
                    // Add next second roll as bonus.
                    // However, if the next roll is strike as well, first roll after the the next frame will be added.
                    if (frames.get(counter + 1).isStrike()) {
                        if (counter < frames.size() - 2) {
                            score += frames.get(counter + 2).getRolls().get(0);
                        }
                        // This condition is used to add second bonus if there is a strike in the previous frame of the last frame.
                        else if (counter == frames.size() - 2) {
                            score += frames.get(counter + 1).getRolls().get(1);
                        }
                    } else {
                        score += frames.get(counter + 1).getRolls().get(1);
                    }
                }
            }
            // update the frame score
            frame.setScore(score);
        }
    }

    /*
     * Create a new frame if the frame is first frame.
     * Always get the 10th frame if the currentFrameIndex is greater then 9.
     * Create a new frame if the current frame is finished and it is not the last frame.
     * */
    private Frame getCurrentFrame() {
        Frame frame;
        if (currentFrameIndex > 9)
            return frames.get(9);

        if (frames.size() == 0) {
            return createFrame();
        }
        frame = frames.get(currentFrameIndex);
        if (frame.isFinished()) {
            if (currentFrameIndex != 9) {
                ++currentFrameIndex;
                return createFrame();
            }
        }
        return frame;
    }

    private Frame createFrame() {
        Frame frame = new Frame("Frame : ".concat(String.valueOf(currentFrameIndex + 1)));
        frames.add(frame);
        return frame;
    }

    public LiveData<ArrayList<Frame>> getFrames() {
        return framesLiveData;
    }

    //  It is used to start the game again
    public void restart() {
        frames.clear();
        currentFrameIndex = 0;
    }
}
