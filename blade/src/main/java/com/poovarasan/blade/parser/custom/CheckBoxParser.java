/*
 * Copyright 2016 Poovarasan Vasudevan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poovarasan.blade.parser.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.poovarasan.blade.EventType;
import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.DrawableResourceProcessor;
import com.poovarasan.blade.processor.EventProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeCheckBox;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by prateek.dixit on 1/8/15.
 */
public class CheckBoxParser<T extends CheckBox> extends WrappableParser<T> {

    public CheckBoxParser(Parser<T> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeCheckBox(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(Attributes.CheckBox.Button, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                view.setButtonDrawable(drawable);
            }
        });

        addHandler(Attributes.CheckBox.Checked, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, CheckBox view) {
                view.setChecked(Boolean.parseBoolean(attributeValue));
            }
        });
        addHandler(Attributes.CheckBox.OnCheck, new EventProcessor<T>() {
            @Override
            public void setOnEventListener(T view, JsonElement attributeValue) {
                fireEvent((BladeView) view, EventType.OnChecked, attributeValue);
            }
        });

    }
}
