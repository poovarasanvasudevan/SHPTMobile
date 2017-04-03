package com.poovarasan.blade.parser.custom;

import com.google.gson.JsonObject;

import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeRadioGroup;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by poovarasanv on 3/4/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 3/4/17 at 2:16 PM
 */

public class RadioGroupParser extends WrappableParser<RadioGroup> {
    public RadioGroupParser(Parser<RadioGroup> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeRadioGroup(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();
    }
}
