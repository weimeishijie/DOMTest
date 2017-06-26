package com.mingjiang.test;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * 用DOM解析xml文件
 */
public class DOMTest {

    public static void main(String[] args){
        //创建DocumentBuilderFactory对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //通过DocumentBuilder对象的parse方法加载文件到当前项目下
            Document document = db.parse("web/WEB-INF/books.xml");

            //获取所有book节点的集合
            NodeList bookList = document.getElementsByTagName("book");
            //通过noteList的getLength()方法获取bookList的长度
            System.out.println("一共有"+bookList.getLength()+"本书");


            //遍历每个book节点在不知道xml内容的时候用一下方法：
            for(int i=0;i<bookList.getLength();i++){
                System.out.println("=================下面开始遍历第"+(i+1)+"本书的内容================================");

                //通过item(i)方法 获取每一个book节点，nodeList的索引值从0开始
                Node book = bookList.item(i);
                //获取book节点的所有属性集合
                NamedNodeMap attrs = book.getAttributes();
                System.out.println("第"+(i+1)+"本书共有"+attrs.getLength()+"个属性");
                //遍历book属性
                for(int j=0;j<attrs.getLength();j++){
                    //通过item(j)方法获取book节点的某一个属性值
                    Node attr = attrs.item(j);
                    //获取属性名
                    System.out.print("属性名："+attr.getNodeName()+"  ");
                    //获取属性值
                    System.out.println("--属性值："+attr.getNodeValue());
                }


                //解析book节点的子节点
                NodeList childNodes = book.getChildNodes();
                //遍历childNodes获取每个节点的节点名和节点值
                System.out.println("第"+(i+1)+"本书共有"+childNodes.getLength()+"个子节点");
                for(int k=0; k<childNodes.getLength();k++){
                    //区分text类型的node以及element类型的node
                    if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
                        //获取了element类型节点的节点名
                        System.out.print("第"+(k+1)+"个节点的节点名："+childNodes.item(k).getNodeName());
                        //获取了element类型的节点值
//                        System.out.println("--节点值是："+childNodes.item(k).getFirstChild().getNodeValue());
                        //另一种方法获取节点值
                        System.out.println("--节点值是："+childNodes.item(k).getTextContent());
                    }

                }

            }
            /**
            //遍历每个book节点 在知道了xml内部结构时遍历方法
            for(int i=0;i<bookList.getLength();i++){
                System.out.println("=================下面开始遍历第"+(i+1)+"本书的内容================================");

                //使用Element前提：已经知道book有且只能有一个id属性
                //将book节点强制类型转换，转换成Element类型
                Element book = (Element) bookList.item(i);
                //通过getAttribute("id")方法获取属性值
                String attrValue = book.getAttribute("id");
                System.out.println("id属性的值为："+attrValue);
            }
             */




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
