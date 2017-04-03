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
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.ParseHelper;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.ColorResourceProcessor;
import com.poovarasan.blade.processor.DimensionAttributeProcessor;
import com.poovarasan.blade.processor.DrawableResourceProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeTextView;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by Poovarasan Vasudevan on 12/05/14.
 */
public class TextViewParser<T extends TextView> extends WrappableParser<T> {

    public TextViewParser(Parser<T> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeTextView(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();
        addHandler(Attributes.TextView.HTML, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setText(Html.fromHtml(attributeValue));
            }
        });
        addHandler(Attributes.TextView.Text, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setText(attributeValue);
            }
        });

        addHandler(Attributes.TextView.DrawablePadding, new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(float dimension, T view, String key, JsonElement value) {
                view.setCompoundDrawablePadding((int) dimension);
            }
        });

        addHandler(Attributes.TextView.TextSize, new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(float dimension, T view, String key, JsonElement value) {
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension);
            }
        });
        addHandler(Attributes.TextView.Gravity, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setGravity(ParseHelper.parseGravity(attributeValue));
            }
        });

        addHandler(Attributes.TextView.TextColor, new ColorResourceProcessor<T>() {

            @Override
            public void setColor(T view, int color) {
                view.setTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setTextColor(colors);
            }
        });

        addHandler(Attributes.TextView.TextColorHint, new ColorResourceProcessor<T>() {

            @Override
            public void setColor(T view, int color) {
                view.setHintTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setHintTextColor(colors);
            }
        });

        addHandler(Attributes.TextView.TextColorLink, new ColorResourceProcessor<T>() {

            @Override
            public void setColor(T view, int color) {
                view.setLinkTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setLinkTextColor(colors);
            }
        });

        addHandler(Attributes.TextView.TextColorHighLight, new ColorResourceProcessor<T>() {

            @Override
            public void setColor(T view, int color) {
                view.setHighlightColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                //
            }
        });

        addHandler(Attributes.TextView.DrawableLeft, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                Drawable[] compoundDrawables = view.getCompoundDrawables();
                view.setCompoundDrawablesWithIntrinsicBounds(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
            }
        });
        addHandler(Attributes.TextView.DrawableTop, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                Drawable[] compoundDrawables = view.getCompoundDrawables();
                view.setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], drawable, compoundDrawables[2], compoundDrawables[3]);
            }
        });
        addHandler(Attributes.TextView.DrawableRight, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                Drawable[] compoundDrawables = view.getCompoundDrawables();
                view.setCompoundDrawablesWithIntrinsicBounds(drawable, compoundDrawables[1], drawable, compoundDrawables[3]);
            }
        });
        addHandler(Attributes.TextView.DrawableBottom, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                Drawable[] compoundDrawables = view.getCompoundDrawables();
                view.setCompoundDrawablesWithIntrinsicBounds(drawable, compoundDrawables[1], compoundDrawables[2], drawable);
            }
        });

        addHandler(Attributes.TextView.MaxLines, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setMaxLines(ParseHelper.parseInt(attributeValue));
            }
        });

        addHandler(Attributes.TextView.Ellipsize, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                Enum ellipsize = ParseHelper.parseEllipsize(attributeValue);
                view.setEllipsize((android.text.TextUtils.TruncateAt) ellipsize);
            }
        });

        addHandler(Attributes.TextView.PaintFlags, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                if (attributeValue.equals("strike"))
                    view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        addHandler(Attributes.TextView.Prefix, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setText(attributeValue + view.getText());
            }
        });

        addHandler(Attributes.TextView.Suffix, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setText(view.getText() + attributeValue);
            }
        });

        addHandler(Attributes.TextView.TextStyle, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                int typeface = ParseHelper.parseTextStyle(attributeValue);
                view.setTypeface(Typeface.defaultFromStyle(typeface));
            }
        });

        addHandler(Attributes.TextView.SingleLine, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setSingleLine(ParseHelper.parseBoolean(attributeValue));
            }
        });

        addHandler(Attributes.TextView.TextAllCaps, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    view.setAllCaps(ParseHelper.parseBoolean(attributeValue));
                }
            }
        });
        addHandler(Attributes.TextView.Hint, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.setHint(attributeValue);
            }
        });
    }
}
