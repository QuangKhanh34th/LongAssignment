package DTO;

import Lists.LearnerList;
import Lists.TopicList;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.*;

public class Course {

    // code or ID, name, type (online/offline), title, begin date, end
    // date, tuition fee, etc…. Course ID is unique
    // a course will have a list of topics covered in that course
    // a course will have a list of learners who have enrolled in the course
    public static int idSeed = 20000;
    private int courseID = idSeed;
    private String name;
    private final String[] typeList = {"online", "offline"};
    private String type;
    private String beginDate;
    private String endDate;
    private int tuitionFee;
    //important ALWAYS CHECK FOR ERROR WHEN INSERTING TOPICS INTO COURSE
    private TopicList topics;
    private double gpa;
    private LearnerList learners;


    //Constructors

    //allocate memory to lists upon creating a new course
    public Course() {
        this.topics = new TopicList();
        this.learners = new LearnerList(false);
        idSeed++;
    }

    private boolean isValidType(String type) {
        for (String typeCheck : typeList) {
            if (type.equalsIgnoreCase(typeCheck)) return true;
        }
        return false;
    }

    //in case of manual course creation, check for correct type
    public Course(String name, String type, String beginDate, String endDate, int tuitionFee) throws IllegalArgumentException{
        this.name = name;
        if (isValidType(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'offline' or 'online'");
        }
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tuitionFee = tuitionFee;
        this.topics = new TopicList();
        this.learners = new LearnerList(false);
        idSeed++;
    }

    //for file reading purpose
    public Course(int courseID, String name, String type, String beginDate, String endDate, TopicList topics, int tuitionFee) throws IllegalArgumentException{
        this.courseID = courseID;
        this.name = name;
        if (isValidType(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'offline' or 'online'");
        }
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.topics = topics;
        this.tuitionFee = tuitionFee;
        this.learners = new LearnerList(false);
        idSeed++;
    }


    //Getters and Setters

    public static int getIdSeed() {
        return idSeed;
    }

    public static void setIdSeed(int idSeed) {
        Course.idSeed = idSeed;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTypeList() {
        return typeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws IllegalArgumentException{
        if (isValidType(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'offline' or 'online'");
        }
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(int tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getTopicsString() {
        return topics.getTopicNames();
    }

    public TopicList getTopics() {
        return this.topics;
    }

    public LearnerList getLearners() {
        return learners;
    }

    public void setLearners(LearnerList learners) {
        this.learners = learners;
    }



    //Methods
    @Override
    public String toString() {
        return "CourseID: " + courseID + "\n" +
                "Course Name: " + name + "\n" +
                "Course type: " + type + "\n" +
                "Begin date (DD/MM/YYYY): " + beginDate + "\n" +
                "End date (DD/MM/YYYY): " + endDate + "\n" +
                "Topics: " + topics.getTopicNames() + "\n" +
                "Tuition fee (USD): " + tuitionFee;
    }

    public String toCSVFormat() {
        return courseID +"," + name +"," + type +"," + beginDate +"," + endDate +"," + topics.getTopicNamesforReading() +"," + tuitionFee;
    }

    //same as create/update function in Topic.java
    public void create() {
        Scanner sc = new Scanner(System.in);
        boolean InvalidInput;

        //Name
        do {
            InvalidInput = false;
            System.out.print("Enter Course's title: ");
            String name = sc.nextLine();
            if (name.trim().replaceAll(" +", " ").isEmpty()) {
                System.out.println("Error: Title cannot be empty, please try again");
                InvalidInput = true;
            } else {
                this.name = name;
            }
        } while (InvalidInput);


        //Type
        do {
            System.out.println();
            InvalidInput = false;
            try {
                int counter = 1;
                for (String type : this.typeList) {
                    System.out.println(counter + ". " + type);
                    counter++;
                }
                System.out.print("Select Course's type: ");
                int typeChoice = sc.nextInt() - 1;
                this.type = typeList[typeChoice];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Your input doesn't match any type in the list, please try again");
                sc = new Scanner(System.in);
                InvalidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);


        //beginDate
        sc = new Scanner(System.in);
        /*Build a new formatter which only accept day and month
         * because the default DateTimeFormatter doesn't accept missing field */
        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
        String dateInput;
        do {
            InvalidInput = false;
            dateInput = null;
            try {
                System.out.println();
                System.out.print("Enter the begin date of this course (DD/MM/YYYY): ");
                dateInput = sc.nextLine();
                LocalDate.parse(dateInput, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Your input isn't a valid date format (DD/MM/YYYY), please try again\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
        this.beginDate = dateInput;


        //endDate
        do {
            InvalidInput = false;
            dateInput = null;
            try {
                System.out.println();
                System.out.print("Enter the end date of this course (DD/MM/YYYY): ");
                dateInput = sc.nextLine();
                LocalDate.parse(dateInput, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Your input isn't a valid date format (DD/MM/YYYY), please try again\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
        this.endDate = dateInput;


        //tuitionFee
        System.out.println();
        sc = new Scanner(System.in);
        int tuitionFee=0;
        do {
            InvalidInput = false;
            try {
                System.out.print("Enter Course's Tuition fee: ");
                tuitionFee = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
        this.tuitionFee = tuitionFee;
    }

    public void update() {
        Scanner sc = new Scanner(System.in);
        boolean InvalidInput;

        //Name
        InvalidInput = false;
        System.out.print("Enter Course's title (leave blank to skip): ");
        String name = sc.nextLine();
        if (!name.trim().replaceAll(" +", " ").isEmpty()) {
            this.name = name;
        }



        //Type
        do {
            System.out.println();
            InvalidInput = false;
            try {
                int counter = 1;
                for (String type : this.typeList) {
                    System.out.println(counter + ". " + type);
                    counter++;
                }
                System.out.print("Select Course's type (enter 0 to skip): ");
                int typeChoice = sc.nextInt() - 1;
                if (typeChoice == -1) break;
                this.type = typeList[typeChoice];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Your input doesn't match any type in the list, please try again");
                sc = new Scanner(System.in);
                InvalidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);


        //beginDate
        sc = new Scanner(System.in);
        /*Build a new formatter which only accept day and month
         * because the default DateTimeFormatter doesn't accept missing field */
        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
        String dateInput;
        do {
            InvalidInput = false;
            dateInput = null;
            try {
                System.out.println();
                System.out.print("Enter the begin date of this course (DD/MM/YYYY) (leave blank to skip): ");
                dateInput = sc.nextLine();
                if (dateInput.trim().replaceAll(" +", " ").isEmpty()) break;
                LocalDate.parse(dateInput, dateFormat);
                this.beginDate = dateInput;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Your input isn't a valid date format (DD/MM/YYYY), please try again\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);


        //endDate
        do {
            InvalidInput = false;
            dateInput = null;
            try {
                System.out.println();
                System.out.print("Enter the end date of this course (DD/MM/YYYY) (leave blank to skip): ");
                dateInput = sc.nextLine();
                if (dateInput.trim().replaceAll(" +", " ").isEmpty()) break;
                LocalDate.parse(dateInput, dateFormat);
                this.endDate = dateInput;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Your input isn't a valid date format (DD/MM/YYYY), please try again\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);


        //tuitionFee
        System.out.println();
        sc = new Scanner(System.in);
        int tuitionFee=0;
        do {
            InvalidInput = false;
            try {
                System.out.print("Enter Course's Tuition fee (press 0 to skip): ");
                tuitionFee = sc.nextInt();
                if (tuitionFee ==0) break;
                this.tuitionFee = tuitionFee;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
    }


    //add Topic to course, require a general topic list (database)
    public boolean addTopic(TopicList topics) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        boolean InvalidInput;

        //using TopicList's clone for displayNinsert purpose so the original list won't be affected
        TopicList clone = new TopicList();
        clone.addAll(topics);

        //remove topics that is already in this course
        clone.removeAll(this.topics);


        try {
            Topic errorCatch = topics.get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Cannot run this function because Topic list is empty");
            return false;
        }

        //Insert topic into course until the user press 0
        Topic tInput;
        do {
            do {
                System.out.println();
                tInput = null;
                InvalidInput = false;
                try {
                    clone.sortByID();
                    clone.displayAll();
                    System.out.print("Enter topicID (press 0 to stop): ");
                    input = sc.nextInt();
                    tInput = clone.getByID(input);
                    if (tInput == null && input != 0) throw new NullPointerException();

                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input, please enter a number");
                    sc = new Scanner(System.in);
                    InvalidInput = true;
                } catch (NullPointerException e) {
                    System.out.println("Error: Incorrect topicID or topic does not exist");
                    sc = new Scanner(System.in);
                    InvalidInput = true;
                }


            } while (InvalidInput);
            this.topics.add(tInput);

            //delete the added topic from clone list to avoid duplicate topic insertion to course
            clone.deleteTopicWithoutMessage(input);
        } while (input != 0);
        return true;
    }

    //remove topic from course using its own topic list
    public boolean removeTopic() {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        boolean InvalidInput;


        TopicList clone = new TopicList();
        clone.addAll(this.topics);
        //remove null object in list for sorting
        clone.removeAll(Collections.singleton(null));
        try {
            Topic errorCatch = this.topics.get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Cannot run this function because Topic list is empty");
            return false;
        }

        //Delete topic from course until the user press 0
        Topic tInput;
        do {
            do {
                System.out.println();
                tInput = null;
                InvalidInput = false;
                try {
                    clone.sortByID();
                    clone.displayAll();
                    System.out.print("Enter topicID (press 0 to stop): ");
                    input = sc.nextInt();
                    tInput = clone.getByID(input);
                    if (tInput == null && input != 0) throw new NullPointerException();

                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input, please enter a number");
                    sc = new Scanner(System.in);
                    InvalidInput = true;
                } catch (NullPointerException e) {
                    System.out.println("Error: Incorrect topicID or topic does not exist");
                    sc = new Scanner(System.in);
                    InvalidInput = true;
                }


            } while (InvalidInput);
            this.topics.remove(tInput);

            //delete the added topic from clone list to avoid duplicate topic deletion to course
            clone.deleteTopicWithoutMessage(input);


        } while (input !=0);
        return true;
    }

}
