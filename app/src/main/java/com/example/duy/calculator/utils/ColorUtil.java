/*
 * Copyright 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.duy.calculator.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Duy on 19/7/2016
 */
public class ColorUtil {
    public static int RED = Color.parseColor("#FF5252");
    public static int PINK = Color.parseColor("#E91E63");
    public static int PURPLE = Color.parseColor("#9C27B0");
    public static int DEEP_PURPLE = Color.parseColor("#7C4DFF");
    public static int INDIGO = Color.parseColor("#3F51B5");
    public static int BLUE = Color.parseColor("#448AFF");
    public static int CYAN = Color.parseColor("#03A9F4");
    public static int TEAL = Color.parseColor("#009688");
    public static int GREEN = Color.parseColor("#009688");
    public static int LIGHT_GREEN = Color.parseColor("#8BC34A");
    public static int LIME = Color.parseColor("#CDDC39");
    public static int YELLOW = Color.parseColor("#FFEB3B");
    public static int ORANGE = Color.parseColor("#FFEB3B");
    public static int BROW = Color.parseColor("#795548");
    public static int GREY = Color.parseColor("#9E9E9E");
    public static int BLUE_GREY = Color.parseColor("#9E9E9E");
    public static int BLACK = Color.parseColor("#000000");
    public static int COLOR[] = {RED, PINK, PURPLE, DEEP_PURPLE, INDIGO, BLUE, CYAN, TEAL, GREEN,
            LIGHT_GREEN, LIME, YELLOW, ORANGE, BROW, GREY, BLUE_GREY
    };

    public int getColorRandom() {
        return COLOR[new Random(new Random().nextLong()).nextInt(COLOR.length - 1)];
    }
}
