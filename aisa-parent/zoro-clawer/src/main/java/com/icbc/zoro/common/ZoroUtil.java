package com.icbc.zoro.common;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZoroUtil {
	/***
     * 去掉div标签中的a标签内容
     */
	public static String divTagRemoveATag(String str) {
		
		String result = "";
        int flag = 1;
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if(i>0&&str.charAt(i) =='<'&&str.charAt(i) =='a') {
            	flag = 0;
            }
            if(flag==0&&str.charAt(i) =='<'&&str.charAt(i) =='/') {
            	flag = 1;
            }	
            if (flag>0) {
            	result=result+tmp;
            } 
        }
        return result;
    }
	/***
     * 获取字符串中非内层div对象中的内容，并且去掉特殊符号
     */
	public static String divFormat(String str) {
		StringBuffer result = new StringBuffer();
		int level = 0;
        for (int i = 0; i < str.length()-5; i++) {
            char tmp = str.charAt(i);
            if(str.charAt(i)=='<'&&str.charAt(i+1)=='d'&&str.charAt(i+2)=='i'&&str.charAt(i+3)=='v') {
            	level++;
            }
            else if(str.charAt(i)=='<'&&str.charAt(i+1)=='/'&&str.charAt(i+2)=='d'&&str.charAt(i+3)=='i'&&str.charAt(i+4)=='v') {
            	level--;
            }
        	if(level<=1) {
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
	public static String getMainContent(String str,String keyWord) {
		StringBuffer result = new StringBuffer();
		String temp[] = str.split("。");
		for(int i=0;i<temp.length;i++) {
			if(temp[i].contains(keyWord)) {
				result.append(temp[i]);
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
    
    /** 
     * 将短时间格式时间转换为字符串 yyyy-MM-dd 
     *  
     * @param dateDate 
     * @param k 
     * @return 
     */  
    public static String dateToStr(Date dateDate) {  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        String dateString = formatter.format(dateDate);  
        return dateString;  
    }  
      
    /** 
     * 将短时间格式字符串转换为时间 yyyy-MM-dd 
     *  
     * @param strDate 
     * @return 
     */  
    public static Date strToDate(String strDate) {  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        ParsePosition pos = new ParsePosition(0);  
        Date strtodate = formatter.parse(strDate, pos);  
        return strtodate;  
    }  
}
