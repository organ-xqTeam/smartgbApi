package com.smart.socket.util;

import java.security.MessageDigest;

public class MD5Util {
    public MD5Util() {
    }

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int md5Bytes = 0; md5Bytes < charArray.length; ++md5Bytes) {
            byteArray[md5Bytes] = (byte)charArray[md5Bytes];
        }

        byte[] var9 = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for(int i = 0; i < var9.length; ++i) {
            int val = var9[i] & 255;
            if(val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString().toUpperCase();
    }

    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();

        for(int s = 0; s < a.length; ++s) {
            a[s] = (char)(a[s] ^ 116);
        }

        String var3 = new String(a);
        return var3;
    }
}