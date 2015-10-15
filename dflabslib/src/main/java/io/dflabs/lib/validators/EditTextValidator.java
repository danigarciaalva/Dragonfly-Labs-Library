package io.dflabs.lib.validators;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel García Alvarado on 9/13/15.
 * Gastalon - danielgarcia
 */
@SuppressWarnings("unused")
public class EditTextValidator extends FormValidator.Validator {

    private final EditText editText;
    private final int errorMessage;
    private final Pattern pattern;

    public EditTextValidator(EditText editText, String regex, int errorMessage) {
        this.editText = editText;
        this.errorMessage = errorMessage;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid() {
        Matcher matcher = pattern.matcher(editText.getText().toString().trim());
        return matcher.matches();
    }

    @Override
    public void showError() {
        error(errorMessage);
    }

    private void error(Integer message) {
        try {
            TextInputLayout textInputLayout = (TextInputLayout) editText.getParent();
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message != null ? context.getString(message) : null);
        }catch (ClassCastException ignored){
            editText.setError(message != null ? context.getString(message) : null);
        }
    }

    @Override
    void stopError() {
        error(null);
    }
}
