package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.models.AssistanceRequest;
import ar.com.sustentate.mw.models.AssistanceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssistanceController {

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public @ResponseBody AssistanceResponse chat(@RequestBody AssistanceRequest request) {
        AssistanceResponse r = new AssistanceResponse();
        r.setSentence(request.getSentence());
        r.setUrlAttachment(request.getUrlAttachment());
        r.setStatus(0); //Ok
        return r;
    }
}
