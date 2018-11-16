package repositories;

import ar.com.sustentate.mw.repositories.Repository;
import com.cloudant.client.api.model.Document;

import static com.cloudant.client.api.query.Expression.eq;
import static org.assertj.core.api.Assertions.*;

import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.org.lightcouch.Attachment;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import org.junit.Before;
import org.junit.Test;
import stubs.DatabaseStub;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesTest {

    private Document firstDocument;
    private List<Document> allDocuments;
    private Repository<Document> aRepository;
    private DatabaseStub aDatabaseStub;

    @Before
    public void setupDatabaseStub() {
        this.firstDocument = new Document();
        this.firstDocument.setId("firstId");

        Document secondDocument = new Document();
        secondDocument.setId("secondId");

        this.allDocuments = new ArrayList<>();
        this.allDocuments.add(this.firstDocument);
        this.allDocuments.add(secondDocument);
        this.aDatabaseStub = DatabaseStub.withContent(this.allDocuments);
        this.aRepository = new Repository<>(aDatabaseStub);
    }

    @Test
    public void aRepositoryGivesAllTheDocumentsOfADatabase() {
        assertThat(this.aRepository.findAllAs(Document.class)).isEqualTo(this.allDocuments);
    }

    @Test
    public void aRepositoryRetrievesADocumentById() {
        assertThat(this.aRepository.findByIdAs(Document.class, this.firstDocument.getId())).isEqualTo(this.firstDocument);
    }

    @Test
    public void aRepositoryThrowsAnErrorIfADocuemntIsNotFound() {
        assertThatExceptionOfType(NoDocumentException.class)
                .isThrownBy(() -> this.aRepository.findByIdAs(Document.class, "a non-existent id"))
                .withMessage("404 " + Repository.DOCUMENT_NOT_FOUND + ".");
    }

    @Test
    public void aRepositorySavesDocuments() {
        Document newDocument = new Document();
        newDocument.setId("my new id");
        this.aRepository.save(newDocument);
        assertThat(this.aRepository.findByIdAs(Document.class, newDocument.getId())).isEqualTo(newDocument);
    }

    @Test
    public void aRepositoryUpdatesDocuments() {
        String attachmentLabel = "An attachment";
        Attachment attachment = new Attachment();
        this.firstDocument.addAttachment(attachmentLabel, attachment);
        this.aRepository.update(this.firstDocument);
        assertThat(this.aRepository.findByIdAs(Document.class, this.firstDocument.getId()).getAttachments()).containsKey(attachmentLabel).containsValue(attachment);
    }

    @Test
    public void aRepositoryExecutesAQueryOnItsDatabase() {
        String aQuery = new QueryBuilder(eq("a property", "a value")).build();
        this.aRepository.queryAs(aQuery, Document.class);
        assertThat(this.aDatabaseStub.executedQuery(aQuery, Document.class)).isTrue();
    }
}
