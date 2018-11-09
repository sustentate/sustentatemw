package stubs;

import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.views.AllDocsRequest;
import com.cloudant.client.api.views.AllDocsResponse;

import java.util.List;

class AllDocsRequestStub implements AllDocsRequest {
    private final List<Document> documents;

    AllDocsRequestStub(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public AllDocsResponse getResponse() {
        return new AllDocsResponseStub(this.documents);
    }
}
