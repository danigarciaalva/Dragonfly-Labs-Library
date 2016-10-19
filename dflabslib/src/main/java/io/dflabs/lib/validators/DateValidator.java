package io.dflabs.lib.validators;

import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Daniel García Alvarado on 9/13/15.
 * Gastalon - danielgarcia
 */
@SuppressWarnings("unused")
public class DateValidator extends FormValidator.Validator {

    public static final int BEFORE_DATE_SYSTEM = 1;
    public static final int BEFORE_DATE = 2;
    public static final int AFTER_DATE = 3;
    public static final int EQUAL_DATE = 4;
    public static final int NOT_EQUAL_DATE = 5;

    private final String dateString;
    private final String dateCompare;
    private final int errorMessage;
    private final int typeValidate;

    public DateValidator(String dateString, int typeValidate, int errorMessage) {
        this.dateString = dateString;
        this.errorMessage = errorMessage;
        this.typeValidate = typeValidate;
        this.dateCompare = "";
    }

    public DateValidator(String dateString, String dateCompare, int typeValidate, int errorMessage) {
        this.dateString = dateString;
        this.errorMessage = errorMessage;
        this.typeValidate = typeValidate;
        this.dateCompare = dateCompare;
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date dateSecond;
            Date dateFirst;
            switch (typeValidate) {
                case BEFORE_DATE_SYSTEM:
                    dateSecond = formatDate.parse(formatDate.format(new Date()));
                    dateFirst = formatDate.parse(dateString);
                    if (dateFirst.equals(dateSecond) || dateFirst.after(dateSecond)) {
                        valid = false;
                    }
                    break;
                case BEFORE_DATE:
                    dateSecond = formatDate.parse(dateCompare);
                    dateFirst = formatDate.parse(dateString);
                    valid = dateFirst.before(dateSecond);
                    break;
                case AFTER_DATE:
                    dateSecond = formatDate.parse(dateCompare);
                    dateFirst = formatDate.parse(dateString);
                    valid = dateFirst.after(dateSecond);
                    break;
                case EQUAL_DATE:
                    dateSecond = formatDate.parse(dateCompare);
                    dateFirst = formatDate.parse(dateString);
                    valid = dateFirst.equals(dateSecond);
                    break;
                case NOT_EQUAL_DATE:
                    dateSecond = formatDate.parse(dateCompare);
                    dateFirst = formatDate.parse(dateString);
                    valid = !dateFirst.equals(dateSecond);
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    @Override
    void showError() {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    void stopError() {

    }
}
