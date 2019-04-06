package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.exceptions.NoUpdatableFormatException;
import ar.com.sustentate.mw.managers.ObjectStorageManager;
import ar.com.sustentate.mw.models.Event;
import ar.com.sustentate.mw.models.EventType;
import ar.com.sustentate.mw.repositories.Repository;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.query.*;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Expression.exists;
import static com.cloudant.client.api.query.Expression.regex;
import static com.cloudant.client.api.query.Operation.and;
import static com.cloudant.client.api.query.Operation.or;

@RestController
@RequestMapping("/events")
public class EventController {

    private final Repository<Event> eventRepository;
    private ObjectStorageManager objectStorageManager;

    @Autowired
    public EventController(CloudantClient cloudantClient, ObjectStorageManager objectStorageManager1) {
        this.eventRepository = new Repository<>(new ar.com.sustentate.mw.repositories.Database(cloudantClient.database("eventos", true)));
        this.objectStorageManager = objectStorageManager1;
    }


    @GetMapping
    private List<Event> allEvents(@RequestParam Optional<Boolean> promoted,
                                  @RequestParam Optional<Boolean> published,
                                  @RequestParam Optional<EventType> type,
                                  @RequestParam Optional<String> search,
                                  @RequestParam Optional<Long> limit) {

        Selector alwaysTrueSelector = exists("_id", true);
        Selector selector = and(promoted.map(searchTerm -> (Selector) eq("promoted", searchTerm)).orElse(alwaysTrueSelector),
                                published.map(searchTerm -> (Selector) eq("published", searchTerm)).orElse(alwaysTrueSelector),
                                type.map(searchTerm -> (Selector) eq("type", searchTerm.toString())).orElse(alwaysTrueSelector),

                                or(search.map(searchTerm -> (Selector) regex("title", ".*" + searchTerm + ".*")).orElse(alwaysTrueSelector),
                                   search.map(searchTerm -> (Selector) regex("description", ".*" + searchTerm + ".*")).orElse(alwaysTrueSelector)));


        String query = new QueryBuilder(selector)
                .limit(limit.orElse(20L))
                .build();

        return eventRepository.queryAs(query, Event.class);
    }

    @PostMapping
    private ResponseEntity crearEvento(@Valid @RequestBody Event anEventToBeCreated) {
        // TODO Segurizar
        eventRepository.save(anEventToBeCreated);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping("/{id}/image")
    private ResponseEntity attachImage(@PathVariable String id, @RequestBody String imageEncoded) throws IOException {
    	
    	Event event = eventRepository.findByIdAs(Event.class, id);
    	
    	if(event == null) {
    		return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	byte[] imageContents = Base64.decodeBase64(imageEncoded);

        File tempFile = File.createTempFile("event", ".jpeg");
        OutputStream outputStream = new FileOutputStream(tempFile);
        outputStream.write(imageContents);
        outputStream.close();
        try {
        	String url = objectStorageManager.saveImage(tempFile, "agenda-event");
        	event.setUrlImage(url);
        	eventRepository.update(event);
        }catch(Exception e){
        	return null;
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    	
    }

    @PutMapping
    private ResponseEntity updateEvent(@Valid @RequestBody Event anUpdatedEvent) throws NoUpdatableFormatException {
        // TODO Segurizar
        assertHasIdAndRev(anUpdatedEvent);
        eventRepository.update(anUpdatedEvent);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private void assertHasIdAndRev(Event anUpdatedEvent) throws NoUpdatableFormatException {
        if (anUpdatedEvent.getId() == null || anUpdatedEvent.getRev() == null) {
            throw new NoUpdatableFormatException("The document supplied has no _id or _rev fields");
        }
    }

    @GetMapping("/{id}")
    private Event getEvent(@PathVariable String id) {
        return eventRepository.findByIdAs(Event.class, id);
    }
}
