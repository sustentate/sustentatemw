package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.exceptions.NoUpdatableFormatException;
import ar.com.sustentate.mw.models.Event;
import ar.com.sustentate.mw.models.EventType;
import ar.com.sustentate.mw.repositories.Repository;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    public EventController(CloudantClient cloudantClient) {
        this.eventRepository = new Repository<>(new ar.com.sustentate.mw.repositories.Database(cloudantClient.database("eventos", true)));
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
        eventRepository.save(anEventToBeCreated);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity updateEvent(@Valid @RequestBody Event anUpdatedEvent) throws NoUpdatableFormatException {
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
