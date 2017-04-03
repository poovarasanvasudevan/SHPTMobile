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

package com.poovarasan.blade.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.poovarasan.blade.R;
import com.poovarasan.blade.processor.AttributeProcessor;
import com.poovarasan.blade.toolbox.BladeConstants;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will help parsing by introducing handlers. Any subclass can use addHandler()
 * method  to specify a callback for an attribute.
 * This class also creates an instance of the view with the first constructor.
 *
 * @author Poovarasan Vasudevan
 * @author Poovarasan Vasudevan
 */
public abstract class Parser<V extends View> implements LayoutHandler<V> {

    private static final String TAG = "Parser";

    private static XmlResourceParser sParser = null;
    private Map<String, AttributeProcessor> handlers = new HashMap<>();

    @Override
    public void onBeforeCreateView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        // nothing to do here
    }

    @Override
    public void onAfterCreateView(V view, ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        try {
            ViewGroup.LayoutParams layoutParams = generateDefaultLayoutParams(parent);
            view.setLayoutParams(layoutParams);
        } catch (Exception e) {
            if (BladeConstants.isLoggingEnabled()) {
                Log.e(TAG, "#createView() : " + e.getMessage());
            }
        }
    }

    protected abstract void prepareHandlers();

    @Override
    public boolean handleAttribute(V view, String attribute, JsonElement value) {
        AttributeProcessor attributeProcessor = handlers.get(attribute);
        if (attributeProcessor != null) {
            //noinspection unchecked
            attributeProcessor.handle(attribute, value, view);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleChildren(BladeView view) {
        return false;
    }

    @Override
    public boolean addView(BladeView parent, BladeView view) {
        return false;
    }

    @Override
    public void prepareAttributeHandlers() {
        if (handlers.size() == 0) {
            prepareHandlers();
        }
    }

    @Override
    public void addHandler(Attributes.Attribute key, AttributeProcessor<V> handler) {
        handlers.put(key.getName(), handler);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams(ViewGroup parent) throws IOException, XmlPullParserException {

        /**
         * This whole method is a hack! To generate layout params, since no other way exists.
         * Refer : http://stackoverflow.com/questions/7018267/generating-a-layoutparams-based-on-the-type-of-parent
         */
        if (null == sParser) {
            synchronized (Parser.class) {
                if (null == sParser) {
                    sParser = parent.getResources().getLayout(R.layout.layout_params_hack);
                    //noinspection StatementWithEmptyBody
                    while (sParser.nextToken() != XmlPullParser.START_TAG) {
                        // Skip everything until the view tag.
                    }
                }
            }
        }

        return parent.generateLayoutParams(sParser);
    }
}

