package com.whut.resume.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/25 15:32
 */
public class classUtil {
    public static Map<String,String> getAttributesAndValue(Object o) throws IllegalAccessException{
        Map<String,String> result=new HashMap<>();
        for(Field field:o.getClass().getDeclaredFields()){
            //获取原本的访问限权
            boolean flag=field.isAccessible();
            field.setAccessible(true);
            result.put(field.getName(),field.get(o).toString());
            //读取后恢复原本访问限权
            field.setAccessible(flag);
        }
        return result;
    }

    public static List<String> getFields(Object o){
        List<String> fields=new ArrayList<>();
        for(Field field:o.getClass().getDeclaredFields()){
            fields.add(field.getName());
        }
        return fields;
    }
}
