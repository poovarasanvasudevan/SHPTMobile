package com.poovarasan.bladeappcompat.parser;

import com.google.gson.JsonObject;

import android.view.ViewGroup;

import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladeappcompat.widget.AppButton;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 9:54 AM
 */

public class AppButtonParser extends WrappableParser<AppButton> {
    public AppButtonParser(Parser<AppButton> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppButton(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();
    }
}
