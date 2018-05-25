package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CalendarManager;
import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.models.EcoTipResponse;
import ar.com.sustentate.mw.models.EventoResponse;
import ar.com.sustentate.mw.models.OperationResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class EventosController {
    @Autowired
    private CloudantManager cloudantManager;

    @Autowired
    private CalendarManager calendarManager;

    @RequestMapping("/eventos")
    public @ResponseBody
    List<EventoResponse> getEventos(@RequestParam(name="lastId", required = false) String lastId) throws IOException {
        List<EventoResponse> eventos = cloudantManager.getEventos(lastId);
        return eventos;
    }

    @RequestMapping(value = "/eventos/sync", method = RequestMethod.POST)
    public @ResponseBody
    OperationResponse sync() throws IOException {
        OperationResponse response = new OperationResponse();
        calendarManager.sync();
        response.setStatus("OK");
        return response;
    }
}
