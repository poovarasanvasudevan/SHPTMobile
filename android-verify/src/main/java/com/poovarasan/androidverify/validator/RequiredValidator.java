package com.poovarasan.androidverify.validator;


import android.text.TextUtils;

import com.poovarasan.androidverify.App;
import com.poovarasan.androidverify.R;

public class RequiredValidator extends AbstractValidator {

    private boolean mRequired;

    public RequiredValidator(boolean required) {
        mRequired = required;
        mErrorMessage = App.getContext().getString(R.string.error_field_required);
    }

    @Override
    public boolean isValid(String value) {
        if (mRequired)
            return !TextUtils.isEmpty(value);
        return true;
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String message) {
        mRequired = true;
        mErrorMessage = message;
    }
}
