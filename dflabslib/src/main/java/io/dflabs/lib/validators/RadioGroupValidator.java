package io.dflabs.lib.validators;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Daniel García Alvarado on 9/22/16.
 * DragonflyLabsLibrary
 */
public class RadioGroupValidator extends FormValidator.Validator {

    private final int errorMessage;
    private final RadioGroup radioGroup;

    public RadioGroupValidator(RadioGroup radioGroup, int errorMessage) {
        this.radioGroup = radioGroup;
        this.errorMessage = errorMessage;
    }

    @Override
    boolean isValid() {
        int count = radioGroup.getChildCount();
        boolean atLeastOneChecked = false;
        for (int i = 0; i < count; i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                atLeastOneChecked = ((RadioButton) o).isChecked() || atLeastOneChecked;
            }
        }
        return atLeastOneChecked;
    }

    @Override
    void showError() {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    void stopError() {

    }
}
