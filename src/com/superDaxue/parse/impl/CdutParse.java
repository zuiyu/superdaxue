package com.superDaxue.parse.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class CdutParse implements IParse {
	
	public List<Courses> parseCourses(String html) {
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	      //  NodeFilter filter = new NodeClassFilter(Div.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "class", "floatDiv10" );
	        NodeFilter attrFilter2=new HasAttributeFilter( "class", "floatDiv20" );
	        NodeFilter orfFilter=new OrFilter(attrFilter2, attrFilter);
	      //  NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(orfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	        List<Courses> list = new ArrayList<Courses>();
	        for (int i = 1; i < nodeList.size()/10; i++) {
	        	Courses courses=new Courses();
	        	for (int j = 0; j < 10; j++) {
	        		if(nodeList.elementAt(i*10+j) instanceof Div){  
		            	Div tag = (Div) nodeList.elementAt(i*10+j);
		            	String info=tag.toPlainTextString().trim();
		            	info=info.replaceAll("&nbsp;", "");
		            	switch (j) {
						case 0:
							String yearStart=info.substring(0,4);
							int year=Integer.parseInt(yearStart);
							courses.setSchoolYear(year+"-"+(year+1));
							courses.setSemester(info.substring(info.length()-1,info.length()));
							break;
						case 1:
							courses.setCourseCode(info);
							break;
						case 2:
							courses.setCoursesname(info);
							break;
						case 3:
							courses.setRemark(info);
							break;
						case 4:
							courses.setCredit(Double.parseDouble(info));
							break;
						case 5:
							courses.setScore(info);
							break;
						default:
							break;
						}
		            } 
	        		
				}
	        	list.add(courses);
				
			}
	     
	        return list;
	}

	public List<TimeTable> parseTimeTables(String html) {
	//	System.out.println(html);
		Parser parser = new Parser();  
       try {
			parser.setInputHTML(html);
			 parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}  
        
      List<TimeTable> list=new ArrayList<TimeTable>();
       NodeFilter filter = new NodeClassFilter(TableTag.class); 
       NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
       for(int i = 0; i < nodeList.size(); i++){  
           if(nodeList.elementAt(i) instanceof TableTag){  
               TableTag tag = (TableTag) nodeList.elementAt(i);  
               if(tag.getText().indexOf("table id=\"Table1\"")==-1){
               	continue;
               }
               TableRow[] rows = tag.getRows(); 
               for (int j = 0; j < rows.length; j++) {  
                   TableRow row = (TableRow) rows[j];  
                   TableColumn[] columns = row.getColumns();  
                   TimeTable timeTable=null;
                   for (int k = 0; k < columns.length; k++) {  
                   	Node columnNode=columns[k];
                       String info = columnNode.toPlainTextString().trim(); 
                      
                   }// end for k  
               }// end for j  
           }  
       }
       return list;
	}
}
