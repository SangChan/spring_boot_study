package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.web.client.RestTemplate;

public class Application {

    public static void main(String[] args) {
        String appId = promptForInput("Enter your Consumer ID:");
        String appSecret = promptForInput("Enter your Consumer Secret:");
        String appToken = fetchApplicationAccessToken(appId, appSecret);
        List<Tweet> tweets = searchTwitter("#springframework", appToken);
        for (Tweet tweet : tweets) {
            System.out.println(tweet.getText());
        }
    }

    private static List<Tweet> searchTwitter(String query, String appToken) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        // Using application token for search so that we don't have to go through hassle of getting a user token.
        // This is not (yet) supported by Spring Social, so we must construct the request ourselves.
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + appToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        Map<String, ?> result = rest.exchange("https://api.twitter.com/1.1/search/tweets.json?q={query}", HttpMethod.GET, requestEntity, Map.class, query).getBody();
        List<Map<String, ?>> statuses = (List<Map<String, ?>>) result.get("statuses");
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (Map<String, ?> status : statuses) {
            tweets.add(new Tweet(Long.valueOf(status.get("id").toString()), status.get("text").toString()));
        }
        return tweets;
    }

    private static String fetchApplicationAccessToken(String appId, String appSecret) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        OAuth2Operations oauth = new OAuth2Template(appId, appSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth.authenticateClient().getAccessToken();
    }
    
    private static String promptForInput(String promptText) {
        return JOptionPane.showInputDialog(promptText + " ");
    }
    
    private static final class Tweet {
        private long id;

        private String text;
        
        public Tweet(long id, String text) {
            this.id = id;
            this.text = text;
        }
        
        public long getId() {
            return id;
        }
        
        public String getText() {
            return text;
        }
    }

}
