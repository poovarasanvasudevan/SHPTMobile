package com.poovarasan.bladeappcompat.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 8:57 AM
 */

public class AppCheckbox extends AppCompatCheckBox implements BladeView {
    private BladeViewManager viewManager;

    public AppCheckbox(Context context) {
        super(context);
    }

    public AppCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public BladeViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(BladeViewManager bladeViewManager) {
        this.viewManager = bladeViewManager;
    }
}
