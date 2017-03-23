package com.poovarasan.bladecardview.parser;

import com.google.gson.JsonObject;

import android.view.ViewGroup;

import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.bladecardview.widget.AppCardView;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:30 AM
 */

public class AppCardViewParser extends WrappableParser<AppCardView> {
    public AppCardViewParser(Parser<AppCardView> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new AppCardView(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();


    }
}
