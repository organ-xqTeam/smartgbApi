package com.smart.socket.util;

public class ByteUtils {
	//2进制数转换成16进制字符串
    public  static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {

            return null;

        }
        for (int i = 0; i < src.length; i++) {

            int v = src[i] & 0xFF;

            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {

                stringBuilder.append(0);

            }

            stringBuilder.append(hv);

        }

        return stringBuilder.toString();

    }
    /**
     * 2进制数转换成16进制数组
     * @param src
     * @return
     */
    public  static String[] bytesToHexStrings(byte[] src){

        if (src == null || src.length <= 0) {

            return null;

        }
        String[] str = new String[src.length];
        for (int i = 0; i < src.length; i++) {

            int v = src[i] & 0xFF;

            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {

                str[i] = "0";

            }

            str[i] = hv;

        }

        return str;

    }
    /**
     * 16进制字符串转换成 byte数组
     * @param hex
     * @return
     */
    public static  byte[] hextoByte(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {

            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return bytes;
    }
    //////-----byte 和int相互转换
    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset+3] & 0xFF)<<24)
                |((src[offset+2] & 0xFF)<<16)
                |((src[offset+1] & 0xFF)<<8)
                |(src[offset] & 0xFF));
        return value;
    }
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2(int value)
    {
        byte[] src = new byte[4];
        src[3] = (byte) ((value>>24) & 0xFF);
        src[2] = (byte) ((value>>16)& 0xFF);
        src[1] = (byte) ((value>>8)&0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }


    /**
     * 将short数值转换为占2个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToShort2（）配套使用
     */
    public static byte[] shortTobyte2(short value)
    {
        byte[] src = new byte[2];
        src[1] = (byte) ((value>>8) & 0xFF);

        src[0] = (byte) (value & 0xFF);
        return src;
    }
    /**
     * byte数组中取short数值，本方法适用于(低位在后，高位在前)的顺序。和shortToBytes2（）配套使用
     */
    public static short bytesToShort2(byte[] src) {
        short value;
        value = (short) ( ((src[1] & 0xFF)<<8)

                |(src[0] & 0xFF));
        return value;
    }
}
