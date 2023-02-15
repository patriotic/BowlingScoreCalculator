package com.example.bowlingscorecalculator.model;


import static com.google.common.truth.Truth.assertThat;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrameManagerTest {
    private static FrameManager frameManager;

    @BeforeClass
    public static void initialize() {
        frameManager = new FrameManager();
    }

    @After
    public void setUp() {
        frameManager.restart();
    }

    @Test
    public void totalScore_GivenInput_ReturnsTrue() {
        int[] rolls = {1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6};
        for (int roll : rolls) {
            frameManager.roll(roll);
        }
        assertThat(frameManager.getTotalScore()).isEqualTo(133);
    }

    @Test
    public void totalScore_GivenInput_ReturnsFalse() {
        int[] rolls = {1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6};
        for (int roll : rolls) {
            frameManager.roll(roll);
        }
        assertThat(frameManager.getTotalScore()).isNotEqualTo(135);
    }

    @Test
    public void totalScore_AllZeros_ReturnsTrue() {
        for (int counter = 0; counter < 20; counter++) {
            frameManager.roll(0);
        }
        assertThat(frameManager.getTotalScore()).isEqualTo(0);
    }

    @Test
    public void totalScore_AllStrike_ReturnsTrue() {
        for (int counter = 0; counter < 12; counter++) {
            frameManager.roll(10);
        }
        assertThat(frameManager.getTotalScore()).isEqualTo(300);
    }

    @Test
    public void totalScore_AllSpare_ReturnsTrue() {
        for (int counter = 0; counter < 21; counter++) {
            frameManager.roll(5);
        }
        assertThat(frameManager.getTotalScore()).isEqualTo(150);
    }

    @Test
    public void totalScore_WithoutBonusInLastFrame_ReturnsTrue() {
        int[] rolls = {1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 5};
        for (int roll : rolls) {
            frameManager.roll(roll);
        }
        assertThat(frameManager.getTotalScore()).isEqualTo(121);
    }
}