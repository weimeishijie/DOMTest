package com.mingjiang.dome;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * 利用DOM对xml文件解析
 */
public class DOMTests {

    /**
     * 获取DocumentBuilder方法
     * @return DocumentBuilder
     */
    public DocumentBuilder getDocumentBuilder(){
        //创建DocumentBuilderFactory对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //创建DocumentBuilder对象
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return db;
    }

    /**
     * 解析xml文件方法
     */
    public void xmlParser(){
        try {
            //通过DocumentBuilder的parse方法将文件加载到当前项目下
            Document document = getDocumentBuilder().parse("books.xml");
            //获取所有的book节点
            NodeList bookList = document.getElementsByTagName("book");
            //遍历所有的book节点
            for(int i=0;i<bookList.getLength();i++){
//                System.out.println("============================遍历开始=========================");
                //获取每个节点book
                Node book = bookList.item(i);
                //获取节点book中所有的属性
                NamedNodeMap attrs = book.getAttributes();
//                System.out.println("拿到了节点，总共："+attrs.getLength());
                //便利节点中所有的属性
                for(int j=0;j<attrs.getLength();j++){
                    //获取每一个属性名
                    System.out.println("属性的名字："+attrs.item(j).getNodeName());
                    //获取每一个属性值
                    System.out.println("属性的值:"+attrs.item(j).getNodeValue());
                }
                //获取节点中的子节点
                NodeList childNodes = book.getChildNodes();
                for(int k=0;k<childNodes.getLength();k++){
                    if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
                        System.out.println(childNodes.item(k).getNodeName());
                        System.out.println(childNodes.item(k).getFirstChild().getTextContent());
                    }
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成xml文档
     */
    public void createXML(){
        //获取DocumentBuilder对象
        DocumentBuilder db = getDocumentBuilder();
        //创建一个Document对象(树)
        Document document = db.newDocument();
        //Standalone是说明有没有dtd和schema相关的资料，如果为true则说明没有，为false则说明有（默认为false）
        document.setXmlStandalone(true);
        //创建一个根节点
        Element bookStore = document.createElement("bookStore");
        //在bookStore中添加子节点book，首先创建节点book
        Element book = document.createElement("book");
        //在book中添加子节点name，创建节点name
        Element name = document.createElement("name");
        //在name节点下添加文本内容
        name.setTextContent("小王子");
        //将name节点添加到book节点下
        book.appendChild(name);
        //在book节点上添加属性
        book.setAttribute("id", "1");
        //将book节点添加到bookStore节点中
        bookStore.appendChild(book);
        //将跟节点添加到Document对象中（树）
        document.appendChild(bookStore);


        //将现有的的document树转换成xml文件，首先创建一个TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            //创建一个Transformer对象
            Transformer tf = tff.newTransformer();
            //利用Transformer对象提供的setOutputProperty方法对输出的xml文件格式化
            tf.setOutputProperty(OutputKeys.INDENT,"yes");
            //利用Transformer提供的方法transform将要生成的xml文件资源注入，和输出方式路径注入
            tf.transform(new DOMSource(document),new StreamResult(new File("book1.xml")));

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }  catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 程序的入口
     * @param args
     */
    public static void main(String[] args){
        //创建DOMTest对象
        DOMTests test = new DOMTests();
        //调用解析xml文件方法
//        test.xmlParser();
        test.createXML();
    }
}
