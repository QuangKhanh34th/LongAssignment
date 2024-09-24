package UI;

import DTO.Course;
import DTO.Learner;
import DTO.Topic;
import Lists.CourseList;
import Lists.LearnerList;
import Lists.TopicList;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // used for confirming various action
    public static boolean confirmChoice(String message) {
        Scanner sc = new Scanner(System.in);
        boolean invalidInput;
        String confirm ="";
        do {
            System.out.println();
            invalidInput = false;
            try {
                System.out.print(message + " (Y/N): ");
                confirm = sc.nextLine();
                //catch input that is not "y" or "n" (case-insensitive) and is more than 1 character
                if (confirm.matches("[^ynYN]+") || confirm.matches("[ynYN].+") ||
                        confirm.matches(".+[ynYN]") || confirm.matches(".+[ynYN].+") ||
                            confirm.isEmpty())
                {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input (not 'y/Y' or 'n/N'), please try again");
                sc = new Scanner(System.in);
                invalidInput = true;
            }
        } while (invalidInput);

        return confirm.equalsIgnoreCase("Y");
    }

    // used in choosing menu functions
    public static int validateMenuChoice() {
        Scanner sc;
        boolean InvalidInput;
        int choiceResult=0;
        do {
            sc = new Scanner(System.in);
            InvalidInput = false;
            try {
                System.out.print("Enter your choice: ");
                choiceResult = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;

            }
        } while (InvalidInput);
        return choiceResult;
    }

        public static void main(String[] args) throws IOException {
            //init Lists
            TopicList tList = new TopicList();
            tList = tList.readFile("TopicList.csv");
            if (tList == null) {
                System.out.println("Topic data not found, new storage initialized");
                tList = new TopicList();
            } else {
                System.out.println("Topic data imported successfully");
            }

            CourseList cList = new CourseList();
            cList = cList.readFile("CourseList.csv", tList);
            if (cList == null) {
                System.out.println("Course data not found, new storage initialized");
                cList = new CourseList();
            } else {
                System.out.println("Course data imported successfully");
            }

            LearnerList leList = new LearnerList(true);


            //Program UI
            Scanner sc = new Scanner(System.in);
            int menuChoice=0;
            boolean InvalidInput;
            String menuReturn = "Return to Main Menu?";

            do {
                System.out.println("\n===Courses Program Management===");
                System.out.println("1. Manage the Topics");
                System.out.println("2. Manage the Course");
                System.out.println("3. Manage the Learner");
                System.out.println("4. Search information");
                System.out.println("5. Save Topics, Courses and Learner to file");
                System.out.println("Others. Exit the application");

                menuChoice = validateMenuChoice();

                switch (menuChoice) {
                    // Manage the Topics
                    case 1:
                        boolean exitStatus = false;
                        int topicChoice=0;
                        do {
                            sc = new Scanner(System.in);
                            String topicReturn = "Return to Topic Manager?";

                            System.out.println("\n===TOPIC MANAGER===");
                            System.out.println("1. Add Topics to catalog");
                            System.out.println("2. Update Topic");
                            System.out.println("3. Delete Topic");
                            System.out.println("4. Display all Topics (sorted by title length)");
                            System.out.println("Others. Return to main menu");
                            topicChoice = validateMenuChoice();

                            switch (topicChoice) {
                                //Add topic
                                case 1: {
                                    do {
                                        System.out.println();
                                        Topic input = new Topic();
                                        input.create();
                                        tList.add(input);
                                    } while (!confirmChoice(topicReturn));
                                    break;
                                }

                                //Update topic
                                case 2: {
                                    do {
                                        System.out.println();
                                        Topic result =null;
                                        do {
                                            InvalidInput = false;
                                            try {
                                                tList.displayAll();
                                                System.out.print("Enter the TopicID: ");
                                                int query = sc.nextInt();
                                                result = tList.updateTopic(query);
                                            } catch (InputMismatchException e) {
                                                System.out.println("Error: Invalid input, please enter a number\n");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            }
                                        } while (InvalidInput);

                                        if (result==null) {
                                            System.out.println("\nTopic does not exist");
                                        } else {
                                            System.out.println("Topic's info updated: ");
                                            System.out.println(result + "\n");
                                        }
                                    } while (!confirmChoice(topicReturn));
                                    break;
                                }

                                //Delete topic
                                case 3: {
                                    do {
                                        System.out.println();
                                        boolean status = false;
                                        int query = 0;
                                        do {
                                            InvalidInput = false;
                                            try {
                                                tList.displayAll();
                                                System.out.print("Enter the TopicID (press 0 to cancel deletion process): ");
                                                query = sc.nextInt();
                                                if (query!=0) {
                                                    status = tList.deleteTopic(query);
                                                    if (status && tList.getByID(query) == null) {
                                                        System.out.println("Deletion successfully");
                                                    } else if (status && tList.getByID(query) != null) {
                                                        System.out.println("Deletion cancelled");
                                                        break;
                                                    } else {
                                                        throw new NullPointerException();
                                                    }
                                                } else {
                                                    System.out.println("Deletion cancelled");
                                                    break;
                                                }

                                            } catch (InputMismatchException e) {
                                                System.out.println("Error: Invalid input, please enter a number\n");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            } catch (NullPointerException e) {
                                                System.out.println("Error: Incorrect courseID, please try again");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            }
                                        } while (InvalidInput);
                                        System.out.println();
                                    } while (!confirmChoice(topicReturn));
                                    break;
                                }

                                //Display all
                                case 4: {
                                    do {
                                        System.out.println();
                                        TopicList result = new TopicList();
                                        result.addAll(tList);
                                        result.sortByTitle();
                                        result.displayAll();
                                    } while (!confirmChoice(topicReturn));
                                    break;
                                }

                                //return to main
                                default: {
                                    exitStatus = confirmChoice(menuReturn);
                                    break;
                                }
                            }

                        } while (topicChoice > 0 && topicChoice < 5 && !exitStatus);
                        break;

                    // Manage the Course
                    case 2:
                        int courseChoice = 0;
                        exitStatus = false;
                        String courseReturn = "Return to Course Manager?";
                        do {
                            sc = new Scanner(System.in);
                            System.out.println("\n===COURSE MANAGER===");
                            System.out.println("1. Add Course");
                            System.out.println("2. Update Course");
                            System.out.println("3. Delete Course");
                            System.out.println("4. Display Course information (sorted by BeginDate)");
                            System.out.println("Others. Return to main menu");
                            courseChoice = validateMenuChoice();

                            switch (courseChoice) {
                                //Add course
                                case 1: {
                                    do {
                                        System.out.println();
                                        Course input = new Course();
                                        input.create();

                                        //check if the course is successfully inserted to list
                                        if (input.getName() != null) {
                                            if (confirmChoice("Course info added but contain no topic" +
                                                    ". Do you want to add topics to the course?")) {
                                                 if (input.addTopic(tList)) {
                                                     System.out.println("\ntopics added to the course successfully");
                                                 }
                                            } else {
                                                System.out.println("\nCourse data added, please remember to update" +
                                                        " the course's topic in time");
                                            }
                                        }
                                        cList.add(input);

                                    } while (!confirmChoice(courseReturn));
                                    break;
                                }

                                //Update course
                                case 2: {
                                    sc = new Scanner(System.in);
                                    int subCourseChoice=0;
                                    boolean courseExitStatus = false;
                                    String updateReturn = "Do you want to return to the update menu";
                                    do {
                                        System.out.println("\n===COURSE UPDATE MENU===");
                                        System.out.println("1. Update Course's information");
                                        System.out.println("2. Update Course's topics");
                                        System.out.println("Others. Return to Course Manager");
                                        subCourseChoice = validateMenuChoice();

                                        switch (subCourseChoice) {
                                            //Update Course's information
                                            case 1: {
                                                do {
                                                    sc = new Scanner(System.in);
                                                    int query;
                                                    Course result = null;
                                                    do {
                                                        try {
                                                            InvalidInput = false;
                                                            cList.displayAllwithTopics();
                                                            System.out.print("Enter Course ID: ");
                                                            query = sc.nextInt();
                                                            result = cList.updateCourse(query);
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Error: Invalid input, please enter a number\n");
                                                            sc = new Scanner(System.in);
                                                            InvalidInput = true;
                                                        }
                                                    } while (InvalidInput);

                                                    if (result == null) {
                                                        System.out.println("\nThe course does not exist");
                                                    } else {
                                                        System.out.println("Course info updated");
                                                        System.out.println("\n" + result);
                                                    }
                                                } while (!confirmChoice(updateReturn));
                                                break;
                                            }


                                            //Update course topic list
                                            case 2: {
                                                if (tList.isEmpty()) {
                                                    System.out.println("Error: Cannot run this function because Topic list is empty");
                                                    System.out.println("Consider importing data into the application or create new topics");
                                                    break;
                                                }
                                                sc = new Scanner(System.in);
                                                int updateTopicChoice = 0;
                                                boolean topicMenuStatus= false;

                                                do {
                                                    System.out.println("\n1. Add topic to course");
                                                    System.out.println("2. Remove topic from course");
                                                    System.out.println("Others. Return to Update Menu");
                                                    updateTopicChoice = validateMenuChoice();

                                                    switch (updateTopicChoice) {
                                                        //Add topic to course
                                                        case 1: {
                                                            sc = new Scanner(System.in);
                                                            int query = 0;
                                                            do {
                                                                InvalidInput = false;
                                                                try {
                                                                    System.out.println();
                                                                    cList.displayAllwithTopics();
                                                                    System.out.print("Enter the CourseID (press 0 to cancel the update process): ");
                                                                    query = sc.nextInt();
                                                                    if (query !=0) {
                                                                        if (cList.addTopic(query, tList) != null) {
                                                                            System.out.println("Topic(s) added successfully");
                                                                        } else {
                                                                            throw new NullPointerException();
                                                                        }
                                                                    } else {
                                                                        System.out.println("Update cancelled");
                                                                        break;
                                                                    }
                                                                } catch (InputMismatchException e) {
                                                                    System.out.println("Error: Invalid input, please enter a number");
                                                                    sc = new Scanner(System.in);
                                                                    InvalidInput = true;
                                                                } catch (NullPointerException e) {
                                                                    System.out.println("Error: Incorrect courseID or topic data is missing, please try again");
                                                                    sc = new Scanner(System.in);
                                                                    InvalidInput = true;
                                                                }
                                                            } while (InvalidInput);
                                                            break;
                                                        }


                                                        //Remove topic from course
                                                        case 2: {
                                                            sc = new Scanner(System.in);
                                                            int query = 0;
                                                            Course result;
                                                            do {
                                                                InvalidInput = false;
                                                                try {
                                                                    System.out.println();
                                                                    cList.displayAllwithTopics();
                                                                    System.out.print("Enter the CourseID (press 0 to cancel the update process): ");
                                                                    query = sc.nextInt();
                                                                    if (query !=0) {
                                                                        result = cList.removeTopic(query);
                                                                        if (result != null) {
                                                                            System.out.println("Topic(s) removed successfully");
                                                                        } else {
                                                                            throw new NullPointerException();
                                                                        }
                                                                    } else {
                                                                        System.out.println("Update cancelled");
                                                                        break;
                                                                    }
                                                                } catch (InputMismatchException e) {
                                                                    System.out.println("Error: Invalid input, please enter a number");
                                                                    sc = new Scanner(System.in);
                                                                    InvalidInput = true;
                                                                } catch (NullPointerException e) {
                                                                    System.out.println("Error: Incorrect courseID or the selected course don't hold any topic, please try again");
                                                                    sc = new Scanner(System.in);
                                                                    InvalidInput = true;
                                                                }
                                                            } while (InvalidInput);
                                                            break;
                                                        }

                                                        default: {
                                                            topicMenuStatus = confirmChoice(updateReturn);
                                                            break;
                                                        }
                                                    }
                                                } while (!topicMenuStatus);
                                                break;
                                            }

                                            default: {
                                                courseExitStatus = confirmChoice(courseReturn);
                                                break;
                                            }
                                        }
                                    } while (!courseExitStatus);
                                    break;
                                }

                                //Delete course:
                                case 3: {
                                    do {
                                        System.out.println();
                                        boolean status = false;
                                        int query = 0;
                                        do {
                                            InvalidInput = false;
                                            try {
                                                cList.displayAllwithTopics();
                                                System.out.print("Enter the CourseID (press 0 to cancel deletion process): ");
                                                query = sc.nextInt();
                                                if (query!=0) {
                                                    status = cList.deleteCourse(query);
                                                    if (status && cList.getByID(query) == null) {
                                                        System.out.println("Deletion successfully");
                                                    } else if (status && cList.getByID(query) != null) {
                                                        System.out.println("Deletion cancelled");
                                                        break;
                                                    } else {
                                                        throw new NullPointerException();
                                                    }
                                                } else {
                                                    System.out.println("Deletion cancelled");
                                                    break;
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Error: Invalid input, please enter a number\n");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            } catch (NullPointerException e) {
                                                System.out.println("Error: Incorrect courseID, please try again");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            }
                                        } while (InvalidInput);

                                        System.out.println();
                                    } while (!confirmChoice(courseReturn));
                                    break;
                                }

                                //Display all and see course detail by choice
                                case 4: {
                                    int displayMenuChoice = -1;
                                    do {
                                        System.out.println();
                                        CourseList clone = new CourseList();
                                        clone.addAll(cList);
                                        clone.sortByBeginDate();
                                        do {
                                            do {
                                                InvalidInput = false;
                                                try {
                                                    clone.displayAllMenu();
                                                    System.out.print("Select a Course to view it details (press 0 to stop): ");
                                                    displayMenuChoice = sc.nextInt() -1;
                                                    if (displayMenuChoice == -1) break;
                                                    System.out.println("\n[Course Details]\n" + clone.getByMenuChoice(displayMenuChoice) + "\n");
                                                    System.out.println("Enter any key to proceed");
                                                    sc.next();
                                                } catch (IndexOutOfBoundsException e) {
                                                    System.out.println("Error: Invalid choice, please try again\n");
                                                    sc = new Scanner(System.in);
                                                    InvalidInput = true;
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Error: Invalid input, please enter a number\n");
                                                    sc = new Scanner(System.in);
                                                    InvalidInput = true;
                                                }
                                            } while (InvalidInput);

                                        } while (displayMenuChoice != -1);

                                    } while (!confirmChoice(courseReturn));
                                    break;
                                }

                                default: {
                                    exitStatus = confirmChoice(menuReturn);
                                    break;
                                }
                            }
                        } while (courseChoice > 0 && courseChoice < 5 && !exitStatus);
                        break;

                    // Manage the Learner
                    case 3:
                        int learnerChoice = 0;
                        exitStatus = false;
                        String learnerReturn = "Return to Learner Manager?";
                        do {
                            sc = new Scanner(System.in);
                            System.out.println("\n===LEARNER MANAGER===");
                            System.out.println("1. Add Learner to Course");
                            System.out.println("2. Enter scores for learners");
                            System.out.println("3. Display Learner information");
                            System.out.println("Others. Return to main menu");
                            learnerChoice = validateMenuChoice();

                            switch (learnerChoice) {
                                //Add learner to course
                                case 1: {
                                    if (cList.isEmpty()) {
                                        System.out.println("\nError: Cannot run this function because Course list is empty");
                                        System.out.println("Consider importing data into the application or create new Courses");
                                        break;
                                    }

                                    do {
                                        System.out.println();
                                        boolean status = false;
                                        int learnerIDInput = 0;
                                        int courseIDInput = 0;

                                        //select learner
                                        do {
                                            InvalidInput = false;
                                            try {
                                                leList.displayAll();
                                                System.out.print("Enter LearnerID (enter 0 to cancel the assignment process): ");
                                                learnerIDInput = sc.nextInt();
                                                Learner errorCatch = leList.getByID(learnerIDInput);
                                                if (errorCatch == null && learnerIDInput !=0) {
                                                    throw new NullPointerException();
                                                } else if (learnerIDInput == 0) {
                                                    break;
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Error: Invalid input, please enter a number\n");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            } catch (NullPointerException e) {
                                                System.out.println("Error: Incorrect learnerID, please try again\n");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            }
                                        } while (InvalidInput);

                                        //select course
                                        Course courseInput = null;
                                        do {
                                            InvalidInput = false;
                                            if (learnerIDInput==0) break;
                                            try {
                                                sc = new Scanner(System.in);
                                                System.out.println();
                                                cList.displayAllwithTopics();
                                                System.out.print("Enter CourseID: ");
                                                courseIDInput = sc.nextInt();
                                                courseInput = cList.getByID(courseIDInput);
                                                if (courseInput == null) {
                                                    throw new NullPointerException();
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Error: Invalid input, please enter a number");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            } catch (NullPointerException e) {
                                                System.out.println("Error: Incorrect courseID, please try again");
                                                sc = new Scanner(System.in);
                                                InvalidInput = true;
                                            }
                                        } while (InvalidInput);

                                        //assign learner to course
                                        if (learnerIDInput != 0) {
                                            if (leList.assignCoursetoLearner(learnerIDInput, courseInput)) {
                                                System.out.println("Course assigned\n" + leList.getByID(learnerIDInput));
                                                System.out.println("\nCourse learners:" + cList.getByID(courseIDInput).getLearners().getAllLearnerNames());
                                            }
                                        } else {
                                            System.out.println("\nCourse assignment cancelled");
                                        }

                                    } while(!confirmChoice(learnerReturn));
                                    break;
                                }

                                //Enter scores
                                case 2: {
                                    System.out.println("\nTo be implemented (or never will be)");
                                    break;
                                }

                                //Display all
                                case 3: {
                                    System.out.println("\nTo be implemented (or never will be)");
                                    break;
                                }

                                default: {
                                    exitStatus = confirmChoice(menuReturn);
                                }
                            }
                        } while (learnerChoice>0 && learnerChoice<4 && !exitStatus);
                        break;

                    // Search information
                    case 4:
                        int mainSearchChoice=0;
                        String searchReturn = "Return to the search menu?";
                        exitStatus = false;
                        do {
                            sc = new Scanner(System.in);
                            System.out.println("\n===SEARCH MENU===");
                            System.out.println("1. Search Topic");
                            System.out.println("2. Search Course");
                            System.out.println("Others. Return to main menu");
                            mainSearchChoice = validateMenuChoice();

                            switch (mainSearchChoice) {
                                case 1: {
                                    do {
                                        System.out.println("To be implemented (or never be)");
                                    } while(!confirmChoice(searchReturn));
                                    break;
                                }

                                case 2: {
                                    int subSearchChoice=0;
                                    String subSearchReturn = "Return to sub search menu?";
                                    boolean subSearchExitStatus = false;
                                    do {
                                        sc = new Scanner(System.in);
                                        System.out.println("\n1. By Topic");
                                        System.out.println("2. By Name");
                                        System.out.println("Others. Return to search menu");
                                        subSearchChoice = validateMenuChoice();

                                        switch (subSearchChoice) {
                                            case 1: {
                                                do {
                                                    System.out.println("To be implemented (or will never be)");
                                                } while (!confirmChoice(subSearchReturn));
                                                break;
                                            }

                                            case 2: {
                                                do {
                                                    System.out.println("To be implemented (or will never be)");
                                                } while (!confirmChoice(subSearchReturn));
                                                break;
                                            }

                                            default: {
                                                subSearchExitStatus = confirmChoice(searchReturn);
                                                break;
                                            }
                                        }
                                    } while (subSearchChoice > 0 && subSearchChoice < 3 && !subSearchExitStatus);
                                    break;
                                }

                                default: {
                                    exitStatus = confirmChoice(menuReturn);
                                    break;
                                }
                            }
                        } while (mainSearchChoice > 0 && mainSearchChoice < 3 && !exitStatus);

                        break;
                    case 5:
                        // Save Topics, Courses and Learner to file
                        System.out.println();
                        boolean tStatus = tList.writeFile("TopicList.csv");
                        if (tStatus) {
                            System.out.println("Topic data saved");
                        } else {
                            System.out.println("Error: Cannot access file because it is being used by a outside " +
                                    "program. Ensure the file is closed before running this function");
                            System.out.println("Enter any key to proceed");
                            sc.next();
                            break;
                        }

                        boolean cStatus = cList.writeFile("CourseList.csv");
                        if (cStatus) {
                            System.out.println("Course data saved");
                        } else {
                            System.out.println("Error: Cannot access file because it is being used by a outside " +
                                    "program. Ensure the file is closed before running this function");
                            System.out.println("Enter any key to proceed");
                            sc.next();
                            break;
                        }

                        break;

                }
            } while (menuChoice > 0 && menuChoice < 6);
            System.out.println("Exiting...");
        }
    }
