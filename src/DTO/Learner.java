package DTO;

public class Learner  {

    //code or ID, name, date of birth, score, course, etc…
    private static int idSeed = 30000;
    private int learnerID;
    private String name;
    private String dob;
    private int[] score;
    private float gpa;
    private Course course;

    //Constructors
    public Learner() {
        idSeed++;
    }

    public Learner(int learnerID, String name, String dob) {
        this.learnerID = learnerID;
        this.name = name;
        this.dob = dob;
        idSeed++;
    }


    //Getters and Setters

    public static int getIdSeed() {
        return idSeed;
    }

    public static void setIdSeed(int idSeed) {
        Learner.idSeed = idSeed;
    }

    public int getLearnerID() {
        return learnerID;
    }

    public void setLearnerID(int learnerID) {
        this.learnerID = learnerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Course getCourse() {
        return course;
    }

    public boolean setCourse(Course course) {
        try {
            // add learner to course's learner list (won't add and throw exception if the course already at max capacity
            // or setCourse() is used for unassignment purpose)
            course.getLearners().addLearnerToCourse(this);
            //assign course to learner (this won't run if the line above throw exception)
            this.course = course;
        } catch (IllegalArgumentException e) { //handling course at max capacity
            System.out.println(e.getMessage());
            return false;
        } catch (NullPointerException e) { //for unassignment
            this.course = null;
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        //Handling course has not been assigned yet
        try {
            return String.format("LearnerID: %-6d | Name: %-15s | DOB: %-10s | Course attended: %-20s", learnerID, name, dob, course.getName());
        } catch (NullPointerException e) {
            return String.format("LearnerID: %-6d | Name: %-15s | DOB: %-10s | Course attended: %-20s", learnerID, name, dob, "null");
        }
    }



}
