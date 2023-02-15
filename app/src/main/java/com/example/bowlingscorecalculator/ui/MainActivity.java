package com.example.bowlingscorecalculator.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.bowlingscorecalculator.R;
import com.example.bowlingscorecalculator.databinding.ActivityMainBinding;
import com.example.bowlingscorecalculator.model.Frame;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    ActivityViewModel mViewModel;
    FrameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);

        mBinding.quantityEdittext.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10)});

        adapter = new FrameAdapter();

        mBinding.frameRecyclerview.setAdapter(adapter);

        mViewModel.getFrames().observe(this, new Observer<ArrayList<Frame>>() {
            @Override
            public void onChanged(ArrayList<Frame> frames) {
                adapter.submitList(frames);
            }
        });
    }

    public void roll(View view) {
        String text = Objects.requireNonNull(mBinding.quantityEdittext.getText()).toString().trim();
        if (!text.isEmpty()) {
            mViewModel.roll(Integer.parseInt(text));
        }

        mBinding.quantityEdittext.getText().clear();
        mBinding.quantityEdittext.clearFocus();
        InputMethodManager imm = ContextCompat.getSystemService(this, InputMethodManager.class);
        if (imm != null)
            imm.hideSoftInputFromWindow(mBinding.quantityEdittext.getWindowToken(), 0);

    }

    public void restart(View view) {
        mViewModel.restart();
        adapter.notifyDataSetChanged();
    }
}