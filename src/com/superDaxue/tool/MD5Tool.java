package com.superDaxue.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5 算法
*/
public class MD5Tool {
    
    // 全局数组
    private final String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    // 返回形式为数字跟字符串
    private String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
    // 转换字节数组为16进制字串
    private String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public String GetMD5Code(String strObj) {
    	if(strObj==null){
    		return "";
    	}
        String resultString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        MD5Tool md5 = new MD5Tool();
        String checkCode=md5.GetMD5Code(md5.GetMD5Code("k3k5".toUpperCase()).substring(0,30).toUpperCase()+"11825").substring(0,30).toUpperCase();
        String passCode=md5.GetMD5Code("2013080100420"+md5.GetMD5Code("131131").substring(0,30).toUpperCase()+"11825").substring(0,30).toUpperCase();
		System.out.println(passCode);
        System.out.println(checkCode);
       	
    }
}