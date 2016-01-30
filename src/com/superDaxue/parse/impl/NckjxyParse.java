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

public class NckjxyParse implements IParse {
	/**
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
		NodeFilter attrFilter = new HasAttributeFilter("id", "grid_DXMainTable");
		NodeFilter andfFilter = new AndFilter(filter, attrFilter);
		NodeList nodeList = null;
		try {
			nodeList = parser.extractAllNodesThatMatch(andfFilter);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Courses> list = new ArrayList<Courses>();
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				TableRow[] rows = tag.getRows();
				Courses courses = null;
				String schoolyear = "";
				String semester = "";
				for (int j = 0; j < rows.length; j++) {
					TableRow row = (TableRow) rows[j];
					TableColumn[] columns = row.getColumns();
					int len = columns.length;
					courses = new Courses();
					for (int k = 0; k < len; k++) {
						Node columnNode = columns[k];
						String info = columnNode.toPlainTextString().trim();
						int temp = k;
						if (len == 13) {
							temp = temp + 4;
						} else if (len == 15) {
							temp = temp + 2;
						}
						if (temp >= 0) {
							if (info.equals("学年"))
								break;
							switch (temp) {
							case 2:
								schoolyear = info.substring(0, 4);
								schoolyear = schoolyear + "-"
										+ (Integer.parseInt(schoolyear) + 1);
								semester = info.substring(4, 5);
								break;
							}
						}
						courses.setSchoolYear(schoolyear);
						courses.setSemester(semester);
						switch (temp) {
						case 4:
							courses.setCourseCode(info);
							break;
						case 5:
							courses.setCoursesname(info);
							break;
						case 8:
							courses.setType(info);
							break;
						case 10:
							courses.setCredit(Double.parseDouble(info));
							break;
						case 12:
							courses.setScore(info);
					  		break;
						default:
							break;
						}
					}
					if (courses.getCoursesname() != null) {
						list.add(courses);
					}

				}// end for j
			}
		}
		return list;
	}

	public List<TimeTable> parseTimeTables(String html) {
		// System.out.println(html);
		Parser parser = new Parser();
		try {
			parser.setInputHTML(html);
			parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}

		List<TimeTable> list = new ArrayList<TimeTable>();
		NodeFilter filter = new NodeClassFilter(TableTag.class);
		NodeFilter attrFilter = new HasAttributeFilter("id", "TableLCRoomOccupy");
		NodeFilter andfFilter = new AndFilter(filter, attrFilter);
		NodeList nodeList = null;
		try {
			nodeList = parser.extractAllNodesThatMatch(andfFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				TableRow[] rows = tag.getRows();
				for (int j = 0; j < rows.length; j++) {
					TableRow row = (TableRow) rows[j];
					TableColumn[] columns = row.getColumns();
					TimeTable timeTable = null;
					for (int k = 0; k < columns.length; k++) {
						Node columnNode=columns[k];
                        String info = columnNode.toPlainTextString().trim();
                        String ex_info = columnNode.toHtml().trim();
                        if(info.length()>1&&info.indexOf("星期")==-1){
                        	String time=(2*j-1)+"-"+(2*j);
                            String week=k+"";
                        	timeTable=new TimeTable();
                        	timeTable.setTime(time);
                        	timeTable.setWeek(week);
                        	String nameStartStr="target=\"_blank\">";
                        	String nameEndStr="</A><br>";
                        	int nameStart=ex_info.indexOf(nameStartStr);
                        	int nameEnd=ex_info.indexOf(nameEndStr,nameStart);
                        	String name=ex_info.substring(nameStart+nameStartStr.length(),nameEnd);
                        	timeTable.setCourseName(name);
                        	int teaStart=ex_info.indexOf(nameStartStr,nameEnd);
                        	int teaEnd=ex_info.indexOf("</a><br>",teaStart);
                        	String teacher=ex_info.substring(teaStart+nameStartStr.length(), teaEnd);
                        	timeTable.setTeacher(teacher);
                        	int addStart=teaEnd+"</a><br>".length();
                        	int addEnd=ex_info.indexOf("<br>",addStart);
                        	String address=ex_info.substring(addStart,addEnd);
                        	address=address.replaceAll(" ", "");
                        	timeTable.setAddress(address);
                        	int cycStart=addEnd+4;
                        	int cycEnd=ex_info.indexOf("<br>",cycStart);
                        	String cycle=ex_info.substring(cycStart, cycEnd);
                        	int cyc_end=cycle.indexOf("周");
                        	timeTable.setCycle(cycle.substring(0,cyc_end));
                        	list.add(timeTable);
                        }
					}// end for k
				}// end for j
			}
		}
		return list;
	}

}
