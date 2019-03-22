package ar.com.sustentate.mw.models;

public class AssistanceResponse {
    private String sentence;
    private String urlAttachment;
    private int status; // 0 OK
    private String sessionId;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getUrlAttachment() {
        return urlAttachment;
    }

    public void setUrlAttachment(String urlAttachment) {
        this.urlAttachment = urlAttachment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
