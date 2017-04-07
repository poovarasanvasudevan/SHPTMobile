package com.poovarasan.bladecardview.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * Created by poovarasanv on 23/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 23/3/17 at 11:22 AM
 */

public class AppCardView extends CardView implements BladeView {

    private BladeViewManager bladeViewManager;

    public AppCardView(Context context) {
        super(context);

        init();
    }

    public AppCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public BladeViewManager getViewManager() {
        return bladeViewManager;
    }

    @Override
    public void setViewManager(BladeViewManager bladeViewManager) {
        this.bladeViewManager = bladeViewManager;
    }

    public void init() {
        super.setRadius(0f);
    }
}
