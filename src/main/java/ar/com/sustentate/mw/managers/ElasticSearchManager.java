package ar.com.sustentate.mw.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Component
public class ElasticSearchManager {
    @Autowired
    private TransportClient elasticSearchClient;

    public void savePrueba() throws IOException {
       elasticSearchClient.prepareIndex("elementos","elemento", "1")
               .setSource(jsonBuilder()
               .startObject()
                .field("titulo", "recicla vieja")
               .endObject())
               .get();
    }
}
