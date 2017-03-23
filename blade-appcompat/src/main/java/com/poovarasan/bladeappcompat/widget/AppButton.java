package com.poovarasan.bladeappcompat.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 9:25 AM
 */

public class AppButton extends AppCompatButton implements BladeView {
    private BladeViewManager bladeViewManager;

    public AppButton(Context context) {
        super(context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
