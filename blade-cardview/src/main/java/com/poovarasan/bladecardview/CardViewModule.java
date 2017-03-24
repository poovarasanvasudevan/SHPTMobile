package com.poovarasan.bladecardview;

import com.poovarasan.blade.builder.LayoutBuilder;
import com.poovarasan.blade.module.Module;
import com.poovarasan.blade.parser.ViewParser;
import com.poovarasan.blade.parser.custom.ViewGroupParser;
import com.poovarasan.bladecardview.parser.AppCardViewParser;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:23 AM
 */

public class CardViewModule implements Module {
    @Override
    public void register(LayoutBuilder layoutBuilder) {
        ViewParser      viewParser      = new ViewParser();
        ViewGroupParser viewGroupParser = new ViewGroupParser(viewParser);

        layoutBuilder.registerHandler("CardView", new AppCardViewParser(viewGroupParser));
    }
}
