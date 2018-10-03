package ar.com.sustentate.mw.models;

public class AssistanceRequest {
    private String conversationId;
    private String sentence;
    private String urlAttachment;


    public String getConversationId() {
        return conversationId;
    }

    /* Identificador único de la conversacion. Se pueden intercambiar muchos dialogos
        para una misma conversación.
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
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
