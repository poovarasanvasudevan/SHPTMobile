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

package com.poovarasan.blade.processor;

import com.google.gson.JsonElement;

import com.poovarasan.blade.EventType;
import com.poovarasan.blade.builder.LayoutBuilderCallback;
import com.poovarasan.blade.view.BladeView;

/**
 * Use this as the base processor for handling events like OnClick , OnLongClick , OnTouch etc.
 * Created by prateek.dixit on 20/11/14.
 */

public abstract class EventProcessor<T> extends AttributeProcessor<T> {

    @Override
    public void handle(String key, JsonElement value, T view) {
        setOnEventListener(view, value);
    }

    public abstract void setOnEventListener(T view, JsonElement attributeValue);

    /**
     * This delegates Event with required attributes to client
     */
    public void fireEvent(BladeView view, EventType eventType, JsonElement attributeValue) {
        LayoutBuilderCallback layoutBuilderCallback = view.getViewManager().getLayoutBuilder().getListener();
        layoutBuilderCallback.onEvent(view, attributeValue, eventType);
    }
}
