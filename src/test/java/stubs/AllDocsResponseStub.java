package stubs;

import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.views.AllDocsResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AllDocsResponseStub implements AllDocsResponse {
    private final List<Document> documents;

    AllDocsResponseStub(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public List<Document> getDocs() {
        return this.documents;
    }

    @Override
    public Map<String, String> getIdsAndRevs() {
        return null;
    }

    @Override
    public <D> List<D> getDocsAs(Class<D> aClass) {
        return (List<D>) this.documents;
    }

    @Override
    public List<String> getDocIds() {
        return null;
    }

    @Override
    public Map<String, String> getErrors() {
        return new HashMap<>();
    }
}
