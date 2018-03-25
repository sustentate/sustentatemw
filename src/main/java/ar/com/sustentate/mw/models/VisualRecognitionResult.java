package ar.com.sustentate.mw.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class VisualRecognitionResult {
    private int errorCode;
    private String errorDesc;
    private int recognitionResult;
    @JsonIgnore
    private HashMap<String, Float> rates;

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

    public HashMap<String, Float> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Float> rates) {
        this.rates = rates;
    }
}
