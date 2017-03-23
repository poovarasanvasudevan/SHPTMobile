package com.poovarasan.bladedesign.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.os.Build;
import android.view.ViewGroup;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.DimensionAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladedesign.widget.BladeAppBar;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:37 AM
 */

public class BladeAppBarParser extends WrappableParser<BladeAppBar> {
    public BladeAppBarParser(Parser<BladeAppBar> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeAppBar(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(new Attributes.Attribute("elevation"), new DimensionAttributeProcessor<BladeAppBar>() {
            @Override
            public void setDimension(float dimension, BladeAppBar view, String key, JsonElement value) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setElevation(dimension);
                }
            }
        });


    }
}
