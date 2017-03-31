package com.poovarasan.androidverify.validator;

import com.poovarasan.androidverify.App;
import com.poovarasan.androidverify.R;


public class EmailValidator extends AbstractValidator {

    public EmailValidator() {
        mErrorMessage = App.getContext().getString(R.string.error_invalid_email);
    }

    @Override
    public boolean isValid(String value) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
