package twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetwork {

    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        // Create an empty follows graph
        Map<String, Set<String>> followsGraph = new HashMap<>();

        // Iterate through the tweets to find @-mentions and update the followsGraph
        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor();
            Set<String> mentionedUsers = extractMentions(tweet.getText());

            // Update the followsGraph
            for (String mentionedUser : mentionedUsers) {
                if (!mentionedUser.equalsIgnoreCase(author)) {
                    followsGraph.computeIfAbsent(author, k -> new HashSet<>()).add(mentionedUser);
                }
            }
        }

        return followsGraph;
    }

    private static Set<String> extractMentions(String tweetText) {
        Set<String> mentionedUsers = new HashSet<>();
        for (String word : tweetText.split("\\s+")) {
            if (word.startsWith("@") && word.length() > 1) {
                mentionedUsers.add(word.substring(1)); // Remove "@" symbol
            }
        }
        return mentionedUsers;
    }

    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        // Create a map to store the follower counts for each user
        Map<String, Integer> followerCounts = new HashMap<>();

        // Count the followers for each user
        for (String user : followsGraph.keySet()) {
            for (String follower : followsGraph.get(user)) {
                followerCounts.put(follower, followerCounts.getOrDefault(follower, 0) + 1);
            }
        }

        // Sort users by follower count in descending order
        List<String> influencers = new ArrayList<>(followerCounts.keySet());
        influencers.sort((user1, user2) -> Integer.compare(followerCounts.get(user2), followerCounts.get(user1)));

        return influencers;
    }
}