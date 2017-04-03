package com.poovarasan.blade.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 3/4/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 3/4/17 at 2:15 PM
 */

public class BladeRadioGroup extends RadioGroup implements BladeView {
    private BladeViewManager bladeViewManager;

    public BladeRadioGroup(Context context) {
        super(context);
    }

    public BladeRadioGroup(Context context, AttributeSet attrs) {
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
