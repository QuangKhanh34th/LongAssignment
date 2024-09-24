package Lists;

import Comparators.CourseBeginDateComparator;
import Comparators.CourseIDComparator;
import DTO.Course;
import DTO.Topic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static UI.Main.confirmChoice;

public class CourseList extends ArrayList<Course> {
    //displayAll used when modifying the list
    public void displayAllwithTopics() {
        System.out.println("===COURSE LIST===");
        if (!this.isEmpty()) {
            for (Course course : this) {
                System.out.printf("CourseID: %-5d | Course Name: %-30s | Topics: %-100s%n",
                        course.getCourseID(), course.getName(), course.getTopicsString());
            }
        }
        else {
            System.out.println("Course list is empty");
        }
    }


    //displayAll used for display and view Course's detail
    public void displayAllMenu() {
        int counter=1;
        System.out.println("===COURSE LIST===");
        if (!this.isEmpty()) {
            for (Course course : this) {
                System.out.println("["+counter+"] " + String.format("CourseID: %-5d | Course Name: %-30s | BeginDate: %-10s",
                        course.getCourseID(), course.getName(), course.getBeginDate()));
                counter++;
            }
        }
        else {
            System.out.println("Course list is empty");
        }
    }
    //used along with displayAllMenu
    public Course getByMenuChoice(int index) {
        return this.get(index);
    }

    public Course getByID(int courseID) {
        for (Course courseSearch : this) {
            if (courseSearch.getCourseID() == courseID) {
                return courseSearch;
            }
        }
        return null;
    }

    // search for course in the list using for-loop, then
    // modify the course's info if found
    public Course updateCourse(int query) {
        for (Course courseSearch : this) {
            if (courseSearch.getCourseID() == query) {
                courseSearch.update();
                return courseSearch;
            }
        }
        return null;
    }

    // same as updateCourse, but for adding topic in to course
    public Course addTopic(int query, TopicList topicList) {
        for (Course courseSearch : this) {
            if (courseSearch.getCourseID() == query) {
                courseSearch.addTopic(topicList);
                return courseSearch;
            }
        }
        return null;
    }

    // same as updateCourse, but for removing topic in to course
    public Course removeTopic(int query) {
        for (Course courseSearch : this) {
            if (courseSearch.getCourseID() == query) {
                if (courseSearch.removeTopic()) {
                    return courseSearch;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    // delete course with confirmation message, also unenroll all learner
    // from the deleted course
    public boolean deleteCourse(int courseID) {
        for (Course courseSearch : this) {
            if (courseSearch.getCourseID() == courseID) {
                if (confirmChoice("Course found, do you really want to delete [" + courseSearch.getName() + "] ?\n" +
                        "Warning: Doing this will result in the unenrollment of all learners from the deleted course")) {

                    courseSearch.getLearners().unassignAll();
                    courseSearch.getLearners().removeAll(courseSearch.getLearners());
                    this.remove(courseSearch);
                    // return true no matter the user's choice, indicate a successful function run
                    // without error
                    return true;
                } else {
                    return true;
                }
            }
        }
        return false; //unable to find the course
    }

    public void sortByBeginDate() {
        Collections.sort(this, new CourseBeginDateComparator());
    }

    public void sortByID() {
        Collections.sort(this, new CourseIDComparator());
    }

    public void updateTopicID() {
        CourseList clone = this;
        clone.sortByID();
        int newIDSeed = clone.get(clone.size() - 1).getCourseID()+1;
        this.get(this.size() - 1).idSeed = newIDSeed;
    }

    //need base topic list to convert name to topic object
    public CourseList readFile(String filename, TopicList tBase) throws IOException {
        if (tBase.isEmpty()) {
            System.out.println("Error reading Course data: Cannot invoke this function because Topic List is empty");
            return null;
        }


        File fInput = new File(filename);
        FileReader fr;
        try {
            fr = new FileReader(fInput);
        } catch (FileNotFoundException e) {
            return null;
        }
        BufferedReader br = new BufferedReader(fr);
        CourseList output = new CourseList();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] cInfos = line.split(",");
            String[] tInfos = cInfos[5].split("\\|");

            int courseID = Integer.parseInt(cInfos[0]);
            String name = cInfos[1];
            String type = cInfos[2];
            String beginDate = cInfos[3];
            String endDate = cInfos[4];

            //TopicList reading
            TopicList outputTList = new TopicList();
            Topic outputTListElement;
            for (String tInfo : tInfos) {
                outputTListElement = tBase.getByExactName(tInfo); // will return null if this function can't get the name
                outputTList.add(outputTListElement);
            }
            if (outputTList.contains(null)) {
                System.out.println("Warning: Course [" + name +"] "+ "Topic List may missing some values because" +
                        " the database has been modified or no longer contain those entries");
                outputTList.removeAll(Collections.singleton(null)); // remove obsolete null elements as a result of
                                                                    // failure addition from above
            }

            int tuitionFee = Integer.parseInt(cInfos[6]);

            output.add(new Course(courseID, name, type, beginDate, endDate, outputTList, tuitionFee));
        }

        fr.close();
        br.close();
        return output;
    }

    public boolean writeFile(String filename) throws IOException {
        File fOutput = new File(filename);
        PrintWriter pw = new PrintWriter(fOutput);

        for (Course t : this) {
            pw.write(t.toCSVFormat()+"\n");
        }

        pw.close();

        return true;
    }
}
