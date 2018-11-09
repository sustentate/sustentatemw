package ar.com.sustentate.mw.repositories;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Repository<T> {
    public static final String DOCUMENT_NOT_FOUND = "Document was not found";
    private final Database database;

    public Repository (Database aDatabase){
        this.database = aDatabase;
    }

    public List<T> findAllAs(Class<T> aClass) {
        try {
            return this.database.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(aClass);
        } catch (IOException exception) {
            return Collections.emptyList();
        }

    }

    public T findByIdAs(Class<T> aClass, String id) {
        return this.database.find(aClass, id);
    }

    public void save(T aNewDocument) {
        this.database.save(aNewDocument);
    }

    public void update(T anUpdatedDocument) {
        this.database.update(anUpdatedDocument);
    }

    public List<T> queryAs(String aQuery, Class<T> aClass) {
        return this.database.queryAs(aQuery, aClass).getDocs();
    }
}
