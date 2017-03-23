package com.poovarasan.bladedesign.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:37 AM
 */

public class BladeAppBar extends AppBarLayout implements BladeView {
    private BladeViewManager bladeViewManager;

    public BladeAppBar(Context context) {
        super(context);
    }

    public BladeAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public BladeViewManager getViewManager() {
        return bladeViewManager;
    }

    @Override
    public void setViewManager(BladeViewManager bladeViewManager) {
        this.bladeViewManager = bladeViewManager;
    }
}
