package io.dflabs.sample.views.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.dflabs.lib.mvp.BaseActivity;
import io.dflabs.lib.utils.CameraUtils;
import io.dflabs.lib.validators.CheckBoxGroupValidator;
import io.dflabs.lib.validators.EditTextValidator;
import io.dflabs.lib.validators.FormValidator;
import io.dflabs.lib.validators.Regex;
import io.dflabs.sample.R;
import io.dflabs.sample.views.adapters.PhotosAdapter;

/**
 * Created by jorge.hernandez on 22/09/2016.
 * test
 */
public class Forms extends BaseActivity {

    @BindView(R.id.partial_injury_injured)
    CheckBox mInjuredCheckBox;
    @BindView(R.id.partial_injury_level)
    EditText partial_injury_level;
    @BindView(R.id.partial_injury_ambulance)
    CheckBox mAmbulanceCheckBox;
    @BindView(R.id.partial_injury_ambulance_container)
    LinearLayout mAmbulanceContainer;
    @BindView(R.id.partial_injury_other_place)
    CheckBox mOtherPlaceCheckBox;
    @BindView(R.id.partial_injury_other_place_text_container)
    TextInputLayout mOtherPlaceEditTextContainer;
    @BindView(R.id.partial_injury_other_place_text)
    EditText mOtherPlaceEditText;
    @BindView(R.id.partial_injury_container)
    LinearLayout mInjuredLayout;

    @BindView(R.id.partial_injury_ambulance_state)
    EditText lalalala;
    @BindView(R.id.partial_injury_ambulance_place)
    EditText lolololo;



    FormValidator mFormValidator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarEnabled(false);
        setContentView(R.layout.form_layout);

        ButterKnife.bind(this);

        mFormValidator = new FormValidator(this, true);
        mFormValidator.addValidators(
                new CheckBoxGroupValidator(mInjuredCheckBox,
                        new EditTextValidator[]{
                                new EditTextValidator(partial_injury_level, Regex.NOT_EMPTY, R.string.action_settings)
                        }, true),
                new CheckBoxGroupValidator(mAmbulanceCheckBox,
                        new EditTextValidator[]{
                                new EditTextValidator(lalalala, Regex.NOT_EMPTY, R.string.action_settings),
                                new EditTextValidator(lolololo, Regex.NOT_EMPTY, R.string.action_settings)
                        }, true)
        );


    }

    @OnCheckedChanged(R.id.partial_injury_injured)
    void onInjuredChecked(boolean checked) {
        if (checked) {
            mInjuredLayout.setVisibility(View.VISIBLE);
        } else {
            mInjuredLayout.setVisibility(View.GONE);
        }
    }


    @OnCheckedChanged(R.id.partial_injury_other_place)
    void onOtherPlaceClick(boolean checked){
        mOtherPlaceEditTextContainer.setVisibility( checked ? View.VISIBLE : View.GONE );
    }

    @OnCheckedChanged(R.id.partial_injury_ambulance)
    void onAmbulanceClick(boolean checked){
        mAmbulanceContainer.setVisibility( checked ? View.VISIBLE : View.GONE );
    }

    @OnClick(R.id.lalalala)
    void validate(){
        if (mFormValidator.isValid()) Toast.makeText(this, "VALIDADO", Toast.LENGTH_SHORT).show();
    }
}
