package stubs;

import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.views.AllDocsRequest;
import com.cloudant.client.api.views.AllDocsRequestBuilder;

import java.util.List;

class AllDocsRequestBuilderStub implements AllDocsRequestBuilder {
    private final List<Document> documents;

    AllDocsRequestBuilderStub(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public AllDocsRequest build() {
        return new AllDocsRequestStub(this.documents);
    }

    @Override
    public AllDocsRequestBuilder returnThis() {
        return this;
    }

    @Override
    public AllDocsRequestBuilder limit(int i) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder skip(long l) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder descending(boolean b) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder endKey(String s) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder endKeyDocId(String s) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder includeDocs(boolean b) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder inclusiveEnd(boolean b) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder keys(String... strings) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder stable(boolean b) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder stale(String s) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder startKey(String s) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder startKeyDocId(String s) {
        return this;
    }

    @Override
    public AllDocsRequestBuilder update(String s) {
        return this;
    }
}
