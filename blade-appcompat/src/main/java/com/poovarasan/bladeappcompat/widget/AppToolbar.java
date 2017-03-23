package com.poovarasan.bladeappcompat.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.manager.BladeViewManager;
import com.poovarasan.bladeappcompat.R;

/**
 * Created by poovarasanv on 22/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 22/3/17 at 7:02 PM
 */

public class AppToolbar extends Toolbar implements BladeView {

    private BladeViewManager viewManager;

    public AppToolbar(Context context) {
        super(context);

        super.setTitleTextColor(Color.WHITE);
        super.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
