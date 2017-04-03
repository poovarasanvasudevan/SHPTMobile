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

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.poovarasan.blade.toolbox.IdGenerator;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.toolbox.Utils;
import com.poovarasan.blade.view.BladeView;

import java.util.Map;

/**
 * A layout builder which can parse @data and @view blocks before passing it on to
 * {@link SimpleLayoutBuilder}
 */
public class DataAndViewParsingLayoutBuilder extends DataParsingLayoutBuilder {

    private Map<String, JsonObject> layouts;

    protected DataAndViewParsingLayoutBuilder(Map<String, JsonObject> layouts, @NonNull IdGenerator idGenerator) {
        super(idGenerator);
        this.layouts = layouts;
    }

    public void setLayouts(Map<String, JsonObject> layouts) {
        this.layouts = layouts;
    }

    @Override
    protected BladeView onUnknownViewEncountered(String type, ViewGroup parent, JsonObject source, JsonObject data, int index, Styles styles) {
        JsonElement element = null;
        if (layouts != null) {
            element = layouts.get(type);
        }
        if (element != null && !element.isJsonNull()) {
            JsonObject layout = element.getAsJsonObject();
            layout = Utils.mergeLayouts(layout, source);
            BladeView view = build(parent, layout, data, index, styles);
            onViewBuiltFromViewProvider(view, type, parent, index);
            return view;
        }
        return super.onUnknownViewEncountered(type, parent, source, data, index, styles);
    }

    private void onViewBuiltFromViewProvider(BladeView view, String type, View parent, int childIndex) {
        if (listener != null) {
            listener.onViewBuiltFromViewProvider(view, parent, type, childIndex);
        }
    }
}
