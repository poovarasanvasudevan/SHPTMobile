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

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;

import com.poovarasan.blade.parser.ViewParser;
import com.poovarasan.blade.parser.custom.AutoCompleteTextViewParser;
import com.poovarasan.blade.parser.custom.ButtonParser;
import com.poovarasan.blade.parser.custom.CheckBoxParser;
import com.poovarasan.blade.parser.custom.EditTextParser;
import com.poovarasan.blade.parser.custom.FrameLayoutParser;
import com.poovarasan.blade.parser.custom.HorizontalProgressBarParser;
import com.poovarasan.blade.parser.custom.HorizontalScrollViewParser;
import com.poovarasan.blade.parser.custom.ImageButtonParser;
import com.poovarasan.blade.parser.custom.ImageViewParser;
import com.poovarasan.blade.parser.custom.LinearLayoutParser;
import com.poovarasan.blade.parser.custom.ProgressBarParser;
import com.poovarasan.blade.parser.custom.RadioButtonParser;
import com.poovarasan.blade.parser.custom.RadioGroupParser;
import com.poovarasan.blade.parser.custom.RatingBarParser;
import com.poovarasan.blade.parser.custom.RelativeLayoutParser;
import com.poovarasan.blade.parser.custom.ScrollViewParser;
import com.poovarasan.blade.parser.custom.TextViewParser;
import com.poovarasan.blade.parser.custom.ViewGroupParser;
import com.poovarasan.blade.parser.custom.ViewPagerParser;
import com.poovarasan.blade.parser.custom.WebViewParser;
import com.poovarasan.blade.toolbox.Formatter;
import com.poovarasan.blade.toolbox.IdGenerator;
import com.poovarasan.blade.toolbox.IdGeneratorImpl;
import com.poovarasan.blade.toolbox.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Factory class for creating Layout builders with different predefined behaviours. This is the
 * only way to create layout builder objects. To create a simple layout builder use
 * {@link LayoutBuilderFactory#getSimpleLayoutBuilder(IdGenerator)}
 */
public class LayoutBuilderFactory {

    private SimpleLayoutBuilder             simpleLayoutBuilderInstance;
    private DataParsingLayoutBuilder        dataParsingLayoutBuilderInstance;
    private DataAndViewParsingLayoutBuilder dataAndViewParsingLayoutBuilderInstance;

    /**
     * Returns a layout builder which can parse @data blocks as well as custom view blocks.
     * See {@link DataParsingLayoutBuilder}
     *
     * @return A new {@link DataAndViewParsingLayoutBuilder}
     */
    public DataAndViewParsingLayoutBuilder getDataAndViewParsingLayoutBuilder(Map<String, JsonObject> layouts, @NonNull IdGenerator idGenerator) {
        if (dataAndViewParsingLayoutBuilderInstance == null) {
            dataAndViewParsingLayoutBuilderInstance = new DataAndViewParsingLayoutBuilder(layouts, idGenerator);
            registerBuiltInHandlers(dataAndViewParsingLayoutBuilderInstance);
            registerFormatter(dataAndViewParsingLayoutBuilderInstance);
        }
        return dataAndViewParsingLayoutBuilderInstance;
    }

    public DataAndViewParsingLayoutBuilder getDataAndViewParsingLayoutBuilder(Map<String, JsonObject> layouts) {
        if (dataAndViewParsingLayoutBuilderInstance == null) {
            dataAndViewParsingLayoutBuilderInstance = new DataAndViewParsingLayoutBuilder(layouts, new IdGeneratorImpl());
            registerBuiltInHandlers(dataAndViewParsingLayoutBuilderInstance);
            registerFormatter(dataAndViewParsingLayoutBuilderInstance);
        }
        return dataAndViewParsingLayoutBuilderInstance;
    }

    /**
     * Returns a layout builder which can parse @data blocks. See {@link DataParsingLayoutBuilder}
     *
     * @return A new {@link DataParsingLayoutBuilder}
     */
    public DataParsingLayoutBuilder getDataParsingLayoutBuilder(@NonNull IdGenerator idGenerator) {
        if (dataParsingLayoutBuilderInstance == null) {
            dataParsingLayoutBuilderInstance = new DataParsingLayoutBuilder(idGenerator);
            registerBuiltInHandlers(dataParsingLayoutBuilderInstance);
            registerFormatter(dataParsingLayoutBuilderInstance);
        }
        return dataParsingLayoutBuilderInstance;
    }

    public DataParsingLayoutBuilder getDataParsingLayoutBuilder() {
        if (dataParsingLayoutBuilderInstance == null) {
            dataParsingLayoutBuilderInstance = new DataParsingLayoutBuilder(new IdGeneratorImpl());
            registerBuiltInHandlers(dataParsingLayoutBuilderInstance);
            registerFormatter(dataParsingLayoutBuilderInstance);
        }
        return dataParsingLayoutBuilderInstance;
    }

    /**
     * Returns a simple layout builder. See {@link SimpleLayoutBuilder}
     *
     * @return A new {@link SimpleLayoutBuilder}
     */
    public SimpleLayoutBuilder getSimpleLayoutBuilder(@NonNull IdGenerator idGenerator) {
        if (simpleLayoutBuilderInstance == null) {
            simpleLayoutBuilderInstance = new SimpleLayoutBuilder(idGenerator);
            registerBuiltInHandlers(simpleLayoutBuilderInstance);
        }
        return simpleLayoutBuilderInstance;
    }

    public SimpleLayoutBuilder getSimpleLayoutBuilder() {
        if (simpleLayoutBuilderInstance == null) {
            simpleLayoutBuilderInstance = new SimpleLayoutBuilder(new IdGeneratorImpl());
            registerBuiltInHandlers(simpleLayoutBuilderInstance);
        }
        return simpleLayoutBuilderInstance;
    }


    /**
     * This method will register all the internal layout handlers to the builder specified.
     *
     * @param layoutBuilder The layout builder which will have handlers registered to it.
     */
    @SuppressWarnings("unchecked")
    protected void registerBuiltInHandlers(LayoutBuilder layoutBuilder) {
        ViewParser                  viewParser                  = new ViewParser();
        ImageViewParser             imageViewParser             = new ImageViewParser(viewParser);
        ImageButtonParser           imageButtonParser           = new ImageButtonParser(imageViewParser);
        ViewGroupParser             viewGroupParser             = new ViewGroupParser(viewParser);
        RelativeLayoutParser        relativeLayoutParser        = new RelativeLayoutParser(viewGroupParser);
        LinearLayoutParser          linearLayoutParser          = new LinearLayoutParser(viewGroupParser);
        FrameLayoutParser           frameLayoutParser           = new FrameLayoutParser(viewGroupParser);
        ScrollViewParser            scrollViewParser            = new ScrollViewParser(frameLayoutParser);
        HorizontalScrollViewParser  horizontalScrollViewParser  = new HorizontalScrollViewParser(frameLayoutParser);
        TextViewParser              textViewParser              = new TextViewParser(viewParser);
        EditTextParser              editTextParser              = new EditTextParser(textViewParser);
        AutoCompleteTextViewParser  autoCompleteTextViewParser  = new AutoCompleteTextViewParser(editTextParser);
        ButtonParser                buttonParser                = new ButtonParser(textViewParser);
        ViewPagerParser             viewPagerParser             = new ViewPagerParser(viewGroupParser);
        WebViewParser               webViewParser               = new WebViewParser(viewParser);
        RatingBarParser             ratingBarParser             = new RatingBarParser(viewParser);
        CheckBoxParser              checkBoxParser              = new CheckBoxParser(buttonParser);
        ProgressBarParser           progressBarParser           = new ProgressBarParser(viewParser);
        HorizontalProgressBarParser horizontalProgressBarParser = new HorizontalProgressBarParser(progressBarParser);
        RadioGroupParser            radioGroupParser            = new RadioGroupParser(viewGroupParser);
        RadioButtonParser           radioButtonParser           = new RadioButtonParser(checkBoxParser);


        layoutBuilder.registerHandler("View", viewParser);
        layoutBuilder.registerHandler("ViewGroup", viewGroupParser);
        layoutBuilder.registerHandler("RelativeLayout", relativeLayoutParser);
        layoutBuilder.registerHandler("LinearLayout", linearLayoutParser);
        layoutBuilder.registerHandler("FrameLayout", frameLayoutParser);
        layoutBuilder.registerHandler("ScrollView", scrollViewParser);
        layoutBuilder.registerHandler("HorizontalScrollView", horizontalScrollViewParser);
        layoutBuilder.registerHandler("ImageView", imageViewParser);
        layoutBuilder.registerHandler("TextView", textViewParser);
        layoutBuilder.registerHandler("EditText", editTextParser);
        layoutBuilder.registerHandler("AutoCompleteTextView", autoCompleteTextViewParser);
        layoutBuilder.registerHandler("Button", buttonParser);
        layoutBuilder.registerHandler("ImageButton", imageButtonParser);
        layoutBuilder.registerHandler("ViewPager", viewPagerParser);
        layoutBuilder.registerHandler("WebView", webViewParser);
        layoutBuilder.registerHandler("RatingBar", ratingBarParser);
        layoutBuilder.registerHandler("CheckBox", checkBoxParser);
        layoutBuilder.registerHandler("ProgressBar", progressBarParser);
        layoutBuilder.registerHandler("HorizontalProgressBar", horizontalProgressBarParser);
        layoutBuilder.registerHandler("RadioGroup", radioGroupParser);
        layoutBuilder.registerHandler("RadioButton", radioButtonParser);
    }

    protected void registerFormatter(DataParsingLayoutBuilder layoutBuilder) {

        Formatter NumberFormatter = new Formatter() {

            private DecimalFormat formatter;

            @Override
            public String format(JsonElement elementValue) {
                double valueAsNumber;
                try {
                    valueAsNumber = Double.parseDouble(elementValue.getAsString());
                } catch (NumberFormatException e) {
                    return elementValue.toString();
                }
                formatter = new DecimalFormat("#,###");
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                    formatter.setRoundingMode(RoundingMode.FLOOR);
                }
                formatter.setMinimumFractionDigits(0);
                formatter.setMaximumFractionDigits(2);
                return formatter.format(valueAsNumber);
            }

            @Override
            public String getName() {
                return "number";
            }
        };

        Formatter DateFormatter = new Formatter() {

            @SuppressLint("SimpleDateFormat")
            private SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressLint("SimpleDateFormat")
            private SimpleDateFormat to = new SimpleDateFormat("d MMM, E");

            @Override
            public String format(JsonElement elementValue) {
                try {
                    // 2015-06-18 12:01:37
                    Date date = from.parse(elementValue.getAsString());
                    return to.format(date);
                } catch (Exception e) {
                    return elementValue.toString();
                }
            }

            @Override
            public String getName() {
                return "date";
            }
        };

        Formatter IndexFormatter = new Formatter() {
            @Override
            public String format(JsonElement elementValue) {
                int valueAsNumber;
                try {
                    valueAsNumber = Integer.parseInt(elementValue.getAsString());
                } catch (NumberFormatException e) {
                    return elementValue.toString();
                }
                return String.valueOf(valueAsNumber + 1);
            }

            @Override
            public String getName() {
                return "index";
            }
        };

        Formatter joinFormatter = new Formatter() {
            @Override
            public String format(JsonElement elementValue) {
                if (elementValue.isJsonArray()) {
                    return Utils.getStringFromArray(elementValue.getAsJsonArray(), ",");
                } else {
                    return elementValue.toString();
                }
            }

            @Override
            public String getName() {
                return "join";
            }
        };

        layoutBuilder.registerFormatter(NumberFormatter);
        layoutBuilder.registerFormatter(DateFormatter);
        layoutBuilder.registerFormatter(IndexFormatter);
        layoutBuilder.registerFormatter(joinFormatter);
    }
}
