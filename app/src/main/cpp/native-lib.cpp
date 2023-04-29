#include <jni.h>
#include <string>


#include <jni.h>




extern "C"
JNIEXPORT jstring JNICALL
Java_com_daniel_dkm_common_Constants_getStringBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("http://");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_daniel_dkm_common_Constants_getStringBaseUrlDemo(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://");
}