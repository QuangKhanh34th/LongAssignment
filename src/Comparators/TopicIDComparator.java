package Comparators;

import DTO.Topic;

import java.util.Comparator;

public class TopicIDComparator implements Comparator {

    // Compare based on TopicID in ascending order
    @Override
    public int compare(Object o1, Object o2) {
        Topic s1 = (Topic) o1;
        Topic s2 = (Topic) o2;

        if(s1.getTopicID() < s2.getTopicID()) return -1;
        else if(s1.getTopicID() > s2.getTopicID()) return 1;
        return 0;
    }
}
