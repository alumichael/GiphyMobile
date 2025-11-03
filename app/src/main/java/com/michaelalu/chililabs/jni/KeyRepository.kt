package com.michaelalu.chililabs.jni

import javax.inject.Inject

/**
 * Created by Michael Alu 02/11/2025

 *
 * [KeyRepository] is used to return both public and private
 * key pair securely stored in an NDK
 *
 * */

class KeyRepository @Inject constructor() {
    init {
        System.loadLibrary("keys")
    }
    external fun getApiKey(): String
}