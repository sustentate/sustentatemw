package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.models.AssistanceRequest;
import ar.com.sustentate.mw.models.AssistanceResponse;
import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.*;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions.Builder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;
import java.util.UUID;

@RestController
public class AssistanceController {

    @Autowired
    private Assistant assistant;

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public @ResponseBody
    AssistanceResponse chat(@RequestBody AssistanceRequest request) {
        AssistanceResponse r = new AssistanceResponse();

        // TODO Sacar el id del asistente de la configuracion
        UUID assistantUUID = UUID.fromString("2e583778-2a1b-47e1-b88c-e2a79d0d4844");

        // TODO Make everycall async
        // TODO Threat session as I should
        ServiceCall<SessionResponse> sessionResponseCall =
                assistant.createSession(new Builder().assistantId(assistantUUID.toString()).build());
        SessionResponse sessionResponse = sessionResponseCall.execute();
        // TODO Learn difference between session and assistantId
        ServiceCall<MessageResponse> messageResponseCall = assistant.message(new MessageOptions.Builder()
                .assistantId(assistantUUID.toString())
                .sessionId(sessionResponse.getSessionId())
                .input(new MessageInput.Builder()
                        .text(request.getSentence())
                        .build()
                )
                .build());
        MessageResponse response = messageResponseCall.execute();
        String responseText = "Error md";

        // TODO ver cual es la manera correcta de parsearesto
        if (response.getOutput().getGeneric().size() > 0) {
            responseText = response.getOutput().getGeneric().get(0).getText();
        }
        r.setSentence(responseText);
        r.setUrlAttachment(request.getUrlAttachment());
        r.setStatus(0); //Ok
        return r;
    }
}
