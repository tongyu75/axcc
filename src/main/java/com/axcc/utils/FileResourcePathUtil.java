package com.axcc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 获取上传资源的路径(如：头像图片存放位置、会员二维码)
 * @ClassName: FileResourcePathUtil
 * @date: 2018年10月22日
 * @description:
 */
public class FileResourcePathUtil {
    //用于保存文件资源存储路径配置文件里的属性名
    public static List<String> propertyNameList = new ArrayList<String>();

    //用于保存文件资源存储路径配置文件里的属性值
    public static Map<String, String> propertyValueMap = new HashMap<String, String>();

    // 读取配置文件,初始化各种文件资源的存储路径
    static {
        InputStream in = FileResourcePathUtil.class.getResourceAsStream("/file_resource_path.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取配置文件中所有的属性名称
        Set<String> propertyNameSet  = prop.stringPropertyNames();
        propertyNameList.addAll(propertyNameSet);

        //循环获取每个属性对应的属性值
        for(String propertyName : propertyNameList){
            propertyValueMap.put(propertyName, prop.getProperty(propertyName));
        }
    }

    public static void main(String[] args) {
        System.out.println(propertyNameList);
        System.out.println(propertyValueMap);
    }
}
