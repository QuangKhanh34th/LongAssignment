package Lists;

import DTO.Course;
import DTO.Learner;

import java.util.ArrayList;

import static UI.Main.confirmChoice;

public class LearnerList extends ArrayList<Learner> {


    //Constructor that generate a list with sample Learner objects when status == true
    public LearnerList(boolean sampleStatus) {
        if (sampleStatus) {
            this.add(new Learner(30000, "Light Khanh", "02/04/2005"));
            this.add(new Learner(30001, "Hoang Wind", "07/12/2005"));
            this.add(new Learner(30002, "Thien Gold", "29/05/2005"));
            this.add(new Learner(30003, "Duc Sea", "08/06/2005"));
            this.add(new Learner(30004, "Anh Nguyen", "19/10/2005"));
            this.add(new Learner (30005, "Nguyen Sulfur", "19/10/2005"));
        }
    }

    public Learner getByID(int LearnerID) {
        for (Learner learnerSearch : this) {
            if (learnerSearch.getLearnerID() == LearnerID) {
                return learnerSearch;
            }
        }
        return null;
    }

    public String getAllLearnerNames() {
        StringBuilder sb = new StringBuilder();

        for (Learner learner : this) {
            if (learner != null)
                sb.append(learner.getName() + ", ");
        }

        String output= sb.toString();
        if (output.isEmpty()) {
            return "null";
        }
        output = output.substring(0,output.length()-2);
        return output;
    }

    // take LearnerID for "which learner need to be assigned?", course for "assign what course?"
    public boolean assignCoursetoLearner(int LearnerID, Course course) {
        boolean status=false;


        // search for unassigned learner in original list
        for (Learner learnerSearch : this) {
            // found the learner
            if (learnerSearch.getLearnerID() == LearnerID) {
                // check if they have already been assigned to any course
                if (learnerSearch.getCourse() != null) {
                    if (confirmChoice("This learner has already been assigned to a course." +
                            "\nDo you want to re-assign them to the selected course? ")) {
                        // handle assign to the same course twice
                        Learner existChecker = course.getLearners().getByID(LearnerID);
                        if (existChecker != null) {
                            course.getLearners().remove(course.getLearners().getByID(LearnerID));
                        }

                        // remove the learner from their current Course's LearnerList
                        learnerSearch.getCourse().getLearners().remove(learnerSearch);

                        // assign the learner to new course
                        status = learnerSearch.setCourse(course);
                    } else {
                        status = false;
                    }
                } else {
                    //Haven't been assigned to any course before
                    status = learnerSearch.setCourse(course);
                }
            }
        }

        return status;
    }

    public void displayAll() {
        if (this.isEmpty()) {
            System.out.println("Learner list is empty");
            return;
        }

        System.out.println("==LEARNER LIST===");
        for (Learner learner : this) {
            System.out.println(learner);
        }
    }



    //Internal use for Learner's setCourse method
    /* add new Learner to list (course's learnerList) while checking course capacity
     * (set at 3 for method presentation purpose)  */
    @Deprecated
    public void addLearnerToCourse(Learner input) throws IllegalArgumentException{
        if (this.size() >= 3) {
            throw new IllegalArgumentException("Cannot assign learner to this course\n" +
                    "Reason: Course capacity is at max [" + (this.size() + " learner(s)]"));
        }
        this.add(input);
    }

    //Internal use for CourseList's deleteCourse method
    @Deprecated
    public void unassignAll() {
        for (Learner l : this) {
            l.setCourse(null);
        }
    }
}
