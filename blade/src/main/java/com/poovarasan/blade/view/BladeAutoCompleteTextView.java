package com.poovarasan.blade.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 31/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 31/3/17 at 3:20 PM
 */

@SuppressLint("AppCompatCustomView")
public class BladeAutoCompleteTextView extends AutoCompleteTextView implements BladeView {

    private BladeViewManager viewManager;

    public BladeAutoCompleteTextView(Context context) {
        super(context);
    }

    public BladeAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BladeAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
