package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class FsptParse implements IParse{
/***
 * 正方常规状态
 */
	public List<Courses> parseCourses(String html) {
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "Datagrid1" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	        List<Courses> list = new ArrayList<Courses>();
	        for(int i = 0; i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                Courses courses=null;
	                boolean flag=false;
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
	                    if(row.toPlainTextString().indexOf("学年学期")!=-1){
	                    	flag=true;
	                    	continue;
	                    }
	                    if(flag){
		                    TableColumn[] columns = row.getColumns(); 
		                    courses=new Courses();
		                    for (int k = 0; k < columns.length; k++) {  
		                    	Node columnNode=columns[k];
		                        String info = columnNode.toPlainTextString().trim(); 
		                       // System.out.println(info+"====="+k);
		                        if(k==0){
		                        	courses.setSchoolYear(info);
		                        }
		                        else if(k==1){
		                        	courses.setSemester(info);
		                        }
		                        else if(k==2){
		                        	courses.setCourseCode(info);
		                        }
		                        else if(k==3){
		                        	courses.setCoursesname(info);
		                        }
		                        else if(k==4){
		                        	courses.setType(info);
		                        }
		                        else if(k==6){
		                        	courses.setCredit(Double.parseDouble(info));
		                        }
		                        else if(k==7){
		                        	//绩点？？什么鬼
		                        }
		                        else if(k==8){
		                        	courses.setScore(info);
		                        }
		                    }//end for k
		                    list.add(courses); 
	                    }
	                }// end for j  
	            }  
	        }
	        return list;
	}
	
	public List<TimeTable> parseTimeTables(String html) {
		Parser parser = new Parser();  
        try {
			parser.setInputHTML(html);
			 parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}  
         
       List<TimeTable> list=new ArrayList<TimeTable>();
       NodeFilter filter = new NodeClassFilter(TableTag.class); 
       NodeFilter idFilter=new HasAttributeFilter("id","Table6");
       NodeFilter andFilter=new AndFilter(filter, idFilter);
        NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(andFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        for(int i = 0; i < nodeList.size(); i++){  
            if(nodeList.elementAt(i) instanceof TableTag){  
                TableTag tag = (TableTag) nodeList.elementAt(i); 
                TableRow[] rows = tag.getRows(); 
                for (int j = 1; j < rows.length; j++) {  
                    TableRow row = (TableRow) rows[j];  
                    TableColumn[] columns = row.getColumns();  
                    TimeTable timeTable=null;
                    for (int k = 0; k < columns.length; k++) {  
                    	Node columnNode=columns[k];
                        String info = columnNode.toHtml().trim();
                        if("".equals(info)||"&nbsp;".equals(info)){
                        	continue;
                        }
                    	int base=0;
                    	int baseStart=info.indexOf("(");
                    	while (baseStart!=-1) {
                    		timeTable=new TimeTable();
                    		int week=k;
                    		if(j==2||j==6||j==10){
                    			week--;
                    		}
                    		timeTable.setWeek(week+"");
							int name_s=info.indexOf(">",base);
							int name_e=info.indexOf("<br>",name_s+1);
							String name=info.substring(name_s+1,name_e);
							timeTable.setCourseName(name);
							int cyc_e=info.indexOf("<br>",name_e+1);
							String cyc=info.substring(name_e+4,cyc_e);
							int time_s=cyc.indexOf("(");
							int time_e=cyc.indexOf(")");
							String time=cyc.substring(time_s+1,time_e);
							if (time.indexOf(",")!=-1) {
								String[] timeArr=time.split(",");
								int timeMin=Integer.MAX_VALUE;
								int timeMax=Integer.MIN_VALUE;
								for (int l = 0; l < timeArr.length; l++) {
									int temp=Integer.parseInt(timeArr[l]);
									if (temp<timeMin) {
										timeMin=temp;
									}
									if (temp>timeMax) {
										timeMax=temp;
									}
								}
								timeTable.setTime(timeMin+"-"+timeMax);
							}
							cyc=cyc.substring(0,time_s);
							timeTable.setCycle(cyc);
							int teacher_e=info.indexOf("<br>",cyc_e+1);
							String teacher=info.substring(cyc_e+4,teacher_e);
							timeTable.setTeacher(teacher);
							int add_e=info.indexOf("<br>",teacher_e+1);
							String add=info.substring(teacher_e+4,add_e);
							int tiao_s=info.indexOf("<font color='red'>",add_e);
							if(tiao_s!=-1&&tiao_s-add_e<15){
								int tiao_end=info.indexOf("</font>",tiao_s);
								String tiao=info.substring(tiao_s+"<font color='red'>".length(),tiao_end);
								add=add+tiao;
							}
							timeTable.setAddress(add);
							base=info.indexOf("<br><br><",add_e);
							if(base!=-1){
								baseStart=info.indexOf("(",base);
								base+="<br><br><".length();
							}else{
								baseStart=-1;
							}
							list.add(timeTable.clone());
						}
                    }// end for k 
                   
                }// end for j  
            }  
        }
        return repeat(list);
	}
	
	 List<TimeTable> repeat(List<TimeTable> list){
		 for (int i = 0; i < list.size(); i++) {
				TimeTable table1=list.get(i);
				if(table1==null){
					continue;
				}
				for (int j = i+1; j < list.size(); j++) {
					TimeTable table2=list.get(j);
					if(table2==null){
						continue;
					}
					String type=isSame(table1, table2);
					if(type!=null){
						if("cycle".equals(type)){
							table1.setCycle(table1.getCycle()+","+table2.getCycle());
							list.set(i, table1);
							list.set(j, null);
						}
					}
				}
			}
		 Collection nuCon = new Vector(); 
		 nuCon.add(null); 
		 list.removeAll(nuCon);
		 for (int i = 0; i < list.size(); i++) {
				TimeTable table1=list.get(i);
				if(table1==null){
					continue;
				}
				for (int j = i+1; j < list.size(); j++) {
					TimeTable table2=list.get(j);
					if(table2==null){
						continue;
					}
					String type=isSame(table1, table2);
					if(type!=null){
						if(type.indexOf("-")!=-1){
							table1.setTime(type);
							list.set(i, table1);
							list.set(j, null);
						}
					}
				}
			}
		 list.removeAll(nuCon);
		 return list;
	 }
	 
	 String isSame(TimeTable table1,TimeTable table2){
		 if(table1.getCourseName().equalsIgnoreCase(table2.getCourseName())&&(table1.getSingleDouble()==null||"".equals(table1.getSingleDouble()))){
			 if(table2.getSingleDouble()==null||"".equals(table2.getSingleDouble())){//都不是单双周的课
				 boolean week=table1.getWeek().equalsIgnoreCase(table2.getWeek());
				 String time1=table1.getTime();
				 String time2=table2.getTime();
				 boolean time = time1.equalsIgnoreCase(time2);
				 boolean address=table1.getAddress().equalsIgnoreCase(table2.getAddress());
				 boolean teacher=table1.getTeacher().equalsIgnoreCase(table2.getTeacher());
				 if(week&&time&&address&&teacher){
					 return "cycle";
				 }
				 else if(week&&address&&teacher){
					 return isNext(time1, time2);
					
				}
			 }
		 }
		 return null;
	 }

	String isNext(String time1, String time2) {
		String[] arr = time1.split("-");
		int[] arrint1=new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int time_n = Integer.parseInt(arr[i]);
			arrint1[i]=time_n;
		}
		String[] arr2 = time2.split("-");
		int[] arrint2=new int[arr2.length];
		for (int i = 0; i < arr2.length; i++) {
			int time_n = Integer.parseInt(arr2[i]);
			arrint2[i]=time_n;
		}
		if(arrint1.length==2&&arrint2.length==2){
			if(arrint1[0]>arrint2[0]){
				if(arrint1[0]-arrint2[1]==1){
					return arrint2[0]+"-"+arrint1[1];
				}
			}else{
				if(arrint2[0]-arrint1[1]==1){
					return arrint1[0]+"-"+arrint2[1];
				}
			}
		}
		return null;
	}
	
	 
	
	/*public static void main(String[] args) {
		FsptParse ypcParse = new FsptParse();
		String html=readTxtFile("C:/Users/Administrator/Desktop/temp/temp/grade.html");
		List<TimeTable> list = ypcParse.parseTimeTables(html);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	
	public static String readFile(String path) {
		StringBuffer stringBuffer=new StringBuffer();
        try {
            Scanner in = new Scanner(new File(path));
            while (in.hasNextLine()) {
                String str = in.nextLine();
                stringBuffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
	
	 public static String readTxtFile(String filePath){
		 StringBuffer stringBuffer=new StringBuffer();
	        try {
	                String encoding="utf-8";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        stringBuffer.append("\r\n"+lineTxt);
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
	        return stringBuffer.toString();
	 }*/
	

}
