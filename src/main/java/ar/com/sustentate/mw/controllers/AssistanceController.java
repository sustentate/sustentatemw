package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.models.AssistanceRequest;
import ar.com.sustentate.mw.models.AssistanceResponse;
import ar.com.sustentate.mw.models.AssistantContext;
import ar.com.sustentate.mw.repositories.Repository;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.Selector;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.*;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions.Builder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Expression.exists;
import static com.cloudant.client.api.query.Expression.regex;
import static com.cloudant.client.api.query.Operation.and;
import static com.cloudant.client.api.query.Operation.or;


import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class AssistanceController {

    private Assistant assistant;

    private final Repository<AssistantContext> assistantContextRepository;

    @Autowired
    public AssistanceController(Assistant assistant, CloudantClient cloudantClient) {
       this.assistant = assistant;
       this.assistantContextRepository = new Repository<>(new ar.com.sustentate.mw.repositories.Database(cloudantClient.database("assistant", true)));
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public @ResponseBody
    AssistanceResponse chat(@RequestBody AssistanceRequest request) {

        AssistanceResponse r = new AssistanceResponse();
        MessageContext messageContext = new MessageContext();
        MessageContextGlobal messageContextGlobal = new MessageContextGlobal();
        MessageContextGlobalSystem messageContextGlobalSystem = new MessageContextGlobalSystem();
        messageContextGlobal.setSystem(messageContextGlobalSystem);
        messageContext.setGlobal(messageContextGlobal);


        // TODO Sacar el id del asistente de la configuracion
        UUID assistantUUID = UUID.fromString("2e583778-2a1b-47e1-b88c-e2a79d0d4844");

        // TODO Make everycall async
        // TODO Threat session as I should
        if (request.getSessionId().isEmpty()) {
            ServiceCall<SessionResponse> sessionResponseCall =
                    assistant.createSession(new Builder().assistantId(assistantUUID.toString()).build());
            SessionResponse sessionResponse = sessionResponseCall.execute();
            request.setSessionId(sessionResponse.getSessionId());
        }

        Selector selector = eq("sessionId", request.getSessionId());
        List<AssistantContext> assistantContexts = assistantContextRepository.queryAs(new QueryBuilder(selector).limit(1).build(), AssistantContext.class);
        MessageContextSkills messageContextSkills = new MessageContextSkills();
        if (assistantContexts.size() != 0) {
            messageContextSkills.put("sustentate", assistantContexts.get(0).getVariables());
        }
        messageContext.setSkills(messageContextSkills);
        MessageInputOptions inputOptions = new MessageInputOptions();
        inputOptions.setReturnContext(true);

        ServiceCall<MessageResponse> messageResponseCall = assistant.message(new MessageOptions.Builder()
                .assistantId(assistantUUID.toString())
                .sessionId(request.getSessionId())
                .input(new MessageInput.Builder()
                        .text(request.getSentence())
                        .options(inputOptions)
                        .build()
                )
                .build());
        MessageResponse response = messageResponseCall.execute();
        String responseText = "Error md";

        AssistantContext assistantContext = new AssistantContext();
        try {
            Map<String, String> variables = (Map<String, String>) response.getContext().getSkills().get("sustentate");
            assistantContext.setVariables(variables);
        }
        catch(Exception e) {
            assistantContext.setVariables(new HashMap<>());
        } finally {
            assistantContext.setSessionId(request.getSessionId());
        }
        assistantContextRepository.save(assistantContext);

        // TODO ver cual es la manera correcta de parsearesto
        if (response.getOutput().getGeneric().size() > 0) {
            responseText = response.getOutput().getGeneric().get(0).getText();
        }
        r.setSentence(responseText);
        r.setUrlAttachment(request.getUrlAttachment());
        r.setStatus(0); //Ok
        r.setSession(request.getSessionId());
        return r;
    }
}
