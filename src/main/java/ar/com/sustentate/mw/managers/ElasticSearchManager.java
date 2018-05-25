package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.ElementosModel;
import ar.com.sustentate.mw.models.ElementosResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElasticSearchManager {
    @Autowired
    private RestHighLevelClient elasticSearchClient;

    @Autowired
    private CloudantManager cloudantManager;

    public void saveElemento(ElementosModel elementosModel) throws IOException {
        HashMap<String, String> values = new HashMap<>();
        values.put("producto", elementosModel.getProducto());
        values.put("condicion", elementosModel.getCondicion());
        values.put("comoHacerlo", elementosModel.getComoHacerlo());

        IndexRequest indexRequest = new IndexRequest("elementos",
                "elemento",
                Long.valueOf(elementosModel.getId()).toString())
                .source(values);
        elasticSearchClient.index(indexRequest);
    }

    public void synchronize() throws IOException {
        // Elimino el indice completo
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("elementos");
            elasticSearchClient.indices().delete(deleteIndexRequest);
        } catch(Exception ex) {
            //Agregar log
        }

        // Create index
        CreateIndexRequest request = new CreateIndexRequest("elementos");
        request.mapping("elemento",
                "  {\n" +
                        "    \"elemento\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"producto\": {\n" +
                        "          \"type\": \"text\",\n" +
                        "          \"analyzer\": \"standard\",\n" +
                        "          \"search_analyzer\": \"standard\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }",
                XContentType.JSON);
        elasticSearchClient.indices().create(request);

        // Me traigo todos los elementos
        List<ElementosModel> elementos = cloudantManager.getElementos();

        // Actualizo todos los elementos
        elementos.forEach((k) -> {
            try {
                saveElemento(k);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<ElementosResponse> search(String criterio) throws IOException {
        List<ElementosResponse> elementosRespons = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("elementos");
        searchRequest.types("elemento");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("producto", criterio));
        //searchSourceBuilder.query(QueryBuilders.multiMatchQuery(criterio, "producto", "comoHacerlo"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = elasticSearchClient.search(searchRequest);
        if (response.status().getStatus() == 200 && response.getHits().totalHits > 0) {
            for (SearchHit hit : response.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                ElementosResponse elementosResponse = new ElementosResponse();
                elementosResponse.setComoHacerlo(sourceAsMap.get("comoHacerlo").toString());
                elementosResponse.setProducto(sourceAsMap.get("producto").toString());
                elementosResponse.setCondicon(sourceAsMap.get("condicion").toString());
                elementosResponse.setId(Long.parseLong(hit.getId()));
                elementosResponse.setScore(hit.getScore());
                elementosRespons.add(elementosResponse);
            }
        }
        return elementosRespons;
    }
}
