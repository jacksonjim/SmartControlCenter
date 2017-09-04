package com.googolfist.smartcontrolcenter;

/**
 * Created by Administrator on 2017/8/11.
 */

public class TestJni {
    TestJni(){
        System.loadLibrary("smartHome");

    }

    public native int testJni();

    public native String print();

    public native void testPrint();


}
