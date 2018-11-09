package stubs;

import ar.com.sustentate.mw.repositories.Database;
import ar.com.sustentate.mw.repositories.Repository;
import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.ExecutionStats;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.views.AllDocsRequestBuilder;
import com.cloudant.client.org.lightcouch.NoDocumentException;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class DatabaseStub extends Database {

    private List<Document> documents;
    private String storedQuery;
    private Class storedQueryClass;

    private DatabaseStub() {
        super(null);
    }

    public static DatabaseStub withContent(List<Document> documents) {
        DatabaseStub newDatabaseStub = new DatabaseStub();
        newDatabaseStub.documents = documents;
        return newDatabaseStub;
    }

    @Override
    public AllDocsRequestBuilder getAllDocsRequestBuilder() {
        return new AllDocsRequestBuilderStub(this.documents);
    }

    @Override
    public <T> T find(Class<T> classType, String id) {
        return (T) this.documents.stream()
                .filter(document -> document.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoDocumentException(Repository.DOCUMENT_NOT_FOUND));
    }

    @Override
    public Response save(Object documentToAdd) {
        this.documents.add((Document) documentToAdd);
        return new Response();
    }

    @Override
    public Response update(Object updatedDocument) {
        this.documents.removeIf(document -> ((Document) updatedDocument).getId().equals(document.getId()));
        this.documents.add((Document) updatedDocument);
        return new Response();
    }

    @Override
    public <T> QueryResult<T> queryAs(String aQuery, Class<T> aClass) {
        this.storedQuery = aQuery;
        this.storedQueryClass = aClass;
        return getEmptyQueryResult();
    }

    private <T> QueryResult<T> getEmptyQueryResult() {
        ExecutionStats emptyExecutionStats = new ExecutionStats(0,0,0,0,0);
        return new QueryResult<>(Collections.emptyList(), "", emptyExecutionStats, "");
    }

    public <T> boolean executedQuery(@NotNull String aQuery, Class <T> aClass) {
        return aQuery.equals(this.storedQuery) && aClass.equals(this.storedQueryClass);
    }
}
