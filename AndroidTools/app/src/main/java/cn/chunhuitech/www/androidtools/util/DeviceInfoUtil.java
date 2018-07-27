package cn.chunhuitech.www.androidtools.util;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Created by hechengjin on 18-7-13.
 */

public class DeviceInfoUtil {
    public static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板： " + Build.BOARD);
        sb.append(
                "\n系统启动程序版本号： " + Build.BOOTLOADER);
        sb.append(
                "\n系统定制商： " + Build.BRAND);
        sb.append(
                "\ncpu指令集： " + Build.CPU_ABI);
        sb.append(
                "\ncpu指令集2 " + Build.CPU_ABI2);
        sb.append(
                "\n设置参数： " + Build.DEVICE);
        sb.append(
                "\n显示屏参数：" + Build.DISPLAY);
        sb.append("\n无线电固件版本：" + Build.getRadioVersion());
        sb.append("\n硬件识别码： " + Build.FINGERPRINT);
        sb.append("\n硬件名称： " + Build.HARDWARE);
        sb.append("\nHOST: " + Build.HOST);
        sb.append("\nBuild.ID);" + Build.ID);
        sb.append("\n硬件制造商： " + Build.MANUFACTURER);
        sb.append("\n版本： " + Build.MODEL);
        sb.append("\n硬件序列号： " + Build.SERIAL);
        sb.append("\n手机制造商： " + Build.PRODUCT);
        sb.append("\n  描述Build的标签： " + Build.TAGS);
        sb.append("\nTIME: " + Build.TIME);
        sb.append("\nbuilder类型" + Build.TYPE);
        sb.append("\nUSER: " + Build.USER);
        return sb.toString();
    }

    /**
     * 通过反射获取所有的字段信息
     *
     * @return
     */
    public static String getDeviceInfo2() {
        StringBuilder sbBuilder = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sbBuilder.append("\n" + field.getName() + ":" + field.get(null).toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sbBuilder.toString();
    }
}
