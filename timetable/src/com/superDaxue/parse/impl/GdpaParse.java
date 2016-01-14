package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.sound.midi.MidiDevice.Info;

import net.sf.json.JSONArray;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;
import com.superDaxue.tool.DateTool;

public class GdpaParse implements IParse {
	/***
 *
 */
	public List<Courses> parseCourses(String html) {
		List<Courses> list = new ArrayList<Courses>();
		int year_end = html.indexOf("##end##");
		String year = html.substring(0, year_end);
		year=year.replace("\r\n", "");
		int start = html.indexOf("\"Values\": ");
		int end = html.indexOf("\"DataKeys\": ");
		if (start != -1 && end != -1) {
			String info = html.substring(start + "\"Values\": ".length(), end)
					.trim();
			info = info.substring(2, info.length() - 3);
			info=info.replaceAll("\"", "");
			info = info.replaceAll("[ ]", "");
			String[] arr = info.split("\\],\\[");
			for (int i = 0; i < arr.length; i++) {
				String[] itemArr = arr[i].split(",");
				Courses courses = new Courses();
				for (int j = 0; j < itemArr.length; j++) {
					String item=itemArr[j];
					switch (j) {
					case 0:
						String[] schoolyear=getSchoolyear(year, item); 
						courses.setSchoolYear(schoolyear[0]);
						courses.setSemester(schoolyear[1]);
						break;
					case 1:
						courses.setCoursesname(item);
						break;
					case 2:
						courses.setCredit(Double.parseDouble(item));
						break;
					case 3:
						courses.setScore(item);
						break;
					default:
						break;
					}
				}
				if(courses.getScore()==null){
					courses.setScore("");
				}
				list.add(courses);
			}
		}
		return list;
	}

	private String[] getSchoolyear(String year, String index) {
		String[] result = new String[2];
		int studentYear = Integer.parseInt(year);
		int studentIndext = Integer.parseInt(index);
		int xn = studentIndext / 2;
		int xq = studentIndext % 2;
		
		if (xq == 0) {
			result[0] = (studentYear + xn-1)+"-"+(studentYear + xn);
			result[1] = "2";
		} else {
			result[0] = (studentYear + xn)+"-"+(studentYear + xn+1);
			result[1] = "1";
		}
		return result;
	}

	public List<TimeTable> parseTimeTables(String html) {
		List<TimeTable> list = new ArrayList<TimeTable>();
		int start = html.indexOf("\"Values\": ");
		int end = html.indexOf("\"DataKeys\": ");
		if (start != -1 && end != -1) {
			String info = html.substring(start + "\"Values\": ".length(), end)
					.trim();
			info = info.substring(2, info.length() - 3);
			info=info.replaceAll("\"", "");
			info = info.replaceAll("[ ]", "");
			String[] arr = info.split("\\],\\[");
			for (int i = 0; i < arr.length; i++) {
				String[] itemArr = arr[i].split(",");
				TimeTable timeTable = new TimeTable();
				for (int j = 0; j < itemArr.length; j++) {
					String item=itemArr[j];
					switch (j) {
					case 0:
						timeTable.setCourseName(item);
						break;
					case 1:
						timeTable.setWeek(item.substring(0,2));
						String time=item.substring(2,item.length()-1);
						time=time.replace("、", "-");
						timeTable.setTime(time);
						break;
					case 2:
						timeTable.setAddress(item);
						break;
					case 3:
						timeTable.setTeacher(item);
						break;
					default:
						break;
					}
				}
				list.add(timeTable);
			}
		}
		return list;
	}
}
