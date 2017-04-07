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

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.ParseHelper;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.ColorResourceProcessor;
import com.poovarasan.blade.processor.JsonDataProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.toolbox.Utils;
import com.poovarasan.blade.view.BladeProgressBar;
import com.poovarasan.blade.view.BladeView;

/**
 * @author Poovarasan Vasudevan
 */
public class ProgressBarParser<T extends ProgressBar> extends WrappableParser<T> {

    public ProgressBarParser(Parser<T> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeProgressBar(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();
        addHandler(Attributes.ProgressBar.Max, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setMax((int) ParseHelper.parseDouble(attributeValue));
            }
        });
        addHandler(Attributes.ProgressBar.Progress, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setProgress((int) ParseHelper.parseDouble(attributeValue));
            }
        });

        addHandler(Attributes.ProgressBar.ProgressTint, new JsonDataProcessor<T>() {
            @Override
            public void handle(String key, JsonElement valueElement, T view) {
                if (!valueElement.isJsonObject() || valueElement.isJsonNull()) {
                    return;
                }
                JsonObject data = valueElement.getAsJsonObject();
                int background = Color.TRANSPARENT;
                int progress = Color.TRANSPARENT;

                String value = Utils.getPropertyAsString(data, "background");
                if (value != null) {
                    background = ParseHelper.parseColor(value);
                }
                value = Utils.getPropertyAsString(data, "progress");
                if (value != null) {
                    progress = ParseHelper.parseColor(value);
                }

                view.setProgressDrawable(getLayerDrawable(progress, background));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addHandler(Attributes.ProgressBar.SecondaryProgressTint, new ColorResourceProcessor<T>() {
                @Override
                public void setColor(T view, int color) {

                }

                @Override
                public void setColor(T view, ColorStateList colors) {
                    //noinspection AndroidLintNewApi
                    view.setSecondaryProgressTintList(colors);
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addHandler(Attributes.ProgressBar.IndeterminateTint, new ColorResourceProcessor<T>() {
                @Override
                public void setColor(T view, int color) {

                }

                @Override
                public void setColor(T view, ColorStateList colors) {
                    //noinspection AndroidLintNewApi
                    view.setIndeterminateTintList(colors);
                }
            });
        }
    }

    private Drawable getLayerDrawable(int progress, int background) {
        ShapeDrawable shape = new ShapeDrawable();
        shape.getPaint().setStyle(Paint.Style.FILL);
        shape.getPaint().setColor(background);

        ShapeDrawable shapeD = new ShapeDrawable();
        shapeD.getPaint().setStyle(Paint.Style.FILL);
        shapeD.getPaint().setColor(progress);
        ClipDrawable clipDrawable = new ClipDrawable(shapeD, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        return new LayerDrawable(new Drawable[]{shape, clipDrawable});
    }
}
