package ar.com.sustentate.mw.repositories;

import com.cloudant.client.api.Changes;
import com.cloudant.client.api.DesignDocumentManager;
import com.cloudant.client.api.Search;
import com.cloudant.client.api.model.*;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.views.AllDocsRequestBuilder;
import com.cloudant.client.api.views.ViewRequestBuilder;

import java.io.InputStream;
import java.net.URI;
import java.util.*;

/**
 * This class' only purpose is to be able to test repositories
 * without going to Cloudant. This is the only way I could find
 * to abstract away the actual Database class (because it's not
 * an interface I could implement and can't subclass it because
 * of reasons).
 */
public class Database {

    private final com.cloudant.client.api.Database cloudantDatabase;

    public Database (com.cloudant.client.api.Database cloudantDatabase) {
        this.cloudantDatabase = cloudantDatabase;
    }

    public void setPermissions(String userNameorApikey, EnumSet<Permissions> permissions) {
        this.cloudantDatabase.setPermissions(userNameorApikey, permissions);
    }

    public Map<String, EnumSet<Permissions>> getPermissions() {
        return this.cloudantDatabase.getPermissions();
    }

    public List<Shard> getShards() {
        return this.cloudantDatabase.getShards();
    }

    public Shard getShard(String docId) {
        return this.cloudantDatabase.getShard(docId);
    }

    public void createIndex(String indexName, String designDocName, String indexType, IndexField[] fields) {
        this.cloudantDatabase.createIndex(indexName, designDocName, indexType, fields);
    }

    public void createIndex(String indexDefinition) {
        this.cloudantDatabase.createIndex(indexDefinition);
    }

    public <T> List<T> findByIndex(String selectorJson, Class<T> classOfT) {
        return this.cloudantDatabase.findByIndex(selectorJson, classOfT);
    }

    public <T> List<T> findByIndex(String selectorJson, Class<T> classOfT, FindByIndexOptions options) {
        return this.cloudantDatabase.findByIndex(selectorJson, classOfT, options);
    }

    public List<Index> listIndices() {
        return this.cloudantDatabase.listIndices();
    }

    public void deleteIndex(String indexName, String designDocId) {
        this.cloudantDatabase.deleteIndex(indexName, designDocId);
    }

    public Search search(String searchIndexId) {
        return this.cloudantDatabase.search(searchIndexId);
    }

    public DesignDocumentManager getDesignDocumentManager() {
        return this.cloudantDatabase.getDesignDocumentManager();
    }

    public ViewRequestBuilder getViewRequestBuilder(String designDoc, String viewName) {
        return this.cloudantDatabase.getViewRequestBuilder(designDoc, viewName);
    }

    public AllDocsRequestBuilder getAllDocsRequestBuilder() {
        return this.cloudantDatabase.getAllDocsRequestBuilder();
    }

    public Changes changes() {
        return this.cloudantDatabase.changes();
    }

    public <T> T find(Class<T> classType, String id) {
        return this.cloudantDatabase.find(classType, id);
    }

    public <T> T find(Class<T> classType, String id, Params params) {
        return this.cloudantDatabase.find(classType, id, params);
    }

    public <T> T find(Class<T> classType, String id, String rev) {
        return this.cloudantDatabase.find(classType, id, rev);
    }

    public <T> T findAny(Class<T> classType, String uri) {
        return this.cloudantDatabase.findAny(classType, uri);
    }

    public InputStream find(String id) {
        return this.cloudantDatabase.find(id);
    }

    public InputStream find(String id, String rev) {
        return this.cloudantDatabase.find(id, rev);
    }

    public boolean contains(String id) {
        return this.cloudantDatabase.contains(id);
    }

    public Response save(Object object) {
        return this.cloudantDatabase.save(object);
    }

    public Response save(Object object, int writeQuorum) {
        return this.cloudantDatabase.save(object, writeQuorum);
    }

    public Response post(Object object) {
        return this.cloudantDatabase.post(object);
    }

    public Response post(Object object, int writeQuorum) {
        return this.cloudantDatabase.post(object, writeQuorum);
    }

    public Response update(Object object) {
        return this.cloudantDatabase.update(object);
    }

    public Response update(Object object, int writeQuorum) {
        return this.cloudantDatabase.update(object, writeQuorum);
    }

    public Response remove(Object object) {
        return this.cloudantDatabase.remove(object);
    }

    public Response remove(String id, String rev) {
        return this.cloudantDatabase.remove(id, rev);
    }

    public List<Response> bulk(List<?> objects) {
        return this.cloudantDatabase.bulk(objects);
    }

    public Response saveAttachment(InputStream in, String name, String contentType) {
        return this.cloudantDatabase.saveAttachment(in, name, contentType);
    }

    public Response saveAttachment(InputStream in, String name, String contentType, String docId, String docRev) {
        return this.cloudantDatabase.saveAttachment(in, name, contentType, docId, docRev);
    }

    public Response removeAttachment(Object object, String attachmentName) {
        return this.cloudantDatabase.removeAttachment(object, attachmentName);
    }

    public Response removeAttachment(String id, String rev, String attachmentName) {
        return this.cloudantDatabase.removeAttachment(id, rev, attachmentName);
    }

    public String invokeUpdateHandler(String updateHandlerUri, String docId, Params params) {
        return this.cloudantDatabase.invokeUpdateHandler(updateHandlerUri, docId, params);
    }

    public URI getDBUri() {
        return this.cloudantDatabase.getDBUri();
    }

    public DbInfo info() {
        return this.cloudantDatabase.info();
    }

    public void ensureFullCommit() {
        this.cloudantDatabase.ensureFullCommit();
    }

    public <T> QueryResult<T> queryAs(String aQuery, Class<T> aClass) {
        return this.cloudantDatabase.query(aQuery, aClass);
    }
}
