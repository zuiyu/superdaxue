package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class ParseTest {
	public static void main(String[] args) {
//sanquan 成绩可以
	IParse ypcParse = new FjnuParse();
	//String html=readTxtFile("C:/Users/Administrator/Desktop/temp/temp/grade.html");
	String html=readTxtFile("C:/Users/Administrator/Desktop/temp/temp/timetable.html");
	System.out.println(html.length());
	//List<Courses> list = ypcParse.parseCourses(html);
	List<TimeTable> list = ypcParse.parseTimeTables(html);
	for (int i = 0; i < list.size(); i++) {
		System.out.println(list.get(i));
	}
}


public static String readFile(String path) {
	StringBuffer stringBuffer=new StringBuffer();
    try {
        Scanner in = new Scanner(new File(path),"utf-8");
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
                String encoding="gb2312";
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
 }
}
