package com.icbc.zoro.common;

public class ZoroUtil {
	public static String clearNotChinese(String buff) {
		String tmpString = buff.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");// 去掉所有中英文符号
		char[] carr = tmpString.toCharArray();
		for (int i = 0; i < tmpString.length(); i++) {
			if (carr[i] < 0xFF) {
				carr[i] = ' ';// 过滤掉非汉字内容
			}
		}
		return String.copyValueOf(carr).trim();
	}
	/***
     * 获取字符串中第一层div对象中的内容，并且去掉特殊符号
     */
	public static String divFormat(String str) {
		StringBuffer result = new StringBuffer();
		int level = 0;
        for (int i = 0; i < str.length()-5; i++) {
            char tmp = str.charAt(i);
            if(str.charAt(i)=='<'&&str.charAt(i+1)=='d'&&str.charAt(i+2)=='i'&&str.charAt(i+3)=='v') {
            	level++;
            }else if(str.charAt(i)=='<'&&str.charAt(i+1)=='/'&&str.charAt(i+2)=='d'&&str.charAt(i+3)=='i'&&str.charAt(i+3)=='v'&&str.charAt(i+3)=='>') {
            	level--;
            }
            if (level<=1) {
            	result.append(tmp);
            } 
        }
        return result.toString();
    }
	/***
     * 获取字符串中文数
     * @param str 需要判断的字符串
     * @return int 字符串中中文数
     */
	public static int getChineseCount(String str) {
		
		int chCharacter = 0;
   
        
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if (isChinese(tmp)) {
                chCharacter ++;
            } 
        }
        return chCharacter;
    }
	/***
     * 判断字符是否为中文
     * @param ch 需要判断的字符
     * @return 中文返回true，非中文返回false
     */
    private static boolean isChinese(char ch) {
        //获取此字符的UniCodeBlock
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        //  GENERAL_PUNCTUATION 判断中文的“号  
        //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
        //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号 
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B 
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS 
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
