package io.dflabs.sample.views.activities;

import android.os.Bundle;

import io.dflabs.lib.mvp.BaseActivity;
import io.dflabs.lib.mvp.BasePresenter;
import io.dflabs.sample.views.fragments.FruitListFragment;
import io.dflabs.sample.R;

/**
 * Created by Daniel García Alvarado on 10/18/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class ToolbarActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.act_toolbar_content, FruitListFragment.newInstance())
                .commit();
    }

    @Override
    protected BasePresenter[] getPresenters() {
        return null;
    }
}
