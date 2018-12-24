package com.example.user.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class MD5Util {
    public static String encode(String str) {
        return Hashing.md5().newHasher().putString(str, Charsets.UTF_8).hash().toString();
    }
}
