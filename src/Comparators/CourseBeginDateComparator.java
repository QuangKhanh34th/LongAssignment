package Comparators;

import DTO.Course;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;

public class CourseBeginDateComparator implements Comparator<Course> {

    @Override
    public int compare(Course o1, Course o2) {
        //use the same date Formatter as when inputted a new Course
        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
        LocalDate d1 = LocalDate.parse(o1.getBeginDate(),dateFormat);
        LocalDate d2 = LocalDate.parse(o2.getBeginDate(),dateFormat);

        if (d1.isAfter(d2)) return 1;
        else if (d2.isAfter(d1)) return -1;
        return 0;
    }
}
