package hello;

import javax.swing.JOptionPane;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;

public class Application {

    public static void main(String[] args) {
        String appId = promptForInput("Enter your App ID:");
        String appSecret = promptForInput("Enter your App Secret:");
        String appToken = fetchApplicationAccessToken(appId, appSecret);
        AppDetails appDetails = fetchApplicationData(appId, appToken);
        System.out.println("\n   APPLICATION DETAILS");
        System.out.println("=========================");
        System.out.println("ID:             " + appDetails.getId());
        System.out.println("Name:           " + appDetails.getName());
        System.out.println("Namespace:      " + appDetails.getNamespace());
        System.out.println("Contact Email:  " + appDetails.getContactEmail());
        System.out.println("Website URL:    " + appDetails.getWebsiteUrl());
    }

    private static AppDetails fetchApplicationData(String appId, String appToken) {
        Facebook facebook = new FacebookTemplate(appToken);
        return facebook.restOperations().getForObject("https://graph.facebook.com/{appId}?fields=name,namespace,contact_email,website_url", AppDetails.class, appId);
    }

    private static String fetchApplicationAccessToken(String appId, String appSecret) {
        OAuth2Operations oauth = new FacebookConnectionFactory(appId, appSecret).getOAuthOperations();
        return oauth.authenticateClient().getAccessToken();
    }
    
    private static String promptForInput(String promptText) {
        return JOptionPane.showInputDialog(promptText + " ");
    }
    
    private static final class AppDetails {
        private long id;

        private String name;
        
        private String namespace;
        
        @JsonProperty("contact_email")
        private String contactEmail;
        
        @JsonProperty("website_url")
        private String websiteUrl;
        
        public long getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getNamespace() {
            return namespace;
        }
        public String getContactEmail() {
            return contactEmail;
        }
        public String getWebsiteUrl() {
            return websiteUrl;
        }
    }

}
