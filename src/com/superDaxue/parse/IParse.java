package com.superDaxue.parse;

import java.util.List;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;

public interface IParse {
	public List<Courses> parseCourses(String html);
	public List<TimeTable> parseTimeTables(String html);
}
