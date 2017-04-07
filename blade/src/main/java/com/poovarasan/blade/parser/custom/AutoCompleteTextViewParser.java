package com.poovarasan.blade.parser.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.poovarasan.blade.parser.Attributes;
import com.poovarasan.blade.parser.Parser;
import com.poovarasan.blade.parser.WrappableParser;
import com.poovarasan.blade.processor.JsonDataProcessor;
import com.poovarasan.blade.toolbox.Styles;
import com.poovarasan.blade.view.BladeAutoCompleteTextView;
import com.poovarasan.blade.view.BladeView;

/**
 * Created by poovarasanv on 31/3/17.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 31/3/17 at 3:29 PM
 */

public class AutoCompleteTextViewParser extends WrappableParser<AutoCompleteTextView> {
    public AutoCompleteTextViewParser(Parser<AutoCompleteTextView> wrappedParser) {
        super(wrappedParser);
    }

    @Override
    public BladeView createView(ViewGroup parent, JsonObject layout, JsonObject data, Styles styles, int index) {
        return new BladeAutoCompleteTextView(parent.getContext());
    }

    @Override
    protected void prepareHandlers() {
        super.prepareHandlers();

        addHandler(new Attributes.Attribute("data"), new JsonDataProcessor<AutoCompleteTextView>() {
            @Override
            public void handle(String key, JsonElement value, AutoCompleteTextView view) {
                JsonArray dataArray   = value.getAsJsonArray();
                String[]  adapterData = new String[dataArray.size()];
                for (int i = 0; i < dataArray.size(); i++) {
                    adapterData[i] = dataArray.get(i).getAsString();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.select_dialog_item, adapterData);
                view.setAdapter(arrayAdapter);
            }
        });

    }


}
