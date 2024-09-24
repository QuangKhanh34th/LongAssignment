package Comparators;

import DTO.Topic;

import java.util.Comparator;

public class TopicNameComparator implements Comparator<Topic> {


    // Compare based on title length in ascending order
    @Override
    public int compare(Topic o1, Topic o2) {
        if (o1.getTitle().length() < o2.getTitle().length()) return -1;
        else if (o1.getTitle().length() > o2.getTitle().length()) return 1;
        return 0;
    }
}
