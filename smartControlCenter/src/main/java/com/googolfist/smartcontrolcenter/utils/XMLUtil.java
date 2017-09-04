package com.googolfist.smartcontrolcenter.utils;

import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 2017/6/3.
 */

public class XMLUtil {
    private static final String TAG = "XMLUtil";

    public static Document StringToXML(String str) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            //Log.d("XMLUtil", str);
            doc = reader.read(new ByteArrayInputStream(str.getBytes()));
        } catch (DocumentException e) {
            Log.e(TAG, "StringToXML: " + str, e);
        }
        return doc;
    }
}
