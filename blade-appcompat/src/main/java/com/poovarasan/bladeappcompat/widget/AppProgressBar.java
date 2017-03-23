package com.poovarasan.bladeappcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;
import com.poovarasan.bladeappcompat.custom.ProgressWheel;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 2:24 PM
 */

public class AppProgressBar extends ProgressWheel implements BladeView {
    private BladeViewManager bladeViewManager;

    public AppProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppProgressBar(Context context) {
        super(context);
        super.spin();
        super.setCircleRadius(530);
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
