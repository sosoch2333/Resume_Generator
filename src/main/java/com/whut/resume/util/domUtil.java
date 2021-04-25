package com.whut.resume.util;

import com.whut.resume.pojo.BasicInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.Map;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/25 15:11
 */
public class domUtil {
    public static Document DOCUMENT=null;
    static{
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOCUMENT = builder.newDocument();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Element parseInfo(Object object,String rootName) throws IllegalAccessException{
        Element root=DOCUMENT.createElement(rootName);
        Map<String,String> attrAndValue=classUtil.getAttributesAndValue(object);
        List<String> fields=classUtil.getFields(object);
        for(String field:fields){
            Element element=DOCUMENT.createElement(field);
            element.setTextContent(attrAndValue.get(field));
            root.appendChild(element);
        }
        return root;
    }
}
