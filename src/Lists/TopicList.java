package Lists;

import Comparators.TopicIDComparator;
import Comparators.TopicNameComparator;
import DTO.Topic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import static UI.Main.confirmChoice;

public class TopicList extends ArrayList<Topic> {


    public TopicList readFile(String filename) throws IOException {
        File fInput = new File(filename);

        // return null when file not found for exception checking in Main.java
        FileReader fr;
        try {
            fr = new FileReader(fInput);
        } catch (FileNotFoundException e) {
            return null;
        }

        BufferedReader br = new BufferedReader(fr);
        TopicList output = new TopicList();

        // separate lines into set of data then assign them to
        // each of the class's attributes
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] infos = line.split(",");

            int TopicID = Integer.parseInt(infos[0]);
            String title = infos[1];
            String type = infos[2];
            int duration = Integer.parseInt(infos[3]);

            // combine the info gathered into an object, then add the object to list
            output.add(new Topic(TopicID, title, type, duration));
        }

        fr.close();
        br.close();
        return output;
    }

    public boolean writeFile(String filename) {
        File fOutput = new File(filename);
        PrintWriter pw;
        try {
            pw = new PrintWriter(fOutput);
        } catch (FileNotFoundException e) {
            return false;
        }

        for (Topic t : this) {
            pw.write(t.toCSVFormat()+"\n");
        }

        pw.close();

        return true;
    }

    public Topic getByID(int topicID) {
       for (Topic topicSearch : this) {
           if (topicSearch.getTopicID() == topicID) {
               return topicSearch;
           }
       }
       return null;
   }

    // search for topic in the list using for-loop, then
    // modify the topic's info if found
    public Topic updateTopic(int topicID) {
        for (Topic topicSearch : this) {
            if (topicSearch.getTopicID() == topicID) {
                topicSearch.update();
                return topicSearch;
            }
        }
        // couldn't find the topic
        return null;
    }


    // same as updateTopic, but for deletion and included a confirmation message
    public boolean deleteTopic(int topicID) {
        for (Topic topicSearch  : this) {
            if (topicSearch.getTopicID() == topicID) {
                // return true no matter the user's choice, indicate a successful function run
                // without error
                if (confirmChoice("Topic found, do you really want to delete [" + topicSearch.getTitle() + "] ?")) {
                    this.remove(topicSearch);
                    return true;
                } else {
                    return true;
                }
            }
        }
        //couldn't find the topic
        return false;
    }

    public void sortByTitle() {
        Collections.sort(this, new TopicNameComparator());
    }

    public void sortByID() {
        Collections.sort(this, new TopicIDComparator());
    }

    public void displayAll() {
        if (!this.isEmpty()) {
            System.out.println("===TOPIC CATALOGUE===");
            for (Topic topic : this) {
                System.out.println(topic.toString());
            }
        } else {
            System.out.println("Topic list is empty");
        }
    }





    // used by Course's TopicList for displaying purpose
    public String getTopicNames() {
        StringBuilder sb = new StringBuilder();

        for (Topic topic : this) {
            if (topic != null) sb.append(topic.getTitle() + ", ");
        }

        String output= sb.toString();
        if (output.isEmpty()) {
            return "null";
        }
        output = output.substring(0,output.length()-2);
        return output;
    }

    // Internal use for CourseList's file reading
    @Deprecated
    public Topic getByExactName(String tName) {
        for (Topic t : this) {
            if (t.getTitle().equals(tName)) {
                return t;
            }
        }
        return null;
    }

    // Internal use for CourseList's file reading
    @Deprecated
    public String getTopicNamesforReading() {
        StringBuilder sb = new StringBuilder();

        for (Topic topic : this) {
            if (topic != null)
                sb.append(topic.getTitle() + "|");
        }

        String output = sb.toString();
        if (output.isEmpty()) {
            return "null";
        }
        output = output.substring(0, output.length() - 1);
        return output;
    }

    // Internal use for Course's Topic deletion
    // same as deleteTopic, but without confirmation message
    @Deprecated
    public boolean deleteTopicWithoutMessage(int topicID) {
        for (Topic topicSearch  : this) {
            if (topicSearch.getTopicID() == topicID) {
                this.remove(topicSearch);
                return true;
            }
        }
        return false;
    }
}
