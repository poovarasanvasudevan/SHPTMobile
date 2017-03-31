package com.poovarasan.androidverify.validator;


import com.poovarasan.androidverify.App;
import com.poovarasan.androidverify.R;

import java.util.regex.Pattern;

public class RegexValidator extends AbstractValidator {

    private Pattern mRegexPattern;

    public RegexValidator(String regex) {
        mRegexPattern = Pattern.compile(regex);
        mErrorMessage = App.getContext().getString(R.string.error_invalid_field);
    }

    @Override
    public boolean isValid(String value) {
        return mRegexPattern.matcher(value).matches();
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
