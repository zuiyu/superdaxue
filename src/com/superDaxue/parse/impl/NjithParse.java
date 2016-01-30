package com.superDaxue.parse.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class NjithParse implements IParse {

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
		                        }
		                        else if(k==8){
		                        	courses.setScore(info);
		                        }
		                        else if(k==10||k==11){
		                        	try {
		                        		info=info.replace("&nbsp;", "");
										Double score=Double.parseDouble(info);
										Double old=Double.parseDouble(courses.getScore());
										if(score>old){
											courses.setScore(score+"");
										}
									} catch (Exception e) {
										
									}
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
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "DBGrid" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        List<TimeTable> list = new ArrayList<TimeTable>();
	        for(int i = 0; i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                TimeTable timeTable=new TimeTable();
	                for (int j = 1; j < rows.length; j++) {  
	                	TableRow row = (TableRow) rows[j];  
	                	TableColumn[] columns = row.getColumns(); 
	                	timeTable=new TimeTable();
	                	String[] weekTime = null ;
	                    for (int k = 0; k < columns.length; k++) {  
	                    	Node columnNode=columns[k];
	                        String info = columnNode.toPlainTextString().trim();
	                        switch (k) {
							case 0:
								int y_start=info.indexOf("(");
								int y_end=info.indexOf(")");
								if(y_start!=-1&&y_end!=-1){
									String year=info.substring(y_start+1,y_end);
									String[] yearArr=year.split("-");
									if(yearArr.length==3){
										timeTable.setSchoolyear(yearArr[0]+"-"+yearArr[1]);
										timeTable.setSemester(yearArr[2]);
									}
								}
								break;
							case 1:
								timeTable.setCourseName(info);	
								break;
							case 2:
								timeTable.setType(info);	
								break;
							case 4:
								timeTable.setTeacher(info);	
								break;
							case 5:
								timeTable.setCredit(Double.parseDouble(info));	
								break;
							case 7:
								String timeinfo=columnNode.getText().trim();
								int start=timeinfo.indexOf("title=\"");
								if(start!=-1){
									timeinfo=timeinfo.substring(start+"title=\"".length(),timeinfo.length()-1);
									weekTime=timeinfo.split(";");
								}else {
									weekTime=info.split(";");
								}
									
								break;
							case 8:
								String[] arr=info.split(";");
								int m=0;
								for (int l = 0; l < weekTime.length; l++) {
									String item=weekTime[l];
									int w_start=item.indexOf("周");
									if(w_start!=-1){
										timeTable.setWeek(item.substring(w_start,w_start+2));
										String week=timeTable.getWeek();
										int t_start=item.indexOf(week+"第");
										int t_end=item.indexOf("节{第");
										String time=item.substring(t_start+3,t_end);
										String[] timeArr=time.split(",");
										timeTable.setTime(timeArr[0]+"-"+timeArr[timeArr.length-1]);
										int c_end=item.indexOf("周",t_end);
										timeTable.setCycle(item.substring(t_end+3,c_end));
										int d_start=item.indexOf("|",c_end);
										if(d_start!=-1){
											timeTable.setSingleDouble(item.substring(d_start+1,d_start+2));
										}
										if(m>arr.length){
											m=arr.length-1;
										}
										timeTable.setAddress(arr[m]);
										list.add(timeTable.clone());
										m++;
									}
									
								}
							//	break;
							default:
								break;
						}
	                }
	            }
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
}
