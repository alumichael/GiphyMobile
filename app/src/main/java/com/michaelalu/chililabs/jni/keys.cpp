//
// Created by micahel alu on 02/11/2025.
//To keep my senstive parameter like api key
//

#include <jni.h>
#include <string>
#include <vector>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_michaelalu_chililabs_jni_KeyRepository_getApiKey(JNIEnv *env, jobject) {
    const char *apiKey = "xJxDd4LWkP00ipATzkDsjMDnk8cCsysb";
    return env->NewStringUTF(apiKey);
}

