/*
 * Copyright 2016 Poovarasan Vasudevan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poovarasan.blade.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.poovarasan.blade.view.manager.BladeViewManager;

/**
 * ImageButton
 *
 * @author Poovarasan Vasudevan
 */
@SuppressLint("AppCompatCustomView")
public class BladeImageButton extends android.widget.ImageButton implements BladeView {

    private BladeViewManager viewManager;

    public BladeImageButton(Context context) {
        super(context);
    }

    public BladeImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BladeImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BladeImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
