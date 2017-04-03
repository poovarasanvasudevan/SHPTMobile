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

package com.poovarasan.blade.parser.custom;

import com.google.gson.JsonObject;

import android.view.ViewGroup;
import android.webkit.WebView;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeView;
import com.poovarasan.blade.view.BladeWebView;

/**
 * Created by Poovarasan Vasudevan on 12/05/14.
 */
public class WebViewParser<T extends WebView> extends WrappableParser<T> {
    public WebViewParser(Parser<T> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeWebView(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();
        addHandler(Attributes.WebView.Url, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.loadUrl(attributeValue);
            }
        });
        addHandler(Attributes.WebView.HTML, new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {
                view.loadData(attributeValue, "text/html", "UTF-8");
            }
        });
    }
}
