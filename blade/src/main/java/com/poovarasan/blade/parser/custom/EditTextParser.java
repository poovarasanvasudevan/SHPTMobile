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

import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.StringAttributeProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeEditText;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by Poovarasan Vasudevan on 25/11/14.
 */
public class EditTextParser<T extends EditText> extends WrappableParser<T> {

    public EditTextParser(Parser<T> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeEditText(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();


        addHandler(new Attributes.Attribute("inputType"), new StringAttributeProcessor<T>() {
            @Override
            public void handle(String attributeKey, String attributeValue, T view) {

                switch (attributeValue) {
                    case "NUMBER":
                        view.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case "TEXT":
                        view.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case "EMAIL":
                        view.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case "PHONE":
                        view.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case "PASSWORD":
                        view.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    default:
                        view.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }
}
