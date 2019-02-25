package ar.com.sustentate.mw.models;

import java.util.Map;

public class AssistantContext {
    private String sessionId;
    private Map<String, String> variables;

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
