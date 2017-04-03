package com.poovarasan.blade.parser.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.poovarasan.blade.EventType;
import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.ParseHelper;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.DrawableResourceProcessor;
import com.poovarasan.blade.processor.EventProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeRadioButton;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by poovarasanv on 3/4/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 3/4/17 at 2:19 PM
 */

public class RadioButtonParser extends WrappableParser<BladeRadioButton> {
    public RadioButtonParser(Parser<BladeRadioButton> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeRadioButton(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();


        addHandler(Attributes.RadioButton.Checked, new StringAttributeProcessor<BladeRadioButton>() {
            @Override
            public void handle(String attributeKey, String attributeValue, BladeRadioButton view) {
                Boolean isChecked = ParseHelper.parseBoolean(attributeValue);
                if (isChecked) {
                    view.setChecked(true);
                }
            }
        });


        addHandler(Attributes.RadioButton.Button, new DrawableResourceProcessor<BladeRadioButton>() {
            @Override
            public void setDrawable(BladeRadioButton view, Drawable drawable) {
                view.setButtonDrawable(drawable);
            }
        });


        addHandler(Attributes.RadioButton.OnCheck, new EventProcessor<BladeRadioButton>() {
            @Override
            public void setOnEventListener(BladeRadioButton view, JsonElement attributeValue) {
                fireEvent(view, EventType.OnChecked, attributeValue);
            }
        });
    }
}
