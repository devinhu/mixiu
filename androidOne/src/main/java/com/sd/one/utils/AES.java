package com.sd.one.utils;

/**
 * Created by devin on 17/10/12.
 */

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by devin on 17/10/12.
 */

public class AES {

    public static final String DEFAULT_ENCODING = "utf-8";
    private static AES instance;
    private String ivParameter;
    private String sKey;

    static {
        instance = null;
    }

    private AES() {
        this.sKey = "IdolXMfgUGgcmML3";
        this.ivParameter = "0392039203920300";
    }

    public static AES getInstance() {
        if (instance == null) {
            synchronized (AES.class) {
                if (instance == null) {
                    instance = new AES();
                }
            }
        }
        return instance;
    }

    public static String encrypt(String encData, String secretKey, String vector) throws Exception {
        if (secretKey == null || secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(secretKey.getBytes(), "AES"), new IvParameterSpec(vector.getBytes()));
        return new String(Base64.encode(cipher.doFinal(encData.getBytes(DEFAULT_ENCODING)), 0));
    }

    public String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(this.sKey.getBytes(), "AES"), new IvParameterSpec(this.ivParameter.getBytes()));
        return new String(Base64.encode(cipher.doFinal(sSrc.getBytes(DEFAULT_ENCODING)), 0));
    }

    public String decrypt(String sSrc) throws Exception {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(this.sKey.getBytes("ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, skeySpec, new IvParameterSpec(this.ivParameter.getBytes()));
            return new String(cipher.doFinal(Base64.decode(sSrc, 0)), DEFAULT_ENCODING);
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String sSrc, String key, String ivs) throws Exception {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, skeySpec, new IvParameterSpec(ivs.getBytes()));
            return new String(cipher.doFinal(Base64.decode(sSrc, 0)), DEFAULT_ENCODING);
        } catch (Exception e) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 15) + 97));
            strBuf.append((char) ((bytes[i] & 15) + 97));
        }
        return strBuf.toString();
    }

    public static void test() {
        try {
            long lStart = System.currentTimeMillis();
            String enString = getInstance().encrypt("love");
            System.out.println("1:" + enString);
            System.out.println("2\uff1a" + (System.currentTimeMillis() - lStart) + "ms");
            lStart = System.currentTimeMillis();
            System.out.println("3\uff1a" + getInstance().decrypt(enString));
            System.out.println("4\uff1a" + (System.currentTimeMillis() - lStart) + "ms");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
