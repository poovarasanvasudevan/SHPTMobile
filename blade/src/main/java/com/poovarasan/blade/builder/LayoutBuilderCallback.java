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

package com.poovarasan.blade.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Adapter;

import com.poovarasan.blade.EventType;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;

import java.util.List;

/**
 * @author Poovarasan Vasudevan
 */
public interface LayoutBuilderCallback {

    /**
     * called when the builder encounters an attribute key which is unhandled by its parser.
     *
     * @param attribute attribute that is being parsed
     * @param view      corresponding view for current attribute that is being parsed
     */
    void onUnknownAttribute(String attribute, JsonElement value, BladeView view);

    /**
     * called when the builder encounters a view type which it cannot understand.
     */
    @Nullable
    BladeView onUnknownViewType(String type, View parent, JsonObject layout, JsonObject data, int index, Styles styles);

    JsonObject onLayoutRequired(String type, BladeView parent);

    void onViewBuiltFromViewProvider(BladeView view, View parent, String type, int index);

    /**
     * called when any click occurs on views
     *
     * @param view The view that triggered the event
     */
    View onEvent(BladeView view, JsonElement value, EventType eventType);

    PagerAdapter onPagerAdapterRequired(BladeView parent, final List<BladeView> children, JsonObject layout);

    Adapter onAdapterRequired(BladeView parent, final List<BladeView> children, JsonObject layout);

}
