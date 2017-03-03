package com.shpt.core.processor;

import com.google.gson.JsonElement;

import android.view.View;

import com.flipkart.android.proteus.processor.AttributeProcessor;

/**
 * Created by poovarasanv on 13/2/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 13/2/17 at 11:35 AM
 */

public abstract class JsonObjectProcessor<V extends View> extends AttributeProcessor<V> {
    public JsonObjectProcessor() {
    }

    public void handle(String key, JsonElement value, V view) {
        this.handle(key, value.getAsJsonObject().toString(), view);
    }


    public abstract void handle(String var1, String var2, V var3);
}
