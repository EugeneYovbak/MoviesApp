package com.perspikyliator.mytestapp.presentation.screen.home.view;

import android.app.Activity;
import android.os.Bundle;

import com.perspikyliator.mytestapp.R;

import androidx.annotation.Nullable;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}