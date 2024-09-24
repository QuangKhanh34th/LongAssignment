package DTO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Topic {

    // code or ID, name, type (long/short term), title, duration, etc….
    // Topic ID is unique.
    public static int idSeed = 10000;
    private int topicID = idSeed;
    private String title;
    private final String[] typeList = {"long", "short"};
    private String type;
    private int duration;

    //Constructors
    public Topic() {
        idSeed++;
    }

    private boolean isValidType(String type) {
        for (String typeCheck : typeList) {
            if (type.equalsIgnoreCase(typeCheck)) return true;
        }
        return false;
    }

    public Topic(String title, String type, int duration) throws IllegalArgumentException {
        this.title = title;
        if (isValidType(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'long' or 'short'.");
        }
        this.duration = duration;
        idSeed++;
    }

    public Topic(int topicID,String title, String type, int duration) throws IllegalArgumentException {
        this.topicID = topicID;
        this.title = title;
        if (isValidType(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'long' or 'short'.");
        }
        this.duration = duration;
        idSeed++;
    }

    //Getters and Setters

    public static int getIdSeed() {
        return idSeed;
    }

    public static void setIdSeed(int idSeed) {
        Topic.idSeed = idSeed;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
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
            throw new IllegalArgumentException("Invalid type. Must be 'long' or 'short'.");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("| TopicID: %-5d | title: %-10s | type: %-6s | duration (hr): %-5d |",
                topicID, title, type, duration);
    }

    public String toCSVFormat() {
        return topicID + "," + title + "," + type + "," + duration;
    }


    // Create a new Topic with input validation
    public void create() {
        Scanner sc = new Scanner(System.in);
        boolean InvalidInput;

        //title
        do {
            InvalidInput = false;
            System.out.print("Enter Topic's title: ");
            String title = sc.nextLine();
            if (title.trim().replaceAll(" +", " ").isEmpty()) {
                System.out.println("Error: Title cannot be empty, please try again");
                InvalidInput = true;
            } else {
                this.title = title;
            }
        } while (InvalidInput);


        //type
        do {
            System.out.println();
            InvalidInput = false;
            try {
                int counter = 1;
                for (String type : this.typeList) {
                    System.out.println(counter + ". " + type);
                    counter++;
                }
                System.out.print("Select Topic's type: ");
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


        //Duration
        System.out.println();
        sc = new Scanner(System.in);
        int duration=0;
        do {
            InvalidInput = false;
            try {
                System.out.print("Enter Topic's Duration: ");
                duration = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
        this.duration = duration;
    }

    // Update an existing Topic with the option to skip fields user
    // doesn't want to update
    public void update() {
        Scanner sc = new Scanner(System.in);
        boolean InvalidInput;

        //title
        System.out.print("Enter Topic's title (leave blank to skip): ");
        String title = sc.nextLine();
        if (!title.trim().replaceAll(" +", " ").isEmpty()) {
            this.title = title;
        }



        //type

        do {
            System.out.println();
            InvalidInput = false;
            try {
                int counter = 1;
                for (String type : this.typeList) {
                    System.out.println(counter + ". " + type);
                    counter++;
                }
                System.out.print("Select Topic's type (press 0 to skip): ");
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


        //Duration
        System.out.println();
        sc = new Scanner(System.in);
        int duration=0;
        do {
            InvalidInput = false;
            try {
                System.out.print("Enter Topic's Duration (press 0 to skip): ");
                duration = sc.nextInt();
                if (duration == 0) {
                    break;
                }
                this.duration = duration;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input, please enter a number\n");
                sc = new Scanner(System.in);
                InvalidInput = true;
            }
        } while (InvalidInput);
    }

}
