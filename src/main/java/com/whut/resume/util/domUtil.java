package com.whut.resume.util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

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
            DOCUMENT.setXmlStandalone(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 将object的信息解析为一个以rootName为根节点的子元素
     * @param object
     * @param rootName
     * @return
     * @throws IllegalAccessException
     */
    public static Element parseInfo(Object object,String rootName) throws IllegalAccessException{
        Element root=DOCUMENT.createElement(rootName);
        Map<String,String> attrAndValue=classUtil.getAttributesAndValue(object);
        List<String> fields=classUtil.getFields(object);
        for(String field:fields){
            Element element=DOCUMENT.createElement(field);
            element.setTextContent(field+":"+attrAndValue.get(field));
            root.appendChild(element);
        }
        return root;
    }

    /***
     * 根据DOM树生成xml文件
     * @param target
     * @throws TransformerException
     */
    public static void generateXML(File target) throws TransformerException {
        TransformerFactory transFactory=TransformerFactory.newInstance();
        Transformer transformer=transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(DOCUMENT),new StreamResult(target));
    }

    /***
     * 根据其他临时DOM树生成xml文件
     * @param target
     * @throws TransformerException
     */
    public static void generateXML(Document temp,File target) throws TransformerException {
        TransformerFactory transFactory=TransformerFactory.newInstance();
        Transformer transformer=transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(temp),new StreamResult(target));
    }

    /***
     * 解析xml文件
     * @param fileName
     * @throws Exception
     */
    public static void loadXML(String fileName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOCUMENT=builder.parse(fileName);
    }

    /***
     * 通过标签名获取某个标签所有子节点的值并放入list中
     * @param tagName
     * @return
     */
    public static List<Object> loadObject(String tagName){
        List<Object> values=new ArrayList<>();
        NodeList list=DOCUMENT.getElementsByTagName(tagName);
        Node node=list.item(0);
        NodeList children=node.getChildNodes();
        for(int i=0;i<children.getLength();i++){
            //type==1的节点才是有效元素
            if(children.item(i).getNodeType()==1) {
                values.add(children.item(i).getTextContent());
            }
        }
        return values;
    }

    /***
     * 修改tagName标签下的子元素
     * @param object
     * @param tagName
     */
    public static void update(Object object,String tagName) throws IllegalAccessException{
        Map<String,String> attributesAndValue=classUtil.getAttributesAndValue(object);
        NodeList list=DOCUMENT.getElementsByTagName(tagName);
        Node node=list.item(0);
        NodeList children=node.getChildNodes();
        for(int i=0;i<children.getLength();i++){
            Node currentChild=children.item(i);
            //type==1的节点才是有效元素
            if(currentChild.getNodeType()==1) {
                String childrenTagName=currentChild.getNodeName();
                if(attributesAndValue.containsKey(childrenTagName)){
                    //更改属性
                    currentChild.setTextContent(attributesAndValue.get(childrenTagName));
                }
            }
        }
    }

    /***
     * 在tagName下增加元素，数据来自object
     * @param object
     * @param tagName
     */
    public static void add(Object object,String tagName) throws Exception{
        //刷新DOCUMENT
        domUtil.loadXML("src/main/resources/static/resume.xml");
        //找到tagName的元素
        NodeList list=DOCUMENT.getElementsByTagName(tagName);
        Node current=list.item(0);
        Map<String,String> attrAndValue=classUtil.getAttributesAndValue(object);
        List<String> fields=classUtil.getFields(object);
        for(String field:fields){
            Element element=DOCUMENT.createElement(field);
            element.setTextContent(field+":"+attrAndValue.get(field));
            current.appendChild(element);
        }
    }

    /***
     * 删除tagName下内容为tobeDeleted的元素
     * @param tobeDeleted
     * @param tagName
     */
    public static void delete(String tobeDeleted,String tagName) throws Exception{
        //刷新DOCUMENT
        domUtil.loadXML("src/main/resources/static/resume.xml");
        //找到标签名为tagName的所有元素
        NodeList list=DOCUMENT.getElementsByTagName(tagName);
        for(int i=0;i<list.getLength();i++){
            Node current=list.item(i);
            //type==1的节点才是有效元素
            if(current.getNodeType()==1) {
                if (current.getTextContent().equals(tobeDeleted)) {
                    Node parent = current.getParentNode();
                    parent.removeChild(current);
                }
            }
        }
    }

    /***
     * 输出特定的标签名范围中的内容
     * @param selected
     */
    public static void outputSelected(String[] selected) throws Exception{
        //生成临时的document存储新树
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document tempDocument = builder.newDocument();
        Element root=tempDocument.createElement("resume");
        //刷新DOCUMENT
        domUtil.loadXML("src/main/resources/static/resume.xml");
        for(String s:selected){
            NodeList list=DOCUMENT.getElementsByTagName(s);
            for(int i=0;i<list.getLength();i++){
                Node current=list.item(i);
                if(current.getNodeType()==1) {
                    //因root和current由不同document生成，二者不能作为父子
                    //首先克隆一个currentCopy,其父为DOCUMENT
                    Element currentCopy=(Element) current.cloneNode(true);
                    //现在tempDocument收养这个节点
                    tempDocument.adoptNode(currentCopy);
                    //收养后作为自己的孩子
                    root.appendChild(currentCopy);
                }
            }
        }
        tempDocument.appendChild(root);
        generateXML(tempDocument,new File("src/main/resources/static/resume.xml"));
    }
}
