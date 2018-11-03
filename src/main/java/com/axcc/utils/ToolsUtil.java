
package com.axcc.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 共通功能类
 * @Author 佟士儒
 * @Company chengzhongzhi
 */
public class ToolsUtil {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    static SimpleDateFormat sdfHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
    
    static SimpleDateFormat month = new SimpleDateFormat("MM");
    
    static SimpleDateFormat day = new SimpleDateFormat("dd");
    
    static SimpleDateFormat ymd = new SimpleDateFormat("yyyy年MM月dd日");
    
    static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
     /**
      * 对从url穿过来的中文参数是进行解码，防止乱码
      * @param param 解码内容
      */          
      public static String getDecode(String param)
      {
          String result = StringUtils.EMPTY;
          try {
              result = java.net.URLDecoder.decode(param,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result; 
      }
      
      /**
       * 对通过在Controller里的redirect传递中文参数，跳转后的方法接收不到的情况,
       * @param param 解码内容
       */          
       public static String getEncode(String param)
       {
           String result = StringUtils.EMPTY;
           try {
               // 必须经过两次转码，否则无效
               result = java.net.URLEncoder.encode(param,"UTF-8");
               result = java.net.URLEncoder.encode(result,"UTF-8");
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         return result; 
       }

       /** 
        * 获得一个UUID 
        * @return String UUID 
        */ 
       public static String getUUID(){ 
           String s = UUID.randomUUID().toString(); 
           //去掉“-”符号 
           return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
       }

    /**
        * 获取开始日期和结束日期相差的天数
        * @param fromDate 开始日期
        * @param toDate 结束日期
        * @return 天数
        */
       public static int getDayFromTo(String fromDate , String toDate) {
           int days = 0;
           try {
               Date date1 = sdf.parse(fromDate);
               Date date2 = sdf.parse(toDate);
               Calendar cal = Calendar.getInstance();    
               cal.setTime(date1);    
               long time1 = cal.getTimeInMillis();                 
               cal.setTime(date2);    
               long time2 = cal.getTimeInMillis();         
               long between_days=(time2-time1)/(1000*3600*24);  
               days = Integer.parseInt(String.valueOf(between_days)) + 1;
           } catch (ParseException e) {
               e.printStackTrace();
           }
           return days;
       }
       
       /**
        * 获取开始日期与系统当前日期相差的天数
        * @param fromDate 开始日期
        * @return 天数
        */
       public static int getDayFromSys(String fromDate) {
           int days = 0;
           try {
               Date date1 = sdf.parse(fromDate);
               Date date2 = new Date();
               Calendar cal = Calendar.getInstance();    
               cal.setTime(date1);    
               long time1 = cal.getTimeInMillis();                 
               cal.setTime(date2);    
               long time2 = cal.getTimeInMillis();         
               long between_days=(time2-time1)/(1000*3600*24);  
               days = Integer.parseInt(String.valueOf(between_days)) + 1;
           } catch (ParseException e) {
               e.printStackTrace();
           }
           return days;
       }
       
       /**
        * 到期期限
        * @param fromDate 开始日期
        * @return 天数
        */
       public static int getExpireDay(String fromDate) {
           int days = 0;
           try {
               Date date1 = sdf.parse(fromDate);
               Date date2 = new Date();
               Calendar cal = Calendar.getInstance();    
               cal.setTime(date1);    
               long time1 = cal.getTimeInMillis();                 
               cal.setTime(date2);    
               long time2 = cal.getTimeInMillis();         
               long between_days=(time2-time1)/(1000*3600*24);  
               days = Integer.parseInt(String.valueOf(between_days)) + 1;
           } catch (ParseException e) {
               e.printStackTrace();
           }
           int day = 90 - days;
           if (day < 1) {
               day = 0;
           }
           return day;
       }
    /**
     * 获取30天之后的时间
     *
     **/
//    public static String getFinishDate(String fromDate){
//
//    }

    /**
     * 获取当前年
     * @return 天数
     */   
    public static String getYear() {
           Date date = new Date();
           return yyyy.format(date);
    }
    
    /**
     * 获取当前年
     * @return 天数
     */   
    public static String getYear(Date date) {
           return yyyy.format(date);
    }

    /**
     * 获取当前月
     * @return 天数
     */   
    public static String getMonth() {
           Date date = new Date();
           return month.format(date);
    }
    
    /**
     * 获取当前月
     * 参数：日期
     * @return 天数
     */   
    public static String getMonth(Date date) {
           return month.format(date);
    }
    
    /**
     * 获取当前日
     * @return 天数
     */   
    public static String getDay() {
           Date date = new Date();
           return day.format(date);
    }
    
    /**
     * 获取当前日
     * @return 天数
     */   
    public static String getDay(Date date) {
           return day.format(date);
    }
    
    /**
     * yyyy-MM-dd HH:mm
     * 参数：日期
     * @return 天数
     */   
    public static String getYMDHHSS(Date date) {
           return sdfHHmm.format(date);
    }
    
    /**
     * 获取yyyy年MM月dd
     * 参数：日期
     * @return 天数
     */   
    public static String getYearMonthDay(Date date) {
           return ymd.format(date);
    }
    
    /**
     * 获取yyyy/MM/dd的Date
     * 参数：日期
     * @return Date 日期
     */   
    public static Date getYMD(String ymd){
        Date date = null;
           try {
               date = sdf1.parse(ymd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 获取yyyy-MM-dd
     * 参数：日期
     * @return 天数
     */   
    public static String fromDateToString(Date date) {
           return sdf1.format(date);
    }
    
    /**
     * 解决get请求参数中文乱码问题 ，2017-09-12
     */
     public static String getDecodeParam(String param){
     	try {
     		param = new String(param.getBytes("utf-8"), "gb2312");
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}
     	return param;
     }
}
