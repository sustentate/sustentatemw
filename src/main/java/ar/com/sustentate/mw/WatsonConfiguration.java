package ar.com.sustentate.mw;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.*;

@Configuration
public class WatsonConfiguration {

    @Value("${ibm.es.url}")
    private String elasticSearchUrl;

    @Value("${ibm.es.host}")
    private String elasticSearchHost;

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
    public TransportClient createElasticSearchClient() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticSearchHost), 41997));
        return client;
    }
}
