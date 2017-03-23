package com.poovarasan.bladeappcompat.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 9:45 AM
 */

public class AppRadioButton extends AppCompatRadioButton implements BladeView {
    private BladeViewManager bladeViewManager;

    public AppRadioButton(Context context) {
        super(context);
    }

    public AppRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
