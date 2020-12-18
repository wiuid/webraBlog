package top.webra.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 所有时间相关的小工具
 * @author webra
 */
public class DateUtil {

    /**
     * 返回当前日期
     * @return
     */
    public static Date getNowDate(){
        return new Date();
    }

    /**
     * 1. 不传参 返回当前时间戳的 默认格式  yy-MM-dd hh:mm:ss
     * 2. 传一个时间参数 Date date,返回该时间的格式  yy-MM-dd hh:mm:ss
     * 3. 传两个参数时间date 和 要格式化的格式 format  返回该时间的自定义格式 format
     * @return
     */
    public static String getFormatDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }
    public static String getFormatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static String getFormatDate(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Long getCreateSite(Timestamp createTime){
        long time = createTime.getTime();
        Date date = new Date();
        long time1 = date.getTime();
        long day = (time1 - time) / 1000 / (60 * 60 * 24);
        return day;
    }


}
