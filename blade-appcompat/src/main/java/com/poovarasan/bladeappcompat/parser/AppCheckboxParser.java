package com.poovarasan.bladeappcompat.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckBox;
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
import com.poovarasan.bladeappcompat.widget.AppCheckbox;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 8:59 AM
 */

public class AppCheckboxParser extends WrappableParser<AppCompatCheckBox> {
    public AppCheckboxParser(Parser<AppCompatCheckBox> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppCheckbox(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(Attributes.CheckBox.Button, new DrawableResourceProcessor<AppCompatCheckBox>() {
            @Override
            public void setDrawable(AppCompatCheckBox view, Drawable drawable) {
                view.setButtonDrawable(drawable);
            }
        });

        addHandler(Attributes.CheckBox.Checked, new StringAttributeProcessor<AppCompatCheckBox>() {
            @Override
            public void handle(String attributeKey, String attributeValue, AppCompatCheckBox view) {
                view.setChecked(Boolean.parseBoolean(attributeValue));
            }
        });

        addHandler(Attributes.CheckBox.OnCheck, new EventProcessor<AppCompatCheckBox>() {
            @Override
            public void setOnEventListener(AppCompatCheckBox view, final JsonElement attributeValue) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) v;
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
