package com.poovarasan.bladeappcompat.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.poovarasan.blade.EventType;
import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.DrawableResourceProcessor;
import com.poovarasan.blade.processor.EventProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladeappcompat.widget.AppRadioButton;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 9:55 AM
 */

public class AppRadioButtonParser extends WrappableParser<AppRadioButton> {
    public AppRadioButtonParser(Parser<AppRadioButton> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppRadioButton(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(Attributes.RadioButton.Button, new DrawableResourceProcessor<AppRadioButton>() {
            @Override
            public void setDrawable(AppRadioButton view, Drawable drawable) {
                view.setButtonDrawable(drawable);
            }
        });

        addHandler(Attributes.RadioButton.Checked, new StringAttributeProcessor<AppRadioButton>() {
            @Override
            public void handle(String attributeKey, String attributeValue, AppRadioButton view) {
                view.setChecked(Boolean.parseBoolean(attributeValue));
            }
        });

        addHandler(Attributes.CheckBox.OnCheck, new EventProcessor<AppRadioButton>() {
            @Override
            public void setOnEventListener(AppRadioButton view, final JsonElement attributeValue) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppRadioButton appCompatCheckBox = (AppRadioButton) v;
                        if (appCompatCheckBox.isChecked()) {
                            fireEvent((BladeView) v, EventType.OnChecked, attributeValue);
                        } else {
                            fireEvent((BladeView) v, EventType.OnUnChecked, attributeValue);
                        }
                    }
                });
            }
        });


    }
}
