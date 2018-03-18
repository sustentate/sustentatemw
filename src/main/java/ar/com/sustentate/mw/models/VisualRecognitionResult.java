package ar.com.sustentate.mw.models;

public class VisualRecognitionResult {
    private int errorCode;
    private String errorDesc;
    private int recognitionResult;

    public int getRecognitionResult() {
        return recognitionResult;
    }

    public void setRecognitionResult(int recognitionResult) {
        this.recognitionResult = recognitionResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
