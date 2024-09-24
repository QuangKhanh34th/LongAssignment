package Comparators;

import DTO.Course;

import java.util.Comparator;

public class CourseIDComparator implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        if(o1.getCourseID()> o2.getCourseID()) return 1;
        else if(o1.getCourseID()< o2.getCourseID()) return -1;
        return 0;
    }
}
