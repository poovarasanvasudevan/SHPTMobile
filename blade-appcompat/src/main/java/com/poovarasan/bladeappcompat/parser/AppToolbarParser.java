package com.poovarasan.bladeappcompat.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.ColorResourceProcessor;
import com.poovarasan.blade.processor.DimensionAttributeProcessor;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladeappcompat.widget.AppToolbar;

/**
 * Created by poovarasanv on 22/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 22/3/17 at 7:04 PM
 */

public class AppToolbarParser extends WrappableParser<AppToolbar> {
    public AppToolbarParser(Parser<AppToolbar> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppToolbar(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(new Attributes.Attribute("elevation"), new DimensionAttributeProcessor<AppToolbar>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void setDimension(float dimension, AppToolbar view, String key, JsonElement value) {
                view.setElevation(dimension);
            }
        });

        addHandler(new Attributes.Attribute("titleColor"), new ColorResourceProcessor<AppToolbar>() {
            @Override
            public void setColor(AppToolbar view, int color) {
                view.setTitleTextColor(color);
            }

            @Override
            public void setColor(AppToolbar view, ColorStateList colors) {
                view.setTitleTextColor(colors.getDefaultColor());
            }
        });
        addHandler(new Attributes.Attribute("title"), new StringAttributeProcessor<AppToolbar>() {
            @Override
            public void handle(String attributeKey, String attributeValue, AppToolbar view) {
                view.setTitle(attributeValue);
            }
        });
        addHandler(new Attributes.Attribute("subTitle"), new StringAttributeProcessor<AppToolbar>() {
            @Override
            public void handle(String attributeKey, String attributeValue, AppToolbar view) {
                view.setSubtitle(attributeValue);
            }
        });

        addHandler(new Attributes.Attribute("backButton"), new StringAttributeProcessor<AppToolbar>() {
            @Override
            public void handle(String attributeKey, String attributeValue, AppToolbar view) {
                if (attributeValue.equalsIgnoreCase("true") || attributeValue.equalsIgnoreCase("yes")) {

                }
            }
        });
    }
}
