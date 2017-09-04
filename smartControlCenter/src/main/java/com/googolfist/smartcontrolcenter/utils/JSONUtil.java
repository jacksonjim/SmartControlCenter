package com.googolfist.smartcontrolcenter.utils;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.googolfist.smartcontrolcenter.model.ItemData;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/3.
 */

public class JSONUtil {
    private static final String TAG = "JSONUtil";

    public static JsonObject StringToJson(String str) {
        JsonParser parser = new JsonParser();
        JsonElement element = null;
        try {
            element = parser.parse(str);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (element != null) {
            return element.getAsJsonObject();
        }
        return null;
    }

    /**
     * JSON格式字符串格式转换为JSON对象
     *
     * @param jsonStr  JSON 格式的字符串为无头部的数组
     * @param objClass 转换目标对象Class
     */
    public static ArrayList<ItemData> paserNoHanderJArray(String jsonStr, Class objClass) {
        JsonParser jsonParser = new JsonParser();

        JsonElement element = null;
        try {
            element = jsonParser.parse(jsonStr);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.w(TAG, "paserNoHanderJArray: " + jsonStr);
        }
        ArrayList<ItemData> data = null;
        if (null != element) {
            JsonArray jsonArray = element.getAsJsonArray();
            Gson gson = new Gson();
            data = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                ItemData obj = (ItemData) gson.fromJson(jsonElement, objClass);
                data.add(obj);
            }
        }
        return data;
    }
}
