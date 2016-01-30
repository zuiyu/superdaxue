package com.superDaxue.parse.impl;

import java.util.ArrayList;
import java.util.List;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class GdpaParse implements IParse {
	
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
				//System.out.println(arr[i]);
				String[] itemArr = arr[i].split("）,");
				TimeTable timeTable = new TimeTable();
				for (int j = 0; j < itemArr.length; j++) {
					String item=itemArr[j];
					switch (j) {
					case 0:
						int n_end=item.indexOf("（");
						String name=item.substring(0,n_end);
						timeTable.setCourseName(name);
						break;
					case 1:
						timeTable.setWeek(item.substring(0,2));
						String time=item.substring(2,5);
						time=time.replace("、", "-");
						timeTable.setTime(time);
						int a_start=item.indexOf(",");
						int t_end=item.indexOf(",",a_start+1);
						timeTable.setAddress(item.substring(a_start+1,t_end));
						timeTable.setTeacher(item.substring(t_end+1));
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
