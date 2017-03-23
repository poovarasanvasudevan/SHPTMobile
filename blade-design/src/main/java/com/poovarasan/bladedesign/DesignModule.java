package com.poovarasan.bladedesign;

import com.poovarasan.blade.builder.LayoutBuilder;
import com.poovarasan.blade.module.Module;
import com.poovarasan.blade.parser.ViewParser;
import com.poovarasan.blade.parser.custom.ViewGroupParser;
import com.poovarasan.bladedesign.parser.BladeAppBarParser;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:36 AM
 */

public class DesignModule implements Module {

    @Override
    public void register(LayoutBuilder layoutBuilder) {
        ViewParser viewParser = new ViewParser();
        ViewGroupParser viewGroupParser = new ViewGroupParser(viewParser);

        layoutBuilder.registerHandler("AppBarLayout", new BladeAppBarParser(viewGroupParser));
    }
}
