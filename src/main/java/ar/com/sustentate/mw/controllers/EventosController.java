package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.models.EcoTipResponse;
import ar.com.sustentate.mw.models.EventoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EventosController {
    @Autowired
    private CloudantManager cloudantManager;

    @RequestMapping("/eventos")
    public @ResponseBody
    List<EventoResponse> getEventos(@RequestParam(name="lastId", required = false) String lastId) throws IOException {
        List<EventoResponse> eventos = cloudantManager.getEventos(lastId);
        return eventos;
    }
}
