package ar.com.sustentate.mw.models;

public class AssistanceRequest {
    private String sessionId;
    private String sentence;
    private String urlAttachment;

    public String getSessionId() {
        return sessionId;
    }

    /* Identificador único de la conversacion. Se pueden intercambiar muchos dialogos
        para una misma conversación.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSentence() {
        return sentence;
    }

    /* Dialogo. E.g. Una pregunta del usuario, etc. */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getUrlAttachment() {
        return urlAttachment;
    }

    public void setUrlAttachment(String urlAttachment) {
        this.urlAttachment = urlAttachment;
    }
}
