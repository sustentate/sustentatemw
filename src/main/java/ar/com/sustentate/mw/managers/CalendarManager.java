package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.EventoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/*@Component
public class CalendarManager {
    @Autowired
    private Calendar calendar;

    @Autowired
    private CloudantManager cloudantManager;

    public void sync() throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());

        calendar.calendars();

        Events events = calendar.events().list("8r5eaeh0rp04tpv6mghb5r44ds@group.calendar.google.com")
                .setMaxResults(100)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        items.forEach((event) -> {
            try {
                long id = event.getStart().getDateTime().getValue();
                if (!cloudantManager.doesEventExists(id)) {
                    EventoResponse eventoResponse = new EventoResponse();
                    eventoResponse.setId(id);
                    eventoResponse.setDate(new Date(event.getStart().getDateTime().getValue()));
                    eventoResponse.setDateEnd(new Date(event.getEnd().getDateTime().getValue()));
                    eventoResponse.setTitle(event.getSummary());
                    eventoResponse.setText(event.getDescription());
                    eventoResponse.setImageUrl("");
                    eventoResponse.setPlace(event.getLocation());
                    eventoResponse.setGoogleId(event.getId());
                    cloudantManager.saveEventos(eventoResponse);
                }
            }
            catch (Exception ex) {
                // Manejo de errores mejorar
            }

        });
    }
}*/
