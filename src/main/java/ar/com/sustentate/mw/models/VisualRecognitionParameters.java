package ar.com.sustentate.mw.models;

import java.util.ArrayList;
import java.util.List;

public class VisualRecognitionParameters {
    private List<String> classifierIds;
    private List<String> owners;

    public VisualRecognitionParameters() {
        classifierIds = new ArrayList<>();
        owners = new ArrayList<>();
    }

    public List<String> getClassifierIds() {
        return classifierIds;
    }

    public void setClassifierIds(List<String> classifierIds) {
        this.classifierIds = classifierIds;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }
}
