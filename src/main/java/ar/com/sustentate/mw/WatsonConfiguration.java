package ar.com.sustentate.mw;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
public class WatsonConfiguration {

    @Value("${ibm.watson.visualrecognition.key}")
    private String watsonApiKey;

    @Value("${ibm.watson.objectstorage.key}")
    private String objectStorageKey;

    @Value("${ibm.watson.objectstorage.endpoint}")
    private String objectStorageEndpoint;

    @Value("${ibm.watson.objectstorage.location}")
    private String objectStorageLocation;

    @Value("${ibm.watson.objectstorage.resourceid}")
    private String objectStorageResourceId;

    @Value("${ibm.watson.cloudant.user}")
    private String cloudantUser;

    @Value("${ibm.watson.cloudant.password}")
    private String cloudantPassword;

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

    @Bean
    public VisualRecognition visualRecognition() {
        VisualRecognition visualRecognition = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        visualRecognition.setApiKey(watsonApiKey);
        return visualRecognition;
    }

    @Bean
    public AmazonS3 createAwsClient() {
        AWSCredentials credentials;
        if (objectStorageEndpoint.contains("objectstorage.softlayer.net")) {
            credentials = new BasicIBMOAuthCredentials(objectStorageKey, objectStorageResourceId);
        } else {
            String access_key = objectStorageKey;
            String secret_key = objectStorageResourceId;
            credentials = new BasicAWSCredentials(access_key, secret_key);
        }
        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cos = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(objectStorageEndpoint, objectStorageLocation)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cos;
    }

    @Bean
    public CloudantClient createCloudantClient() throws MalformedURLException {
        CloudantClient client = ClientBuilder
                .account(cloudantUser)
                .username(cloudantUser)
                .password(cloudantPassword)
                .build();
        return client;
    }

    @Bean
    public NetHttpTransport getTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public JacksonFactory getFactory() {
        return JacksonFactory.getDefaultInstance();
    }

 /*   @Bean
    public Credential getCredentials(NetHttpTransport transport, JacksonFactory factory) throws IOException, GeneralSecurityException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                transport, factory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("credentials")))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }*/

    /*@Bean
    public Calendar getCalendar(Credential credential, JacksonFactory factory, NetHttpTransport transport) {
        Calendar calendar = new Calendar.Builder(transport, factory, credential)
                .setApplicationName("sustentatemw")
                .build();
        return calendar;
    }*/
}
