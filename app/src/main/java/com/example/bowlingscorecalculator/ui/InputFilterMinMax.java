package com.example.bowlingscorecalculator.ui;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {
    private final int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned destination, int destinationStart, int destinationEnd) {
        try {
            String newValue = destination.toString().substring(0, destinationStart) + destination.toString().substring(destinationEnd);
            newValue = newValue.substring(0, destinationStart) + source.toString() + newValue.substring(destinationStart);
            int input = Integer.parseInt(newValue);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException ignored) {
        }
        return "";
    }

    private boolean isInRange(int first, int second, int input) {
        return second > first ? input >= first && input <= second : input >= second && input <= first;
    }
}
