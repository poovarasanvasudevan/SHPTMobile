package com.poovarasan.bladeappcompat.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.content.res.ColorStateList;
import android.view.ViewGroup;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.ColorResourceProcessor;
import com.poovarasan.blade.processor.DimensionAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladeappcompat.widget.AppProgressBar;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 2:26 PM
 */

public class AppProgressBarParser extends WrappableParser<AppProgressBar> {
    public AppProgressBarParser(Parser<AppProgressBar> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppProgressBar(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(new Attributes.Attribute("barColor"), new ColorResourceProcessor<AppProgressBar>() {
            @Override
            public void setColor(AppProgressBar view, int color) {
                view.setBarColor(color);
            }

            @Override
            public void setColor(AppProgressBar view, ColorStateList colors) {
                view.setBarColor(colors.getDefaultColor());
            }
        });


        addHandler(new Attributes.Attribute("barWidth"), new DimensionAttributeProcessor<AppProgressBar>() {
            @Override
            public void setDimension(float dimension, AppProgressBar view, String key, JsonElement value) {
                view.setBarWidth(Math.round(dimension));
            }
        });
    }
}
