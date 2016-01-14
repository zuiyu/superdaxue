package com.superDaxue.tool;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class XmlManage {
	public String getParse(String school){
		String classname=null;
		try { 
			String path=getClass().getClassLoader().getResource("schoolparse.xml").getPath();
			File f = new File(path); 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			Document doc = builder.parse(f); 
			NodeList nl = doc.getElementsByTagName("school"); 
			if(nl.getLength()>0){
				String packagename = nl.item(0).getAttributes().getNamedItem("package").getNodeValue();
				NodeList nodeList = doc.getElementsByTagName(school); 
				if(nodeList.getLength()>0){
					classname=packagename+nodeList.item(0).getFirstChild().getNodeValue();
				}
			}
		} 
		catch (Exception e) { 
		e.printStackTrace(); 
		} 
		return classname;
	}
	
	public String getType(String school){
		String type=null;
		try { 
			String path=getClass().getClassLoader().getResource("schoolparse.xml").getPath();
			File f = new File(path); 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			Document doc = builder.parse(f); 
			NodeList nl = doc.getElementsByTagName("school"); 
			if(nl.getLength()>0){
				NodeList nodeList = doc.getElementsByTagName(school); 
				if(nodeList.getLength()>0){
					Node node= nodeList.item(0).getAttributes().getNamedItem("type");
					if(node!=null){
						type=node.getNodeValue();
					}
				}
			}
		} 
		catch (Exception e) { 
		e.printStackTrace(); 
		} 
		return type;
	}
	
	public static void main(String[] args) {
		System.out.println(new XmlManage().getType("kmmc"));
	}
}
