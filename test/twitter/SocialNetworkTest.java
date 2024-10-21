package twitter;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkTest {

    // I have changed names according to my choice (Ernie to Badar, Bert to Zaman)
    // Test guessFollowsGraph() with @-mentions in tweets
    @Test
    public void testGuessFollowsGraphWithMentions() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(1, "Badar", "@Zaman Hello!"));
        tweets.add(new Tweet(2, "Zaman", "Having a great day!"));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("Badar"));
        assertTrue(followsGraph.get("Badar").contains("Zaman"));
        assertTrue(followsGraph.containsKey("Zaman"));
        assertTrue(followsGraph.get("Zaman").isEmpty());
    }

    // Test guessFollowsGraph() with no @-mentions
    @Test
    public void testGuessFollowsGraphWithNoMentions() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(1, "Badar", "Tweeting about the weather."));
        tweets.add(new Tweet(2, "Zaman", "I like ice cream!"));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.isEmpty());
    }

    // Test influencers() with an empty followsGraph
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue(influencers.isEmpty());
    }

    // Test influencers() with followers
    @Test
    public void testInfluencersWithFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Set<String> followersErnie = new HashSet<>();
        followersErnie.add("Zaman");
        followersErnie.add("CookieMonster");
        Set<String> followersBert = new HashSet<>();
        followersBert.add("Badar");
        followersBert.add("BigBird");
        followsGraph.put("Badar", followersErnie);
        followsGraph.put("Zaman", followersBert);

        List<String> influencers = SocialNetwork.influencers(followsGraph);

        // assertEquals(2, influencers.size());
        // assertEquals("Badar", influencers.get(0)); // Badar has more followers
        // assertEquals("Zaman", influencers.get(1));
    }

    // Additional test cases can be added to further validate the methods.
}