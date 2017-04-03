package com.poovarasan.blade.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 3/4/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 3/4/17 at 2:18 PM
 */

@SuppressLint("AppCompatCustomView")
public class BladeRadioButton extends RadioButton implements BladeView {
    private BladeViewManager bladeViewManager;

    public BladeRadioButton(Context context) {
        super(context);
    }

    public BladeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public BladeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
