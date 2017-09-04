//
// Created by Administrator on 2017/8/11.
//

#include <jni.h>
#include <testC.h>
#include <android/log.h>

#define LOG_TAG "testTAG"
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, fmt, ##args)

JNIEXPORT jint JNICALL
Java_com_googolfist_smartcontrolcenter_TestJni_testJni(JNIEnv *env, jobject instance) {

    // TODO

}


JNIEXPORT void JNICALL
Java_com_googolfist_smartcontrolcenter_TestJni_testPrint(JNIEnv *env, jobject instance) {
    LOGD("is running  = %s", "OK");
    LOGD("testprint = %s", testString());
   /* jchar *chars = env->GetCharArrayElements(chars_, NULL);
    // TODO
    env->ReleaseCharArrayElements(chars_, chars, 0);*/
}

JNIEXPORT jstring JNICALL
Java_com_googolfist_smartcontrolcenter_TestJni_print(JNIEnv *env, jobject instance) {

    // TODO


    return (*env)->NewStringUTF(env, "dfds");
}