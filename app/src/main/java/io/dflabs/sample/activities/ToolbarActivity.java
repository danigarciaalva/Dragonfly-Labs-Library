package io.dflabs.sample.activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import io.dflabs.lib.mvp.BaseActivity;
import io.dflabs.sample.R;

/**
 * Created by Daniel García Alvarado on 10/18/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class ToolbarActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
    }
}
