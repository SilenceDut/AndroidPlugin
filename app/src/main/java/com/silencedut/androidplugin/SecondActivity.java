package com.silencedut.androidplugin;

import android.os.Bundle;

import com.ryg.dynamicload.DLBasePluginFragmentActivity;

/**
 * Created by SilenceDut on 16/8/8.
 */

public class SecondActivity extends DLBasePluginFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

    }
}
