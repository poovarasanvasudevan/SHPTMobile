package com.poovarasan.androidverify.validator;


import com.poovarasan.androidverify.App;
import com.poovarasan.androidverify.R;

public class MaxValueValidator extends AbstractValidator {

    private int mMaxValue;

    public MaxValueValidator(int value) {
        mMaxValue = value;
        mErrorMessage = App.getContext().getString(R.string.error_max_value, mMaxValue);
    }

    @Override
    public boolean isValid(String value) {
        try {
            double d = Double.parseDouble(value);
            mErrorMessage = App.getContext().getString(R.string.error_max_value, mMaxValue);
            return d <= mMaxValue;
        } catch (NumberFormatException nfe) {
            mErrorMessage = App.getContext().getString(R.string.error_invalid_number);
            return false;
        }
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
